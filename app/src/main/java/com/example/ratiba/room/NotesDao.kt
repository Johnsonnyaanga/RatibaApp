package com.example.ratiba.room

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ratiba.models.Cartegories
import com.example.ratiba.models.Notes
import com.example.ratiba.models.Task

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes_table ORDER BY id ASC ")
    suspend fun reterieveAllNotes(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Notes)

    @Update
    suspend fun updateNote(note:Notes)



}