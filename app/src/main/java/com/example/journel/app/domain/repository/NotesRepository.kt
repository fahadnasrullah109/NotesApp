package com.example.journel.app.domain.repository

import com.example.journel.app.data.DataResource
import com.example.journel.app.data.local.models.Note
import com.example.journel.app.data.local.models.NoteResponse
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotes(): Flow<DataResource<List<Note>>>
    fun saveNote(note: Note): Flow<Long>
}