package com.sharif.simplenote.data.models

data class BulkNoteRequest(
    val title: String,
    val description: String
)

data class BulkNoteResponse(
    val notes: List<Note>
)
