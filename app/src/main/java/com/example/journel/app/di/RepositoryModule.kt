package com.example.journel.app.di

import com.example.journel.app.data.local.NoteDao
import com.example.journel.app.data.repository.NotesRepositoryImpl
import com.example.journel.app.domain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideUserRepository(
        noteDao: NoteDao
    ): NotesRepository {
        return NotesRepositoryImpl(
            noteDao, Dispatchers.IO
        )
    }
}