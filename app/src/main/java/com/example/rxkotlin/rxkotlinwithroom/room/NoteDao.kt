package com.example.rxkotlin.rxkotlinwithroom.room

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun insert(note: NoteEntity)

    @Delete
    fun delete(note: NoteEntity)

    @Query("SELECT * FROM Note ORDER BY id ASC")
    fun getAll(): List<NoteEntity>

    @Update
    fun update(note: NoteEntity)

    @Query("SELECT * FROM Note WHERE title LIKE '%' || :title || '%' ORDER BY id ASC")
    fun findAllByTitle(title: String?): List<NoteEntity>
}
