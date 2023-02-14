package com.example.journel.app.domain.usecases

import android.annotation.SuppressLint
import com.example.journel.app.Utils
import com.example.journel.app.data.local.models.*
import java.text.SimpleDateFormat
import java.util.*

class TransformNotesUseCase {

    @SuppressLint("SimpleDateFormat")
    fun execute(input: List<Note>): List<NotesListItemType> {
        if (input.isEmpty()) return listOf()
        val responseList = mutableListOf<NotesListItemType>()
        val map = LinkedHashMap<String, MonthData>()
        val noteResponse = NoteResponse(data = map)
        input.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = it.date
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val monthStr = SimpleDateFormat("MMM").format(calendar.time).uppercase(Locale.US)
            val dayStr = SimpleDateFormat("E, dd").format(calendar.time)
            val monthKey = "$year-$month"
            val dayKey = "$month-$day"
            if (map[monthKey] == null) {
                val dMap = LinkedHashMap<String, DayData>()
                dMap[dayKey] = DayData(name = dayStr, count = 1, items = mutableListOf(it))
                map[monthKey] = MonthData(name = monthStr, count = 1, itemMap = dMap)
            } else {
                val mMap = map[monthKey]!!
                val dData = mMap.itemMap[dayKey]
                mMap.count++
                if (dData == null) {
                    mMap.itemMap[dayKey] =
                        DayData(name = dayStr, count = 1, items = mutableListOf(it))
                } else {
                    dData.count++
                    dData.items.add(it)
                }
            }
        }
        noteResponse.data.values.forEach { monthData ->
            responseList.add(
                NotesListItemType.MonthHeader(
                    title = monthData.name,
                    count = monthData.count,
                    bgColor = Utils.getBackgroundColor(monthData.count)
                )
            )
            monthData.itemMap.values.forEach { dayData ->
                responseList.add(
                    NotesListItemType.DayHeader(
                        title = dayData.name, count = dayData.count
                    )
                )
                dayData.items.forEach { note ->
                    responseList.add(NotesListItemType.Content(data = note))
                }
            }
        }
        return responseList
    }
}