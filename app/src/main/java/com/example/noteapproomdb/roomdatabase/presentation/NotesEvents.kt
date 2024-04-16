package com.example.noteapproomdb.roomdatabase.presentation

import android.icu.text.CaseMap.Title
import com.example.noteapproomdb.roomdatabase.data.Note

sealed interface NotesEvents {
    object SortNotes: NotesEvents

    data class DeleteNotes(val note:Note): NotesEvents

    data class SaveNote(
        val title: String,
        val description: String
    ): NotesEvents
}