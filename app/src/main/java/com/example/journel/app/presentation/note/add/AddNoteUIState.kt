package com.example.journel.app.presentation.note.add

sealed interface AddNoteUIState {
    object IdleState : AddNoteUIState
    object LoadingState : AddNoteUIState
    object SaveNoteSuccess : AddNoteUIState
    class ValidationErrorState(val message: String? = null) : AddNoteUIState
    class ErrorState(val message: String? = null) : AddNoteUIState
}