package com.example.journel.app.data.local.models

import androidx.room.TypeConverter

class MoodConverter {
    @TypeConverter
    fun toMood(value: Int) = enumValues<Mood>()[value]

    @TypeConverter
    fun fromMood(value: Mood) = value.ordinal
}