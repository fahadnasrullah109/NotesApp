package com.example.journel.app

import android.graphics.Color
import java.util.*

object Utils {
    const val KEY_IS_NOTE_ADDED = "note_added"

    fun getBackgroundColor(itemCount: Int): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256) % itemCount, rnd.nextInt(256), rnd.nextInt(256))
    }
}