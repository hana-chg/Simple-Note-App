package com.sharif.simplenote.data.models

data class NoteRequest(
    val title: String,
    val description: String
)

data class PatchedNoteRequest(
    val title: String? = null,
    val description: String? = null
)