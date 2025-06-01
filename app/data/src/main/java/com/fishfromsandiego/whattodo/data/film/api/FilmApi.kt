package com.fishfromsandiego.whattodo.data.film.api

import com.fishfromsandiego.whattodo.data.film.dto.FilmDataResponse

interface FilmApi {
    suspend fun getTrendingFilms(): Result<FilmDataResponse>
}