package com.example.journel.app.data

import com.example.journel.app.MockkUnitTest
import com.example.journel.app.TestData
import com.example.journel.app.data.local.NoteDao
import com.example.journel.app.data.repository.NotesRepositoryImpl
import com.example.journel.app.domain.repository.NotesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class NotesRepositoryTest : MockkUnitTest() {

    private lateinit var SUT: NotesRepository

    @MockK
    private lateinit var dao: NoteDao

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getNotes invoked on NotesRepository, return success`() = runTest {
        // Given
        SUT = NotesRepositoryImpl(dao, UnconfinedTestDispatcher())
        val givenResponse = TestData.getDummyNotesResponse()

        // When
        coEvery { dao.getAllNotes() }.returns(givenResponse)

        // Invoke
        val apiResponseFlow = SUT.getNotes()

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val response = apiResponseFlow.last()
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            response, CoreMatchers.instanceOf(DataResource.Success::class.java)
        )

        coVerify(exactly = 1) { dao.getAllNotes() }
        confirmVerified(dao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test saveNote invoked on NotesRepository, return success`() = runTest {
        // Given
        SUT = NotesRepositoryImpl(dao, UnconfinedTestDispatcher())
        val note = TestData.getDummyNote()
        val givenResponse = 1L

        // When
        coEvery { dao.insert(note) }.returns(givenResponse)

        // Invoke
        val apiResponseFlow = SUT.saveNote(note)

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val response = apiResponseFlow.last()
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response, CoreMatchers.equalTo(givenResponse))
        coVerify(exactly = 1) { dao.insert(any()) }
        confirmVerified(dao)
    }
}