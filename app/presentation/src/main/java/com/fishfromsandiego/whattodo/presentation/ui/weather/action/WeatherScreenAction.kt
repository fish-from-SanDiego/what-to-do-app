package com.fishfromsandiego.whattodo.presentation.ui.weather.action

sealed class WeatherScreenAction {
    data object LoadWeatherModel : WeatherScreenAction()
}