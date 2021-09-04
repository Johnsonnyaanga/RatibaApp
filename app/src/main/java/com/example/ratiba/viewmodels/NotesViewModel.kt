package com.example.ratiba.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.ratiba.NotesRepository
import com.example.ratiba.models.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(val notesRepository: NotesRepository
):ViewModel() {

    init {
        reterieveAllNotes()
    }



    val _allNotes = MutableLiveData<List<Notes>>()
    val allNotes:LiveData<List<Notes>> = _allNotes
    fun reterieveAllNotes() = viewModelScope.launch {
        Log.d("datadata", notesRepository.reterieveAllNotes().toString() )
        _allNotes.value = notesRepository.reterieveAllNotes()
    }

    fun addNote(note: Notes) = viewModelScope.launch {
        notesRepository.addNote(note)
    }

    fun updateNote(note: Notes) = viewModelScope.launch {
        notesRepository.updateNote(note)
    }

    fun deleteNote(note: Notes) = viewModelScope.launch {
        notesRepository.deleteNote(note)
    }



}

class NotesViewmodelFactoryProvider(
    val notesRepository: NotesRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepository) as T
    }
}