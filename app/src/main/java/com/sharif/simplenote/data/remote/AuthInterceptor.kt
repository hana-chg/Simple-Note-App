package com.sharif.simplenote.data.remote

import com.sharif.simplenote.data.models.TokenRefresh
import com.sharif.simplenote.data.models.TokenRefreshRequest
import com.sharif.simplenote.data.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor @Inject constructor(
    private val authRepository: Provider<AuthRepository>,
    private val apiService: Provider<ApiService>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { authRepository.get().getAccessToken() }
        val requestBuilder = originalRequest.newBuilder()

        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        var response = chain.proceed(requestBuilder.build())

        if (response.code == 401) {
            response.close()
            val refreshToken = runBlocking { authRepository.get().getRefreshToken() } ?: return chain.proceed(originalRequest)
            val refreshResponse = runBlocking {
                try {
                    apiService.get().refreshToken(TokenRefreshRequest(refreshToken))
                } catch (e: Exception) {
                    null
                }
            }

            refreshResponse?.let {
                runBlocking { authRepository.get().updateAccessToken(it.access) }
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer ${it.access}")
                    .build()
                response = chain.proceed(newRequest)
            }
        }

        return response
    }
}