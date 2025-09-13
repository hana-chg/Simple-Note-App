package com.sharif.simplenote.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sharif.simplenote.data.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getNotes(): PagingSource<Int, Note>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR description LIKE :query ORDER BY updatedAt DESC")
    fun searchNotes(query: String): PagingSource<Int, Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Int): Flow<Note?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}