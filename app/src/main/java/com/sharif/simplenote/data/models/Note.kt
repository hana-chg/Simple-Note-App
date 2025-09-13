package com.sharif.simplenote.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: Int?,
    val title: String,
    val description: String,
    val createdAt: String,  // ISO date-time
    val updatedAt: String,
    val creatorName: String,
    val creatorUsername: String
)