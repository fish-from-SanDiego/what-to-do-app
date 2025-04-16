package com.fishfromsandiego.whattodo.domain.film.repository

import com.fishfromsandiego.whattodo.domain.film.model.FilmModel

interface FilmRepository {
    suspend fun getTrendingFilms(): Result<List<FilmModel>>
}