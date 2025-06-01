package com.fishfromsandiego.whattodo.data.weather.api

import com.fishfromsandiego.whattodo.data.weather.dto.WeatherDataResponse

interface WeatherApi {
    suspend fun getCurrentWeather() : Result<WeatherDataResponse>
}