package com.fishfromsandiego.whattodo.presentation.ui.film.state

import android.media.Image
import com.fishfromsandiego.whattodo.domain.film.model.FilmModel

data class FilmUiState(
    val filmModel: Result<FilmModel>? = null,
    val isFilmModelLoading: Boolean = true,
    val posterImage: Result<Image>? = null,
    val isPosterLoading: Boolean = true,
)
