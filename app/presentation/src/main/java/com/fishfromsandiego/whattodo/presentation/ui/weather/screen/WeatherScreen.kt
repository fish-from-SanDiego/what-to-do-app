package com.fishfromsandiego.whattodo.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.fishfromsandiego.whattodo.common.exceptions.getUserMessage
import com.fishfromsandiego.whattodo.domain.weather.model.WeatherModel
import com.fishfromsandiego.whattodo.domain.weather.model.WeatherType
import com.fishfromsandiego.whattodo.presentation.R
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import com.fishfromsandiego.whattodo.presentation.ui.weather.state.WeatherUiState
import com.fishfromsandiego.whattodo.presentation.ui.weather.viewmodel.WeatherViewModel
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.collectAsState()
    WeatherScreenContent(state, modifier)
}

@Composable
fun WeatherScreenContent(
    weatherUiState: WeatherUiState,
    modifier: Modifier = Modifier,
) {
    if (weatherUiState.isLoading) {
        ProgressIndicator()
    } else {
        weatherUiState.weatherModel!!.onFailure { e ->
            ErrorIcon(e.getUserMessage(), Modifier.fillMaxSize())
        }.onSuccess { weatherModel ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                WeatherCard(
                    weatherModel = weatherModel,
                    modifier = Modifier
                        .weight(2f)
                        .aspectRatio(1f)
                        .padding(12.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

}

@Composable
fun WeatherCard(
    weatherModel: WeatherModel,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(getWeatherIconPainterId(weatherModel.weatherType)),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .weight(1f)
                .fillMaxSize()
                .padding(8.dp),
        )
        Text(
            text = "The weather is:\n${weatherModel.description}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
            letterSpacing = (-0.02).em,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .wrapContentHeight()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WhatToDoTheme(darkTheme = false, dynamicColor = false) {
        WeatherScreenContent(
            weatherUiState = WeatherUiState(
                weatherModel = Result.success(
                    WeatherModel(
                        weatherType = WeatherType.CLEAR,
                        description = "clear sky"
                    )
                ),
                isLoading = false
            ),
            modifier = Modifier.fillMaxSize(),
        )
    }
    Surface(color = MaterialTheme.colorScheme.background) {
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreviewDark() {
    WhatToDoTheme(darkTheme = true, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            WeatherScreenContent(
                weatherUiState = WeatherUiState(
                    weatherModel = Result.success(
                        WeatherModel(
                            weatherType = WeatherType.CLEAR,
                            description = "clear sky",
                        )
                    ),
                    isLoading = false
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}


object WeatherBottomBarItem : BottomNavigationItem {
    override val screen = WhatToDoAppScreen.Weather
    override val selectedIcon = Icons.Filled.Cloud
    override val unselectedIcon = Icons.Outlined.Cloud
}


private @DrawableRes
fun getWeatherIconPainterId(weatherType: WeatherType): Int {
    return when (weatherType) {
        WeatherType.CLEAR -> R.drawable.clear_day_24px
        WeatherType.CLOUDS -> R.drawable.cloud_24px
        WeatherType.DRIZZLE -> R.drawable.rainy_light_24px
        WeatherType.RAIN -> R.drawable.rainy_heavy_24px
        WeatherType.SNOW -> R.drawable.ac_unit_24px
        WeatherType.FOG -> R.drawable.foggy_24px
        WeatherType.THUNDERSTORM -> R.drawable.thunderstorm_24px
    }
}