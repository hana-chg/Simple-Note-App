package com.sharif.simplenote.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharif.simplenote.data.local.database.NoteDao
import com.sharif.simplenote.data.models.*
import com.sharif.simplenote.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val noteDao: NoteDao
) {
    fun getNotes(searchQuery: String?): Flow<PagingData<Note>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                if (searchQuery.isNullOrEmpty()) {
                    noteDao.getNotes()
                } else {
                    noteDao.searchNotes("%$searchQuery%")
                }
            }
        ).flow
    }

    suspend fun syncNotes(searchQuery: String?, page: Int = 1): PaginatedNoteList? {
        return try {
            val response = apiService.getNotes(page = page, title = searchQuery, description = searchQuery)
            noteDao.insertNotes(response.results)
            response
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getNote(id: Int): Note? {
        return try {
            apiService.getNote(id).also { noteDao.insertNote(it) }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun createNote(noteRequest: NoteRequest): Note? {
        return try {
            val note = apiService.createNote(noteRequest)
            noteDao.insertNote(note)
            note
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateNote(id: Int, noteRequest: NoteRequest): Note? {
        return try {
            val note = apiService.updateNote(id, noteRequest)
            noteDao.insertNote(note)
            note
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteNote(id: Int): Boolean {
        return try {
            apiService.deleteNote(id)
            noteDao.deleteNote(Note(id, "", "", "", "", "", ""))
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun createBulkNotes(notes: List<BulkNoteRequest>): List<Note>? {
        return try {
            val createdNotes = apiService.createBulkNotes(notes)
            noteDao.insertNotes(createdNotes)
            createdNotes
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getFilteredNotes(
        title: String? = null,
        description: String? = null,
        updatedGte: String? = null,
        updatedLte: String? = null,
        page: Int = 1,
        pageSize: Int = 20
    ): PaginatedNoteList? {
        return try {
            val response = apiService.getFilteredNotes(
                title = title,
                description = description,
                updatedGte = updatedGte,
                updatedLte = updatedLte,
                page = page,
                pageSize = pageSize
            )
            noteDao.insertNotes(response.results)
            response
        } catch (e: Exception) {
            null
        }
    }
}