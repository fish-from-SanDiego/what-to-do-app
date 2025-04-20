package com.fishfromsandiego.whattodo.presentation.ui.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fishfromsandiego.whattodo.domain.weather.usecase.GetTodayWeather
import com.fishfromsandiego.whattodo.presentation.ui.weather.action.WeatherScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.weather.sideeffect.WeatherScreenSideEffect
import com.fishfromsandiego.whattodo.presentation.ui.weather.state.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(
    val getTodayWeather: GetTodayWeather,
    initialState: WeatherUiState,
) : ContainerHost<WeatherUiState, WeatherScreenSideEffect>, ViewModel() {
    override val container = container<WeatherUiState, WeatherScreenSideEffect>(initialState)

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        intent {
            reduce {
                state.copy(
                    weatherModel = Result.failure(e),
                    isLoading = false,
                )
            }
        }
    }

    init {
        dispatch(WeatherScreenAction.LoadWeatherModel)
    }

    fun dispatch(action: WeatherScreenAction) {
        when (action) {
            WeatherScreenAction.LoadWeatherModel -> loadWeatherModel()
        }
    }

    private fun loadWeatherModel() = intent {
        viewModelScope.launch(exceptionHandler) {
            reduce {
                state.copy(isLoading = true)
            }
            val weatherModel = getTodayWeather()
            weatherModel
                .onFailure { e ->
                    throw e
                }
                .onSuccess { reduce { state.copy(weatherModel = weatherModel, isLoading = false) } }
        }
    }

}