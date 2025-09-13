package com.sharif.simplenote.data.remote

import com.sharif.simplenote.data.models.*


import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/auth/register/")
    suspend fun register(@Body request: RegisterRequest): Register

    @POST("api/auth/token/")
    suspend fun login(@Body request: TokenObtainPairRequest): TokenObtainPair

    @POST("api/auth/token/refresh/")
    suspend fun refreshToken(@Body request: TokenRefreshRequest): TokenRefresh

    @GET("api/auth/userinfo/")
    suspend fun getUserInfo(): UserInfo

    @GET("api/notes/")
    suspend fun getNotes(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("title") title: String? = null,
        @Query("description") description: String? = null
    ): PaginatedNoteList

    @POST("api/notes/")
    suspend fun createNote(@Body request: NoteRequest): Note

    @GET("api/notes/{id}/")
    suspend fun getNote(@Path("id") id: Int): Note

    @PUT("api/notes/{id}/")
    suspend fun updateNote(@Path("id") id: Int, @Body request: NoteRequest): Note

    @PATCH("api/notes/{id}/")
    suspend fun partialUpdateNote(@Path("id") id: Int, @Body request: PatchedNoteRequest): Note

    @DELETE("api/notes/{id}/")
    suspend fun deleteNote(@Path("id") id: Int): Response<Unit>
}