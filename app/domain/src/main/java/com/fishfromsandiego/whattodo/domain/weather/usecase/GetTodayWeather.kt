package com.fishfromsandiego.whattodo.domain.weather.usecase

import com.fishfromsandiego.whattodo.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class GetTodayWeather @Inject constructor(val repository: WeatherRepository) {
    suspend operator fun invoke() = repository.getTodayWeather()
}