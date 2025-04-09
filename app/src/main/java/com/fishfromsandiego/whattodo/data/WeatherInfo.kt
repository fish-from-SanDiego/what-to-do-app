package com.fishfromsandiego.whattodo.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class WeatherInfo(
    @DrawableRes val icon: Int,
    @StringRes val description: Int,
    val iconColor: Color
)
