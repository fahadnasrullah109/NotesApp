package com.example.journel.app.domain.usecases

import com.example.journel.app.data.local.models.Note
import com.example.journel.app.domain.repository.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher

class AddNoteUseCase(
    private val notesRepository: NotesRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Note, Long>(dispatcher) {

    override fun execute(parameters: Note) = notesRepository.saveNote(parameters)
}