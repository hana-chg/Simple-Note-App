package com.sharif.simplenote.data.models

data class TokenRefreshRequest(
    val refresh: String
)

data class TokenRefresh(
    val access: String
)