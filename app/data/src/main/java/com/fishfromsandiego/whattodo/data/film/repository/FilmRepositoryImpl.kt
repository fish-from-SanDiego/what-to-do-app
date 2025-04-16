package com.fishfromsandiego.whattodo.data.film.repository

import android.net.Uri
import com.fishfromsandiego.whattodo.data.BuildConfig
import com.fishfromsandiego.whattodo.domain.film.model.FilmModel
import com.fishfromsandiego.whattodo.domain.film.repository.FilmRepository
import java.time.LocalDate
import androidx.core.net.toUri

class FilmRepositoryImpl : FilmRepository {
    override suspend fun getTrendingFilms(): Result<List<FilmModel>> {
        return Result.success(
            listOf(
                FilmModel(
                    id = 1,
                    title = "Titanic",
                    overview = "101-year-old Rose DeWitt Bukater tells the story of her life aboard the Titanic, 84 years later. A young Rose boards the ship with her mother and fiancé. Meanwhile, Jack Dawson and Fabrizio De Rossi win third-class tickets aboard the ship. Rose tells the whole story from Titanic\\'s departure through to its death—on its first and last voyage—on April 15, 1912.",
                    releaseDate = LocalDate.of(1997, 12, 19),
                    posterUri = "https://image.tmdb.org/t/p/original/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg".toUri()
                )
            )
        )
    }
}