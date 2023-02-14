package com.example.journel.app.di

import com.example.journel.app.domain.repository.NotesRepository
import com.example.journel.app.domain.usecases.AddNoteUseCase
import com.example.journel.app.domain.usecases.GetNotesUseCase
import com.example.journel.app.domain.usecases.TransformNotesUseCase
import com.example.journel.app.domain.usecases.VerifyNoteInputUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideAddNoteUseCase(
        repository: NotesRepository
    ): AddNoteUseCase {
        return AddNoteUseCase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideGetNotesUseCase(
        repository: NotesRepository
    ): GetNotesUseCase {
        return GetNotesUseCase(repository, Dispatchers.IO)
    }

    @ViewModelScoped
    @Provides
    fun provideVerifyNotesInputUseCase() = VerifyNoteInputUseCase()

    @ViewModelScoped
    @Provides
    fun provideTransformNotesUseCase() = TransformNotesUseCase()
}