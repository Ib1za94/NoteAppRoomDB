package com.example.noteapproomdb.roomdatabase.presentation

import androidx.lifecycle.ViewModel
import com.example.noteapproomdb.roomdatabase.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow

class NotesViewModel(
    private val dao: NoteDao
): ViewModel() {

    private val isSortedByDayAdded = MutableStateFlow(true)

    fun onEvent(event: NotesEvents){
        when(event){
            is NotesEvents.DeleteNotes -> {

            }
            is NotesEvents.SaveNote -> {

            }
            NotesEvents.SortNotes -> {

            }
        }
    }
}