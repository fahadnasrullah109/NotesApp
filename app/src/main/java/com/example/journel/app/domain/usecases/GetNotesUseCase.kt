package com.example.journel.app.domain.usecases

import com.example.journel.app.data.DataResource
import com.example.journel.app.data.local.models.Note
import com.example.journel.app.domain.repository.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetNotesUseCase(
    private val notesRepository: NotesRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, DataResource<List<Note>>>(dispatcher) {

    override fun execute(parameters: Unit) = notesRepository.getNotes()
}