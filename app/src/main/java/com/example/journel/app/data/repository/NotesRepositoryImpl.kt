package com.example.journel.app.data.repository

import com.example.journel.app.data.DataResource
import com.example.journel.app.data.local.NoteDao
import com.example.journel.app.data.local.models.Note
import com.example.journel.app.domain.repository.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NotesRepositoryImpl(
    private val noteDao: NoteDao, private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NotesRepository {

    override fun saveNote(note: Note) = flow {
        emit(noteDao.insert(note))
    }.flowOn(dispatcher)

    override fun getNotes() = flow {
        emit(DataResource.Loading)
        emit(DataResource.Success(noteDao.getAllNotes()))
    }.flowOn(dispatcher)
}