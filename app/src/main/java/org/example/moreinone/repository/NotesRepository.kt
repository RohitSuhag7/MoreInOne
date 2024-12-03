package org.example.moreinone.repository

import kotlinx.coroutines.flow.Flow
import org.example.moreinone.model.dao.NotesDao
import org.example.moreinone.model.entities.Notes
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {

    suspend fun insertNotes(notes: Notes) = notesDao.insertNote(notes)

    suspend fun deleteNotes(notes: Notes) = notesDao.deleteNote(notes)

    fun getAllNotes(): Flow<List<Notes>> = notesDao.getAllNotes()
}
