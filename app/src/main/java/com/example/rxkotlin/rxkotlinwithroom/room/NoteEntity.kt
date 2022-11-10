package com.example.rxkotlin.rxkotlinwithroom.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val title: String?,
    val content: String?,
)