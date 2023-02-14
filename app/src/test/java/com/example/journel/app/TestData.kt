package com.example.journel.app

import com.example.journel.app.data.local.models.Mood
import com.example.journel.app.data.local.models.Note
import com.example.journel.app.data.local.models.NotesListItemType
import java.util.*


object TestData {

    fun getEmptyNotesResponse() = listOf<Note>()

    fun getEmptyNotesListItemTypeResponse() = listOf<NotesListItemType>()

    fun getDummyNotesResponse() = listOf(getDummyNote())

    fun getDummyNotesListItemTypeResponse() = listOf(
        NotesListItemType.MonthHeader(title = "dummyTitle", count = 1, -1),
        NotesListItemType.Content(
            data = getDummyNote()
        )
    )

    fun getDummyNote() = Note(
        id = -1, mood = Mood.NOTHING, text = "dummyText", date = Date()
    )

    fun getEmptyNotesErrorMessage() = "Note body can't be empty"

    fun getNotesLengthErrorMessage() = "Note should contains at least 5 characters"

}