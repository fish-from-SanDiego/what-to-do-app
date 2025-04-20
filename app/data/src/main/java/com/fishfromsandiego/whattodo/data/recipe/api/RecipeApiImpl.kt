package com.fishfromsandiego.whattodo.data.recipe.api

import android.util.Log
import com.fishfromsandiego.whattodo.common.Constants
import com.fishfromsandiego.whattodo.common.exceptions.WhatToDoAppCaughtException
import com.fishfromsandiego.whattodo.data.BuildConfig
import com.fishfromsandiego.whattodo.data.recipe.dto.RecipeDataResponse
import com.fishfromsandiego.whattodo.data.util.getErrorFromStatusCode
import com.fishfromsandiego.whattodo.data.weather.dto.WeatherDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RecipeApiImpl
@Inject constructor(val client: HttpClient) : RecipeApi {
    override suspend fun getRandomRecipe(): Result<RecipeDataResponse> {
        try {
            val response = client.get(
                "https://www.themealdb.com/api/json/v1/1/random.php"
            ) {
                contentType(ContentType.Application.Json)
            }
            val err = getErrorFromStatusCode(response.status.value)
            if (err != null) return Result.failure(err)
            return Result.success(Json.decodeFromString<RecipeDataResponse>(response.bodyAsText()))
        } catch (e: Throwable) {
            Log.d(Constants.LOG_TAG, e.message ?: "unknown error")
            return Result.failure(WhatToDoAppCaughtException("Error occured while downloading recipe data"))
        }
    }
}