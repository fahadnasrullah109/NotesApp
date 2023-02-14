package com.example.journel.app.presentation

import com.example.journel.app.MockkUnitTest
import com.example.journel.app.TestData
import com.example.journel.app.data.DataResource
import com.example.journel.app.domain.usecases.GetNotesUseCase
import com.example.journel.app.domain.usecases.TransformNotesUseCase
import com.example.journel.app.presentation.note.list.NotesListVM
import com.example.journel.app.presentation.note.list.NotesUIState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test


class NotesListVMTest : MockkUnitTest() {

    private lateinit var viewModel: NotesListVM

    @MockK
    private lateinit var getNotesUseCase: GetNotesUseCase

    @MockK
    private lateinit var transformNotesUseCase: TransformNotesUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when getNotes invoked on NotesListVM, get empty response`() = runTest {
        // Given
        val response = TestData.getEmptyNotesResponse()
        val notesTypeResponse = TestData.getEmptyNotesListItemTypeResponse()

        // When
        coEvery { getNotesUseCase.invoke(Unit) }.returns(
            flowOf(
                DataResource.Success(
                    response
                )
            )
        )
        coEvery { transformNotesUseCase.execute(response) }.returns(
            notesTypeResponse
        )

        // Invoke
        viewModel = NotesListVM(
            getNotesUseCase = getNotesUseCase, transformNotesUseCase = transformNotesUseCase
        )
        // Then
        coVerify(exactly = 1) { getNotesUseCase.invoke(Unit) }
        coVerify(exactly = 1) { transformNotesUseCase.execute(response) }
        assertTrue(viewModel.uiState.first() is NotesUIState.SuccessState)
        assertTrue(viewModel.uiState.value is NotesUIState.SuccessState)
        assertEquals(0, (viewModel.uiState.value as NotesUIState.SuccessState).notes.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when getNotes invoked on NotesListVM, get response`() = runTest {
        // Given
        val response = TestData.getDummyNotesResponse()
        val notesTypeResponse = TestData.getDummyNotesListItemTypeResponse()

        // When
        coEvery { getNotesUseCase.invoke(Unit) }.returns(
            flowOf(
                DataResource.Success(
                    response
                )
            )
        )
        coEvery { transformNotesUseCase.execute(response) }.returns(
            notesTypeResponse
        )

        // Invoke
        viewModel = NotesListVM(
            getNotesUseCase = getNotesUseCase, transformNotesUseCase = transformNotesUseCase
        )
        // Then
        coVerify(exactly = 1) { getNotesUseCase.invoke(Unit) }
        coVerify(exactly = 1) { transformNotesUseCase.execute(response) }
        assertTrue(viewModel.uiState.first() is NotesUIState.SuccessState)
        assertTrue(viewModel.uiState.value is NotesUIState.SuccessState)
        assertEquals(2, (viewModel.uiState.value as NotesUIState.SuccessState).notes.size)
    }
}