package com.sharif.simplenote.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sharif.simplenote.data.models.*
import com.sharif.simplenote.data.remote.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) {
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun login(username: String, password: String): TokenObtainPair? {
        return try {
            val response = apiService.login(TokenObtainPairRequest(username, password))
            context.dataStore.edit {
                it[ACCESS_TOKEN] = response.access
                it[REFRESH_TOKEN] = response.refresh
            }
            response
        } catch (e: Exception) {
            null
        }
    }

    suspend fun register(username: String, email: String, password: String, firstName: String?, lastName: String?): Register? {
        return try {
            val response = apiService.register(RegisterRequest(username, password, email, firstName, lastName))
            response
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserInfo(): UserInfo? {
        return try {
            apiService.getUserInfo()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun logout() {
        context.dataStore.edit {
            it.remove(ACCESS_TOKEN)
            it.remove(REFRESH_TOKEN)
        }
    }

    suspend fun getAccessToken(): String? {
        return context.dataStore.data.map { it[ACCESS_TOKEN] }.first()
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.map { it[REFRESH_TOKEN] }.first()
    }

    suspend fun updateAccessToken(token: String) {
        context.dataStore.edit { it[ACCESS_TOKEN] = token }
    }

    suspend fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }
}