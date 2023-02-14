package com.example.journel.app.presentation.note.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journel.app.data.local.models.Mood
import com.example.journel.app.data.local.models.Note
import com.example.journel.app.domain.usecases.AddNoteUseCase
import com.example.journel.app.domain.usecases.NoteInputResponse
import com.example.journel.app.domain.usecases.VerifyNoteInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddNoteVM @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val verifyNoteInputUseCase: VerifyNoteInputUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<AddNoteUIState>(AddNoteUIState.IdleState)
    val uiState: StateFlow<AddNoteUIState> = _uiState.asStateFlow()

    fun saveNote(noteString: String, mood: Mood) {
        when (val response = verifyNoteInputUseCase.execute(noteString)) {
            is NoteInputResponse.Error -> _uiState.value =
                AddNoteUIState.ValidationErrorState(message = response.message)
            NoteInputResponse.Success -> saveNote(
                Note(
                    mood = mood, text = noteString, date = Date()
                )
            )
        }
    }

    private fun saveNote(note: Note) {
        viewModelScope.launch {
            addNoteUseCase(note).collect {
                _uiState.value = AddNoteUIState.SaveNoteSuccess
            }
        }
    }
}