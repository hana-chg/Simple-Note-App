package com.sharif.simplenote.data.models

data class TokenObtainPairRequest(
    val username: String,
    val password: String
)

data class TokenObtainPair(
    val access: String,
    val refresh: String
)