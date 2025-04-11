package com.fishfromsandiego.whattodo.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.fishfromsandiego.whattodo.R
import com.fishfromsandiego.whattodo.data.FilmInfo
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import java.nio.file.WatchEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FilmScreen(film: FilmInfo, modifier: Modifier = Modifier) {


    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainInfoCard(
            film = film,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )

        Text(
            modifier = Modifier
                .padding(4.dp)
                .verticalScroll(rememberScrollState()),
            text = film.overview, style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun MainInfoCard(film: FilmInfo, modifier: Modifier = Modifier) {
    val formatter = DateTimeFormatter.ofPattern(stringResource(R.string.film_release_date_pattern))

    Card(
        modifier = modifier.padding(), colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = film.poster,
                contentDescription = "Film Poster",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight(0.38197f)
                    .padding(4.dp)
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = film.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(4.dp),
                text = "Release Date: ${film.releaseDate.format(formatter)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FilmScreenPreview() {
    WhatToDoTheme(darkTheme = false, dynamicColor = false) {
        Surface {
            FilmScreen(
                film = FilmInfo(
                    title = "Titanic",
                    overview = stringResource(R.string.film_preview_overview),
                    releaseDate = LocalDate.of(1997, 12, 19),
                    poster = painterResource(R.drawable.film_preview_poster_large),
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilmScreenPreviewDark() {
    WhatToDoTheme(darkTheme = true, dynamicColor = false) {
        Surface {
            FilmScreen(
                film = FilmInfo(
                    title = "Titanic",
                    overview = stringResource(R.string.film_preview_overview),
                    releaseDate = LocalDate.of(1997, 12, 19),
                    poster = painterResource(R.drawable.film_preview_poster_large),
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            )
        }
    }
}