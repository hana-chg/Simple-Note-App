package com.sharif.simplenote.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharif.simplenote.data.models.Note
import com.sharif.simplenote.data.models.NoteRequest
import com.sharif.simplenote.data.models.BulkNoteRequest
import com.sharif.simplenote.data.models.PaginatedNoteList
import com.sharif.simplenote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteCreateState(
    val isSuccess: Boolean = false,
    val error: String? = null
)


data class NoteDetailState(
    val note: Note? = null,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
)

data class BulkCreateState(
    val notes: List<Note>? = null,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
)

data class FilterState(
    val notes: PaginatedNoteList? = null,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    val notesFlow: Flow<PagingData<Note>> = _searchQuery.flatMapLatest {
        noteRepository.getNotes(it).cachedIn(viewModelScope)
    }

    private val _createState = MutableStateFlow(NoteCreateState())
    val createState: StateFlow<NoteCreateState> = _createState

    private val _noteState = MutableStateFlow(NoteDetailState())
    val noteState: StateFlow<NoteDetailState> = _noteState

    private val _bulkCreateState = MutableStateFlow(BulkCreateState())
    val bulkCreateState: StateFlow<BulkCreateState> = _bulkCreateState

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState


    fun deleteNote(id: Int) {
        viewModelScope.launch {
            noteRepository.deleteNote(id)
        }
    }


    fun getNote(id: Int) {
        viewModelScope.launch {
            _noteState.value = NoteDetailState(isLoading = true)
            val note = noteRepository.getNote(id)
            _noteState.value = if (note != null) {
                NoteDetailState(note = note)
            } else {
                NoteDetailState(error = "Failed to load note")
            }
        }
    }

    fun updateNote(id: Int, title: String, description: String) {
        viewModelScope.launch {
            val result = noteRepository.updateNote(id, NoteRequest(title, description))
            _noteState.value = if (result != null) {
                NoteDetailState(isSuccess = true, note = result)
            } else {
                NoteDetailState(error = "Failed to update note")
            }
        }
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            noteRepository.syncNotes(query)
        }
    }

    fun createNote(title: String, description: String) {
        viewModelScope.launch {
            val result = noteRepository.createNote(NoteRequest(title, description))
            _createState.value = if (result != null) {
                NoteCreateState(isSuccess = true)
            } else {
                NoteCreateState(error = "Failed to create note")
            }
        }
    }

    fun createBulkNotes(notes: List<BulkNoteRequest>) {
        viewModelScope.launch {
            _bulkCreateState.value = BulkCreateState(isLoading = true)
            val result = noteRepository.createBulkNotes(notes)
            _bulkCreateState.value = if (result != null) {
                BulkCreateState(notes = result, isSuccess = true)
            } else {
                BulkCreateState(error = "Failed to create notes")
            }
        }
    }

    fun getFilteredNotes(
        title: String? = null,
        description: String? = null,
        updatedGte: String? = null,
        updatedLte: String? = null,
        page: Int = 1,
        pageSize: Int = 20
    ) {
        viewModelScope.launch {
            _filterState.value = FilterState(isLoading = true)
            val result = noteRepository.getFilteredNotes(
                title = title,
                description = description,
                updatedGte = updatedGte,
                updatedLte = updatedLte,
                page = page,
                pageSize = pageSize
            )
            _filterState.value = if (result != null) {
                FilterState(notes = result, isSuccess = true)
            } else {
                FilterState(error = "Failed to filter notes")
            }
        }
    }
}