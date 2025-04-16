package com.fishfromsandiego.whattodo.presentation.di

import com.fishfromsandiego.whattodo.presentation.ui.film.state.FilmUiState
import com.fishfromsandiego.whattodo.presentation.ui.recipe.state.RecipeUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {

    @Provides
    @ViewModelScoped
    fun provideFilmUiState(): FilmUiState {
        return FilmUiState()
    }

    @Provides
    @ViewModelScoped
    fun provideRecipeUiState(): RecipeUiState {
        return RecipeUiState()
    }
}