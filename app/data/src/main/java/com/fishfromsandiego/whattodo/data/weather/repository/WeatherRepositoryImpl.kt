package com.fishfromsandiego.whattodo.data.weather.repository

import com.fishfromsandiego.whattodo.data.weather.api.WeatherApi
import com.fishfromsandiego.whattodo.data.weather.dto.WeatherData
import com.fishfromsandiego.whattodo.domain.weather.model.WeatherModel
import com.fishfromsandiego.whattodo.domain.weather.model.WeatherType
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

private fun getWeatherTypeById(weatherId: Int): WeatherType {
    if (weatherId < 300) return WeatherType.THUNDERSTORM
    if (weatherId < 500) return WeatherType.DRIZZLE
    if (weatherId < 600) return WeatherType.RAIN
    if (weatherId < 700) return WeatherType.SNOW
    if (weatherId < 800) return WeatherType.FOG
    if (weatherId == 800) return WeatherType.CLEAR
    return WeatherType.CLOUDS
}

fun WeatherData.toModel(): WeatherModel {
    return WeatherModel(
        weatherType = getWeatherTypeById(this.weatherId),
        description = this.description,
    )
}
