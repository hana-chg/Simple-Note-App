package com.sharif.simplenote.data.models

data class PaginatedNoteList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Note>
)