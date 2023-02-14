package com.example.journel.app.presentation.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journel.app.data.onError
import com.example.journel.app.data.onLoading
import com.example.journel.app.data.onSuccess
import com.example.journel.app.domain.usecases.GetNotesUseCase
import com.example.journel.app.domain.usecases.TransformNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListVM @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase, private val transformNotesUseCase: TransformNotesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotesUIState>(NotesUIState.LoadingState)
    val uiState: StateFlow<NotesUIState> = _uiState.asStateFlow()

    init {
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase.invoke(Unit).collect { dataResource ->
                dataResource.onLoading {
                    _uiState.value = NotesUIState.LoadingState
                }.onSuccess {
                    _uiState.value = NotesUIState.SuccessState(transformNotesUseCase.execute(this.data))
                }.onError {
                    _uiState.value = NotesUIState.ErrorState(this.exception.message)
                }
            }
        }
    }
}