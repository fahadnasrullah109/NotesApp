package com.example.journel.app.domain.usecases

class VerifyNoteInputUseCase {

    fun execute(note: String) = when {
        note.isBlank() -> NoteInputResponse.Error("Note body can't be empty")
        note.length < 5 -> NoteInputResponse.Error("Note should contains at least 5 characters")
        else -> NoteInputResponse.Success
    }
}

sealed interface NoteInputResponse {
    object Success : NoteInputResponse
    data class Error(val message: String) : NoteInputResponse
}