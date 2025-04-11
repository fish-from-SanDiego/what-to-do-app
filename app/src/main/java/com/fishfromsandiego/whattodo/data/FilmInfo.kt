package com.fishfromsandiego.whattodo.data

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDate

data class FilmInfo(
    val title: String, val overview: String, val releaseDate: LocalDate, val poster: Painter
)
