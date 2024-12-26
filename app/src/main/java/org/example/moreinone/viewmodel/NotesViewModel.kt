package org.example.moreinone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.moreinone.model.entities.Notes
import org.example.moreinone.repository.NotesRepository
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository) : ViewModel() {

    private val deletedNotes = mutableListOf<Notes>()

    val getAllNotes = notesRepository.getAllNotes()

    fun insertNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.insertNotes(notes)
        }
    }

    fun deleteNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            deletedNotes.addAll(listOf(notes))
            notesRepository.deleteNotes(notes)
        }
    }

    fun undoDeletedNote() {
        viewModelScope.launch(Dispatchers.IO) {
            deletedNotes.forEach { note ->
                notesRepository.insertNotes(note)
            }
        }
    }
}
