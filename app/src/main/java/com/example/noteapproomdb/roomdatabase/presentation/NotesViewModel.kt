package com.example.noteapproomdb.roomdatabase.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapproomdb.roomdatabase.data.Note
import com.example.noteapproomdb.roomdatabase.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val dao: NoteDao
): ViewModel() {

    private val isSortedByDayAdded = MutableStateFlow(true)

    private var notes =
        isSortedByDayAdded.flatMapLatest { sort ->
            if (sort) {
                dao.getNotesOrderByDateAdded()
            } else {
                dao.getNotesOrderByTitle()
                }
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(NoteState())
    val state =
        combine(_state, isSortedByDayAdded, notes) { state, isSortedByDayAdded, notes ->
            state.copy(
                notes = notes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())
    fun onEvent(event: NotesEvents){
        when(event){
            is NotesEvents.DeleteNotes -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            is NotesEvents.SaveNote -> {
                val note = Note(
                        title = state.value.title.value,
                        description = state.value.description.value,
                        dateAdded = System.currentTimeMillis())

                viewModelScope.launch {
                    dao.upsertNote(note)
                }

                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        description = mutableStateOf("")
                    )
                }

            }
            NotesEvents.SortNotes -> {
                isSortedByDayAdded.value = !isSortedByDayAdded.value
            }
        }
    }
}