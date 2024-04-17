package com.example.noteapproomdb.roomdatabase.data

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.invoke.TypeDescriptor

@Entity
data class Note(

    val title: String,
    val description: String,
    val dateAdded: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
