package com.example.journel.app.domain

import com.example.journel.app.MockkUnitTest
import com.example.journel.app.TestData
import com.example.journel.app.domain.repository.NotesRepository
import com.example.journel.app.domain.usecases.AddNoteUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test


class AddNoteUseCaseTest : MockkUnitTest() {

    private lateinit var SUT: AddNoteUseCase

    @MockK
    private lateinit var notesRepository: NotesRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when AddNoteUseCase invoked, call saveNotes on NotesRepository, return success`() =
        runTest {
            // Given
            SUT = AddNoteUseCase(notesRepository, UnconfinedTestDispatcher())
            val note = TestData.getDummyNote()
            val givenResponse = 1L

            // When
            coEvery { notesRepository.saveNote(note) }.returns(flowOf(givenResponse))

            // Invoke
            val response = SUT.invoke(note).first()


            // Then
            coVerify(exactly = 1) { notesRepository.saveNote(any()) }
            confirmVerified(notesRepository)
            assertEquals(response, 1L)
        }
}