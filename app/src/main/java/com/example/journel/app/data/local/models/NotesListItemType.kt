package com.example.journel.app.data.local.models

sealed class NotesListItemType {
    data class MonthHeader(val title : String, val count : Int, val bgColor : Int) : NotesListItemType()
    data class DayHeader(val title : String, val count : Int) : NotesListItemType()
    data class Content(val data : Note): NotesListItemType()
}