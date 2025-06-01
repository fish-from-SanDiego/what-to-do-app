package com.fishfromsandiego.whattodo.data.weather.api

import android.util.Log
import com.fishfromsandiego.whattodo.common.Constants
import com.fishfromsandiego.whattodo.common.exceptions.WhatToDoAppCaughtException
import com.fishfromsandiego.whattodo.data.BuildConfig
import com.fishfromsandiego.whattodo.data.location.LocationProvider
import com.fishfromsandiego.whattodo.data.util.getErrorFromStatusCode
import com.fishfromsandiego.whattodo.data.weather.dto.WeatherDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WeatherApiImpl @Inject constructor(
    val locationProvider: LocationProvider,
    val client: HttpClient
) : WeatherApi {
    override suspend fun getCurrentWeather(): Result<WeatherDataResponse> {
        try {
            val location = locationProvider.getLocation().getOrElse { e ->
                return Result.failure(e)
            }
            val response = client.get(
                "https://api.openweathermap.org/data/2.5/weather" +
                        "?lat=${location.latitude}&lon=${location.longitude}&appid=${BuildConfig.WEATHER_API_KEY}"
            ) {
                contentType(ContentType.Application.Json)
            }
            val err = getErrorFromStatusCode(response.status.value)
            if (err != null) return Result.failure(err)
            return Result.success(Json.decodeFromString<WeatherDataResponse>(response.bodyAsText()))
        } catch (e: Throwable) {
            Log.d(Constants.LOG_TAG, e.message ?: "unknown error")
            return Result.failure(WhatToDoAppCaughtException("Error occured while downloading weather data"))
        }
    }
}