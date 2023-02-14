package com.example.journel.app.presentation

import com.example.journel.app.MockkUnitTest
import com.example.journel.app.TestData
import com.example.journel.app.data.local.models.Mood
import com.example.journel.app.domain.usecases.AddNoteUseCase
import com.example.journel.app.domain.usecases.NoteInputResponse
import com.example.journel.app.domain.usecases.VerifyNoteInputUseCase
import com.example.journel.app.presentation.note.add.AddNoteUIState
import com.example.journel.app.presentation.note.add.AddNoteVM
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test


class AddNoteVMTest : MockkUnitTest() {

    private lateinit var viewModel: AddNoteVM

    @MockK
    private lateinit var addNoteUseCase: AddNoteUseCase

    @MockK
    private lateinit var verifyNoteInputUseCase: VerifyNoteInputUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when saveNote invoked on AddNoteVM with empty noteString, ValidationErrorState should return`() =
        runTest {
            // Given
            val errorMessage = TestData.getEmptyNotesErrorMessage()

            // When
            coEvery { verifyNoteInputUseCase.execute("") }.returns(
                NoteInputResponse.Error(message = errorMessage)
            )

            // Invoke
            viewModel = AddNoteVM(
                addNoteUseCase = addNoteUseCase, verifyNoteInputUseCase = verifyNoteInputUseCase
            )
            viewModel.saveNote(noteString = "", mood = Mood.NOTHING)
            // Then
            coVerify(exactly = 1) { verifyNoteInputUseCase.execute("") }
            assertTrue(viewModel.uiState.first() is AddNoteUIState.ValidationErrorState)
            assertTrue(viewModel.uiState.value is AddNoteUIState.ValidationErrorState)
            assertEquals(
                errorMessage,
                (viewModel.uiState.value as AddNoteUIState.ValidationErrorState).message
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when saveNote invoked on AddNoteVM with noteString less than minimum length, ValidationErrorState should return`() =
        runTest {
            // Given
            val errorMessage = TestData.getNotesLengthErrorMessage()
            val noteString = "hi"

            // When
            coEvery { verifyNoteInputUseCase.execute(noteString) }.returns(
                NoteInputResponse.Error(message = errorMessage)
            )

            // Invoke
            viewModel = AddNoteVM(
                addNoteUseCase = addNoteUseCase, verifyNoteInputUseCase = verifyNoteInputUseCase
            )
            viewModel.saveNote(noteString = noteString, mood = Mood.NOTHING)
            // Then
            coVerify(exactly = 1) { verifyNoteInputUseCase.execute(noteString) }
            assertTrue(viewModel.uiState.first() is AddNoteUIState.ValidationErrorState)
            assertTrue(viewModel.uiState.value is AddNoteUIState.ValidationErrorState)
            assertEquals(
                errorMessage,
                (viewModel.uiState.value as AddNoteUIState.ValidationErrorState).message
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when saveNote invoked on AddNoteVM with valid noteString, SaveNoteSuccess should return`() =
        runTest {
            // Given
            val noteString = "dummy note to save"
            val response = 1L

            // When
            coEvery { verifyNoteInputUseCase.execute(noteString) }.returns(
                NoteInputResponse.Success
            )

            coEvery { addNoteUseCase.invoke(any()) }.returns(
                flowOf(response)
            )

            // Invoke
            viewModel = AddNoteVM(
                addNoteUseCase = addNoteUseCase, verifyNoteInputUseCase = verifyNoteInputUseCase
            )
            viewModel.saveNote(noteString = noteString, mood = Mood.NOTHING)
            // Then
            coVerify(exactly = 1) { verifyNoteInputUseCase.execute(any()) }
            coVerify(exactly = 1) { addNoteUseCase.invoke(any()) }
            assertTrue(viewModel.uiState.first() is AddNoteUIState.SaveNoteSuccess)
            assertTrue(viewModel.uiState.value is AddNoteUIState.SaveNoteSuccess)
        }
}