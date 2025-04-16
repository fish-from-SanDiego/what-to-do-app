package com.fishfromsandiego.whattodo.domain.weather.model

import android.net.Uri

data class WeatherModel(
    val description: String,
    val iconUri: Uri,
)