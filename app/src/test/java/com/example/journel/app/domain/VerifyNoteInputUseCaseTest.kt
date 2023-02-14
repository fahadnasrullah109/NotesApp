package com.example.journel.app.domain

import com.example.journel.app.MockkUnitTest
import com.example.journel.app.TestData
import com.example.journel.app.domain.usecases.NoteInputResponse
import com.example.journel.app.domain.usecases.VerifyNoteInputUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test


class VerifyNoteInputUseCaseTest : MockkUnitTest() {

    private lateinit var SUT: VerifyNoteInputUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when VerifyNoteInputUseCaseTest invoked, called with  blank note, return Error`() =
        runTest {
            // Given
            SUT = VerifyNoteInputUseCase()
            val error = TestData.getEmptyNotesErrorMessage()
            val noteInput = ""

            // Invoke
            val response = SUT.execute(noteInput)

            // Then
            assertTrue(response is NoteInputResponse.Error)
            assertEquals(
                error, (response as NoteInputResponse.Error).message
            )
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when VerifyNoteInputUseCaseTest invoked, called with note having less than minimum length, return Error`() =
        runTest {
            // Given
            SUT = VerifyNoteInputUseCase()
            val error = TestData.getNotesLengthErrorMessage()
            val noteInput = "hi"

            // Invoke
            val response = SUT.execute(noteInput)

            // Then
            assertTrue(response is NoteInputResponse.Error)
            assertEquals(
                error, (response as NoteInputResponse.Error).message
            )
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when VerifyNoteInputUseCaseTest invoked, called with valid note, return Success`() =
        runTest {
            // Given
            SUT = VerifyNoteInputUseCase()
            val noteInput = "dummy note to save"

            // Invoke
            val response = SUT.execute(noteInput)

            // Then
            assertTrue(response is NoteInputResponse.Success)
        }
}