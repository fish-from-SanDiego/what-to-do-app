package com.fishfromsandiego.whattodo.presentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.fishfromsandiego.whattodo.R
import com.fishfromsandiego.whattodo.data.WeatherInfo
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import java.time.LocalDate
import java.util.Calendar

@Composable
fun WeatherScreen(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        WeatherCard(
            weatherInfo = weatherInfo,
            modifier = Modifier
                .weight(2f)
                .aspectRatio(1f)
                .padding(12.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun WeatherCard(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            Icon(
                painter = painterResource(weatherInfo.icon),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .fillMaxSize()
                    .padding(8.dp),
                tint = weatherInfo.iconColor,
            )
            Text(
                text = stringResource(weatherInfo.description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                letterSpacing = (-0.02).em,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .wrapContentHeight()
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    Surface(color = MaterialTheme.colorScheme.background) {
        WhatToDoTheme(darkTheme = false, dynamicColor = false) {
            WeatherScreen(
                weatherInfo = WeatherInfo(
                    icon = R.drawable.sunny_24px,
                    description = R.string.sunny_weather_desc,
                    iconColor = Color(color = 0xFFfde910),
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun WeatherScreenDarkThemePreview() {
    WhatToDoTheme(darkTheme = true, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            WeatherScreen(
                weatherInfo = WeatherInfo(
                    icon = R.drawable.sunny_24px,
                    description = R.string.sunny_weather_desc,
                    iconColor = Color(color = 0xFFfde910),
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}