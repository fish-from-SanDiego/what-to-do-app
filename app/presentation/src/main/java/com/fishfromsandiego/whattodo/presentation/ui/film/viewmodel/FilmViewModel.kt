package com.fishfromsandiego.whattodo.presentation.ui.film.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fishfromsandiego.whattodo.common.Constants
import com.fishfromsandiego.whattodo.domain.film.usecase.GetRandomTrendingFilm
import com.fishfromsandiego.whattodo.presentation.ui.film.action.FilmScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.film.sideeffect.FilmScreenSideEffect
import com.fishfromsandiego.whattodo.presentation.ui.film.state.FilmUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    val getRandomTrendingFilm: GetRandomTrendingFilm,
    initialState: FilmUiState,
) : ContainerHost<FilmUiState, FilmScreenSideEffect>, ViewModel() {
    override val container = container<FilmUiState, FilmScreenSideEffect>(initialState)

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        intent {
            reduce {
                state.copy(
                    filmModel = Result.failure(e),
                    isFilmModelLoading = false,
                    isPosterLoading = false,
                )
            }
        }
    }

    init {
        Log.d(Constants.LOG_TAG, "init load film")
        dispatch(FilmScreenAction.LoadFilmModel)
    }


    fun dispatch(action: FilmScreenAction) {
        when (action) {
            FilmScreenAction.LoadFilmModel -> loadFilmModel()
            FilmScreenAction.LoadFilmModelWithoutExcludedIfPossible -> loadFilmModelWithoutExcluded()
        }
    }

    private fun loadFilmModel() = intent {
        viewModelScope.launch(exceptionHandler) {
            reduce { state.copy(isFilmModelLoading = true) }
            val filmModel = getRandomTrendingFilm()
            Log.d(Constants.LOG_TAG, filmModel.exceptionOrNull()?.message ?: "")
            if (filmModel.isFailure) throw filmModel.exceptionOrNull()!!
            reduce { state.copy(isFilmModelLoading = false, filmModel = filmModel) }
        }
    }

    private fun loadFilmModelWithoutExcluded() = intent {
        viewModelScope.launch(exceptionHandler) {
            reduce { state.copy(isFilmModelLoading = true) }
            val filmModel = getRandomTrendingFilm(excludedFilmId = state.filmModel?.getOrNull()?.id)
            if (filmModel.isFailure) throw filmModel.exceptionOrNull()!!
            reduce { state.copy(isFilmModelLoading = false, filmModel = filmModel) }
        }
    }

}