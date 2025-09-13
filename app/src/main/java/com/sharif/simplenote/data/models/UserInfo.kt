package com.sharif.simplenote.data.models

data class UserInfo(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null
)