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

    private var deletedNote: Notes? = null

    val getAllNotes = notesRepository.getAllNotes()

    fun insertNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.insertNotes(notes)
        }
    }

    fun deleteNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            deletedNote = notes
            notesRepository.deleteNotes(notes)
        }
    }

    fun undoDeletedNote(notes: Notes) {
        deletedNote?.let {
            viewModelScope.launch(Dispatchers.IO) {
                notesRepository.insertNotes(notes)
            }
        }
    }
}
