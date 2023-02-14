package com.example.journel.app.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Note")
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val mood: Mood,
    val text: String,
    val date: Date
)

enum class Mood(val value: Int) {
    HAPPY(0), NEUTRAL(1), SAD(2), NOTHING(-1)
}