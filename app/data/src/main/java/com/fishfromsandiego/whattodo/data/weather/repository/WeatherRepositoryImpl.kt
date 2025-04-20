package com.fishfromsandiego.whattodo.data.weather.repository

import com.fishfromsandiego.whattodo.data.weather.api.WeatherApi
import com.fishfromsandiego.whattodo.data.weather.dto.WeatherData
import com.fishfromsandiego.whattodo.domain.weather.model.WeatherModel
import com.fishfromsandiego.whattodo.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl
@Inject constructor(
    val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getTodayWeather(): Result<WeatherModel> {
        return weatherApi.getCurrentWeather().getOrElse { e ->
            return Result.failure(e)
        }.let { response ->
            Result.success(response.data.first().toModel())
        }
    }
}

fun WeatherData.toModel(): WeatherModel {
    return WeatherModel(
        weatherId = this.weatherId,
        description = this.description,
    )
}