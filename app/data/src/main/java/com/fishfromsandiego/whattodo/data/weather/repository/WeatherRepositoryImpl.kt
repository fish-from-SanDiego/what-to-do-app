package com.fishfromsandiego.whattodo.data.weather.repository

import androidx.core.net.toUri
import com.fishfromsandiego.whattodo.domain.weather.repository.WeatherRepository
import com.fishfromsandiego.whattodo.domain.weather.model.WeatherModel

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getTodayWeather(): Result<WeatherModel> {
        return Result.success(
            WeatherModel(
                description = "It's sunny today! Why not going out?",
                iconUri = "https://cdn1.iconfinder.com/data/icons/weather-429/64/weather_icons_color-01-512.png".toUri()
            )
        )
    }

}