package com.fishfromsandiego.whattodo.data.film.repository

import com.fishfromsandiego.whattodo.data.film.api.FilmApi
import com.fishfromsandiego.whattodo.data.film.dto.FilmData
import com.fishfromsandiego.whattodo.domain.film.model.FilmModel
import com.fishfromsandiego.whattodo.domain.film.repository.FilmRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FilmRepositoryImpl
@Inject constructor(
    val filmApi: FilmApi
) : FilmRepository {
    override suspend fun getTrendingFilms(): Result<List<FilmModel>> {
        val result = filmApi.getTrendingFilms()
        return result.getOrNull()?.let { res ->
            Result.success(res.results.map { it.toModel() })
        } ?: result.exceptionOrNull()!!.let { e ->
            Result.failure(e)
        }
    }
}

fun FilmData.toModel(): FilmModel {
    return FilmModel(
        id = this.id,
        title = this.title,
        overview = this.overview,
        releaseDate = LocalDate.parse(
            this.releaseDateStr,
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        ),
        posterUriStr = this.posterPath,
    )
}