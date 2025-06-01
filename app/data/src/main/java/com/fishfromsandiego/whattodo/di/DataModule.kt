package com.fishfromsandiego.whattodo.di

import android.content.Context
import androidx.room.Room
import com.fishfromsandiego.whattodo.data.chore.dao.ChoreDao
import com.fishfromsandiego.whattodo.data.chore.repository.ChoreRepositoryImpl
import com.fishfromsandiego.whattodo.data.database.AppDatabase
import com.fishfromsandiego.whattodo.data.film.api.FilmApi
import com.fishfromsandiego.whattodo.data.film.api.FilmApiImpl
import com.fishfromsandiego.whattodo.data.film.repository.FilmRepositoryImpl
import com.fishfromsandiego.whattodo.data.location.LocationProvider
import com.fishfromsandiego.whattodo.data.location.LocationProviderImpl
import com.fishfromsandiego.whattodo.data.recipe.api.RecipeApi
import com.fishfromsandiego.whattodo.data.recipe.api.RecipeApiImpl
import com.fishfromsandiego.whattodo.data.recipe.repository.RecipeRepositoryImpl
import com.fishfromsandiego.whattodo.data.weather.api.WeatherApi
import com.fishfromsandiego.whattodo.data.weather.api.WeatherApiImpl
import com.fishfromsandiego.whattodo.data.weather.repository.WeatherRepositoryImpl
import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import com.fishfromsandiego.whattodo.domain.film.repository.FilmRepository
import com.fishfromsandiego.whattodo.domain.recipe.repository.RecipeRepository
import com.fishfromsandiego.whattodo.domain.weather.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFilmApi(httpClient: HttpClient): FilmApi {
        return FilmApiImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideFilmRepository(filmApi: FilmApi): FilmRepository {
        return FilmRepositoryImpl(filmApi)
    }

    @Provides
    @Singleton
    fun provideChoreRepository(choreDao: ChoreDao): ChoreRepository {
        return ChoreRepositoryImpl(choreDao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeApi: RecipeApi): RecipeRepository {
        return RecipeRepositoryImpl(recipeApi)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "main_db"
    ).build()

    @Provides
    @Singleton
    fun provideChoreDao(db: AppDatabase) = db.choreDao()

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    @Provides
    @Singleton
    fun provideLocationProvider(): LocationProvider = LocationProviderImpl()

    @Provides
    @Singleton
    fun provideWeatherApi(
        locationProvider: LocationProvider,
        client: HttpClient
    ): WeatherApi =
        WeatherApiImpl(locationProvider, client)

    @Provides
    @Singleton
    fun provideRecipeApi(httpClient: HttpClient): RecipeApi {
        return RecipeApiImpl(httpClient)
    }

}