package com.example.journel.app.di

import android.content.Context
import com.example.journel.app.data.local.AppDatabase
import com.example.journel.app.data.local.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.init(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(roomDB: AppDatabase): NoteDao {
        return roomDB.noteDao()
    }
}