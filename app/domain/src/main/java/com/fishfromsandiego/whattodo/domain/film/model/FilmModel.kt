package com.fishfromsandiego.whattodo.domain.film.model

import android.net.Uri
import java.time.LocalDate

data class FilmModel(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate,
    val posterUri: Uri,
)