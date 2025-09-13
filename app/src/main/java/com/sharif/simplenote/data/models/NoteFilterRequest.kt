package com.sharif.simplenote.data.models

data class NoteFilterRequest(
    val title: String? = null,
    val description: String? = null,
    val updated__gte: String? = null, // Updated greater than or equal (ISO date)
    val updated__lte: String? = null, // Updated less than or equal (ISO date)
    val page: Int = 1,
    val page_size: Int = 20
)
