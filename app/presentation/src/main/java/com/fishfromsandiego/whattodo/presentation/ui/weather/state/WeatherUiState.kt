package com.fishfromsandiego.whattodo.presentation.ui.weather.state

import com.fishfromsandiego.whattodo.domain.weather.model.WeatherModel

data class WeatherUiState(
    val weatherModel: Result<WeatherModel>? = null,
    val isLoading: Boolean = true,
)
