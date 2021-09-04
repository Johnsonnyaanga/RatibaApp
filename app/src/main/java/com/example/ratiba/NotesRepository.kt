package com.example.ratiba

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ratiba.models.Notes
import com.example.ratiba.room.NotesDao

class NotesRepository(private val notesDao: NotesDao) {
    suspend fun reterieveAllNotes() = notesDao.reterieveAllNotes()

    suspend fun addNote(note: Notes) = notesDao.addNote(note)

    suspend fun updateNote(note: Notes) = notesDao.updateNote(note)

    suspend fun deleteNote(note: Notes) = notesDao.deleteNotes(note)
}