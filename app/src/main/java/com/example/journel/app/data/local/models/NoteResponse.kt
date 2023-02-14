package com.example.journel.app.data.local.models

//data class NoteResponse(val data: LinkedHashMap<String, LinkedHashMap<String, MonthData>>)
data class NoteResponse(val data: LinkedHashMap<String, MonthData>)

data class MonthData(
    val name: String, var count: Int, val itemMap: LinkedHashMap<String, DayData>
)
//data class MonthData(val name: String, var count: Int, val itemMap:  MutableList<Note>)

data class DayData(val name: String, var count: Int, val items: MutableList<Note>)