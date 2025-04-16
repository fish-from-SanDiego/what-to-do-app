package com.fishfromsandiego.whattodo.domain.film.usecase

import com.fishfromsandiego.whattodo.domain.film.model.FilmModel
import com.fishfromsandiego.whattodo.domain.film.repository.FilmRepository
import javax.inject.Inject

class GetRandomTrendingFilm @Inject constructor(
    val filmRepository: FilmRepository,
) {
    suspend operator fun invoke(excludedFilmId: Int? = null): Result<FilmModel> {
        val res = filmRepository.getTrendingFilms()
        if (res.isFailure) {
            return Result.failure<FilmModel>(res.exceptionOrNull()!!)
        } else {
            val films = res.getOrNull()!!
            val filmsFiltered =
                if (excludedFilmId != null) films.filterNot { it.id == excludedFilmId } else films
            if (filmsFiltered.isEmpty()) return Result.success(films.first())
            return Result.success(filmsFiltered.random())
        }
    }
}