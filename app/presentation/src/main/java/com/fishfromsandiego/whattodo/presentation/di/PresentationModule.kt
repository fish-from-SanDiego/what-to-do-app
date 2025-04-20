package com.fishfromsandiego.whattodo.presentation.di

import com.fishfromsandiego.whattodo.presentation.ui.BottomNavigationItem
import com.fishfromsandiego.whattodo.presentation.ui.RecipeBottomBarItem
import com.fishfromsandiego.whattodo.presentation.ui.WeatherBottomBarItem
import com.fishfromsandiego.whattodo.presentation.ui.chore.state.ChoresUiState
import com.fishfromsandiego.whattodo.presentation.ui.film.screen.FilmBottomBarItem
import com.fishfromsandiego.whattodo.presentation.ui.film.state.FilmUiState
import com.fishfromsandiego.whattodo.presentation.ui.recipe.state.RecipeUiState
import com.fishfromsandiego.whattodo.presentation.ui.weather.state.WeatherUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

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

    @Provides
    @ViewModelScoped
    fun provideWeatherUiState(): WeatherUiState {
        return WeatherUiState()
    }

    @Provides
    @ViewModelScoped
    fun provideChoresUiState(): ChoresUiState {
        return ChoresUiState()
    }

    @Provides
    @Singleton
    fun provideNavigationItems(): Array<BottomNavigationItem> {
        return arrayOf(
            WeatherBottomBarItem,
            RecipeBottomBarItem,
            FilmBottomBarItem
        )
    }
}