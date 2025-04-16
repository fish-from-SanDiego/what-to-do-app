package com.fishfromsandiego.whattodo.presentation.ui.film.action

sealed class FilmScreenAction {
    data object LoadFilmModel : FilmScreenAction()
    data object LoadFilmModelWithoutExcludedIfPossible : FilmScreenAction()
}