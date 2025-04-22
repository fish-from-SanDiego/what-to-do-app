package com.fishfromsandiego.whattodo.domain.weather.model

data class WeatherModel(
    val weatherType: WeatherType,
    val description: String,
)

enum class WeatherType {
    CLEAR,
    CLOUDS,
    DRIZZLE,
    RAIN,
    SNOW,
    FOG,
    THUNDERSTORM,
}