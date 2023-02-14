package com.example.journel.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journel.app.data.local.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note ORDER BY date DESC")
    fun getAllNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long

}