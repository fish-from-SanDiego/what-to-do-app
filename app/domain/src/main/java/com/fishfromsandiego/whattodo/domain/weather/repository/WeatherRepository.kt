package com.fishfromsandiego.whattodo.domain.weather.repository

import com.fishfromsandiego.whattodo.domain.weather.model.WeatherModel

interface WeatherRepository {
    suspend fun getTodayWeather(): Result<WeatherModel>
}