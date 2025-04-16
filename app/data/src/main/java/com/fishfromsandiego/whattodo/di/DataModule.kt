package com.fishfromsandiego.whattodo.di

import android.net.Uri
import com.fishfromsandiego.whattodo.data.chore.repository.ChoreRepositoryImpl
import com.fishfromsandiego.whattodo.data.film.api.FilmApi
import com.fishfromsandiego.whattodo.data.film.repository.FilmRepositoryImpl
import com.fishfromsandiego.whattodo.data.recipe.repository.RecipeRepositoryImpl
import com.fishfromsandiego.whattodo.data.weather.repository.WeatherRepositoryImpl
import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import com.fishfromsandiego.whattodo.domain.film.repository.FilmRepository
import com.fishfromsandiego.whattodo.domain.recipe.repository.RecipeRepository
import com.fishfromsandiego.whattodo.domain.weather.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFilmRepository(): FilmRepository {
        return FilmRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideChoreRepository(): ChoreRepository {
        return ChoreRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(): RecipeRepository {
        return RecipeRepositoryImpl()
    }
}