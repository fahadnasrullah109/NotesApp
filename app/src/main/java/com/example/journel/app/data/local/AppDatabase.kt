package com.example.journel.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.journel.app.data.local.AppDatabase.Companion.DATABASE_VERSION
import com.example.journel.app.data.local.models.DateConverter
import com.example.journel.app.data.local.models.MoodConverter
import com.example.journel.app.data.local.models.Note

@Database(
    entities = [Note::class],
    version = DATABASE_VERSION, exportSchema = false,
)
@TypeConverters(
    DateConverter::class, MoodConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private const val DB_NAME = "Note.db"
        const val DATABASE_VERSION = 1

        // to void duplicate instances of DB
        @Volatile
        private var instance: AppDatabase? = null
        fun init(context: Context, useInMemoryDb: Boolean = false): AppDatabase {
            if (instance != null && !useInMemoryDb) {
                return instance!!
            }
            synchronized(this) {
                instance = if (useInMemoryDb) {
                    Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                } else {
                    Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                }.fallbackToDestructiveMigration().build()
                return instance!!
            }
        }
    }
}