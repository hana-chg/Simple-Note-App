package com.sharif.simplenote.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sharif.simplenote.data.models.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}