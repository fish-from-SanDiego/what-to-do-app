package com.fishfromsandiego.whattodo.data.weather.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class WeatherData(
    @SerialName("id") val weatherId: Int,
    @SerialName("description") val description: String,
)
