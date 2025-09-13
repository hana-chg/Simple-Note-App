package com.sharif.simplenote.data.models

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null
)

data class Register(
    val username: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null
)