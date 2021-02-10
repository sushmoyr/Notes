package com.sushmoyr.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sushmoyr.notes.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note:Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM NOTES WHERE title LIKE :query OR body LIKE:query")
    fun searchNote(query: String?): LiveData<List<Note>>

}