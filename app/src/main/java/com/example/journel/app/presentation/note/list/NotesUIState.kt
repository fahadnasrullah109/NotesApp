package com.example.journel.app.presentation.note.list

import com.example.journel.app.data.local.models.NotesListItemType

sealed interface NotesUIState {
    object LoadingState : NotesUIState
    class SuccessState(val notes: List<NotesListItemType>) : NotesUIState
    class ErrorState(val message: String? = null) : NotesUIState
}