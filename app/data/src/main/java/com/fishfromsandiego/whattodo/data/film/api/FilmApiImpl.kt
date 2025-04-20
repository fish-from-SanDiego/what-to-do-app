package com.fishfromsandiego.whattodo.data.film.api

import android.util.Log
import com.fishfromsandiego.whattodo.common.Constants
import com.fishfromsandiego.whattodo.common.exceptions.WhatToDoAppCaughtException
import com.fishfromsandiego.whattodo.data.BuildConfig
import com.fishfromsandiego.whattodo.data.film.dto.FilmDataResponse
import com.fishfromsandiego.whattodo.data.util.getErrorFromStatusCode
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FilmApiImpl @Inject constructor(
    val client: HttpClient
) : FilmApi {
    private val apiKey = BuildConfig.TMDB_API_KEY

    private companion object {
        const val basePosterPath = "https://image.tmdb.org/t/p/original"
    }

    override suspend fun getTrendingFilms(): Result<FilmDataResponse> {
        try {
            val response = client.get(
                "https://api.themoviedb.org/3/trending/movie/day?api_key=${BuildConfig.TMDB_API_KEY}&page=1"
            ) {
                contentType(ContentType.Application.Json)
            }
            val err = getErrorFromStatusCode(response.status.value)
            if (err != null) return Result.failure(err)
            return Json.decodeFromString<FilmDataResponse>(response.bodyAsText())
                .let {
                    Result.success(
                        FilmDataResponse(
                            results = it.results.map
                            { data -> data.copy(posterPath = "$basePosterPath${data.posterPath}") }
                        )
                    )
                }
        } catch (e: Throwable) {
            Log.d(Constants.LOG_TAG, e.message ?: "unkonwn error")
            return Result.failure(WhatToDoAppCaughtException("Error occured while downloading film data"))
        }
    }
}