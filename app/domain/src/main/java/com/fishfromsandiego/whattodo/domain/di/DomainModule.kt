package com.fishfromsandiego.whattodo.domain.di

import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import com.fishfromsandiego.whattodo.domain.chore.usecase.AddAndGetNewChore
import com.fishfromsandiego.whattodo.domain.chore.usecase.EditExistingChore
import com.fishfromsandiego.whattodo.domain.chore.usecase.ListAllChoresNewFirst
import com.fishfromsandiego.whattodo.domain.film.repository.FilmRepository
import com.fishfromsandiego.whattodo.domain.film.usecase.GetRandomTrendingFilm
import com.fishfromsandiego.whattodo.domain.recipe.repository.RecipeRepository
import com.fishfromsandiego.whattodo.domain.recipe.usecase.GetRandomRecipe
import com.fishfromsandiego.whattodo.domain.weather.repository.WeatherRepository
import com.fishfromsandiego.whattodo.domain.weather.usecase.GetTodayWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideAddAndGetNewChore(repository: ChoreRepository): AddAndGetNewChore {
        return AddAndGetNewChore(repository)
    }

    @Singleton
    @Provides
    fun provideEditExistingChore(repository: ChoreRepository): EditExistingChore {
        return EditExistingChore(repository)
    }

    @Singleton
    @Provides
    fun provideListAllChoresNewFirst(repository: ChoreRepository): ListAllChoresNewFirst {
        return ListAllChoresNewFirst(repository)
    }


    @Singleton
    @Provides
    fun provideGetRandomTrendingFilm(repository: FilmRepository): GetRandomTrendingFilm {
        return GetRandomTrendingFilm(repository)
    }

    @Singleton
    @Provides
    fun provideGetRandomRecipe(repository: RecipeRepository): GetRandomRecipe {
        return GetRandomRecipe(repository)
    }

    @Singleton
    @Provides
    fun provideGetTodayWeather(repository: WeatherRepository): GetTodayWeather {
        return GetTodayWeather(repository)
    }

}