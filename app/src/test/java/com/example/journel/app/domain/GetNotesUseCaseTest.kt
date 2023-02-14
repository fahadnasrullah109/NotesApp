package com.example.journel.app.domain

import com.example.journel.app.MockkUnitTest
import com.example.journel.app.TestData
import com.example.journel.app.data.DataResource
import com.example.journel.app.domain.repository.NotesRepository
import com.example.journel.app.domain.usecases.GetNotesUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Test


class GetNotesUseCaseTest : MockkUnitTest() {

    private lateinit var SUT: GetNotesUseCase

    @MockK
    private lateinit var notesRepository: NotesRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test when GetNotesUseCase invoked, call getNotes on NotesRepository, return success`() =
        runTest {
            // Given
            SUT = GetNotesUseCase(notesRepository, UnconfinedTestDispatcher())
            val givenResponse = TestData.getDummyNotesResponse()

            // When
            coEvery { notesRepository.getNotes() }.returns(flowOf(DataResource.Success(givenResponse)))

            // Invoke
            val response = SUT.invoke(Unit).first()


            // Then
            coVerify(exactly = 1) { notesRepository.getNotes() }
            confirmVerified(notesRepository)
            MatcherAssert.assertThat(
                response, CoreMatchers.instanceOf(DataResource.Success::class.java)
            )
        }
}