package com.fishfromsandiego.whattodo.presentation.ui.film.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.fishfromsandiego.whattodo.common.exceptions.getUserMessage
import com.fishfromsandiego.whattodo.domain.film.model.FilmModel
import com.fishfromsandiego.whattodo.presentation.R
import com.fishfromsandiego.whattodo.presentation.ui.BottomNavigationItem
import com.fishfromsandiego.whattodo.presentation.ui.ErrorIcon
import com.fishfromsandiego.whattodo.presentation.ui.ProgressIndicator
import com.fishfromsandiego.whattodo.presentation.ui.WhatToDoAppScreen
import com.fishfromsandiego.whattodo.presentation.ui.imagePlaceholder
import com.fishfromsandiego.whattodo.presentation.ui.film.state.FilmUiState
import com.fishfromsandiego.whattodo.presentation.ui.film.viewmodel.FilmViewModel
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import org.orbitmvi.orbit.compose.collectAsState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FilmScreen(
    viewModel: FilmViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.collectAsState()
    FilmScreenContent(state, modifier)
}

@Composable
fun FilmScreenContent(
    filmUiState: FilmUiState,
    modifier: Modifier = Modifier
) {
    if (filmUiState.isFilmModelLoading) {
        ProgressIndicator()
    } else {
        filmUiState.filmModel!!.onFailure { e ->
            ErrorIcon(e.getUserMessage(), Modifier.fillMaxSize())
        }.onSuccess { filmModel ->
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .background(color = MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainInfoCard(
                    film = filmModel,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .verticalScroll(rememberScrollState()),
                    text = filmModel.overview, style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
fun MainInfoCard(film: FilmModel, modifier: Modifier = Modifier) {
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
            AsyncImage(
                model = film.posterUri,
                placeholder = imagePlaceholder(R.drawable.film_preview_poster_large),
                contentDescription = "Film Poster",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight(0.38197f)
                    .padding(8.dp)
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = film.title,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(4.dp),
                text = "Release Date: ${film.releaseDate.format(formatter)}",
                textAlign = TextAlign.Center,
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
            FilmScreenContent(
                filmUiState = FilmUiState(
                    filmModel = Result.success(
                        FilmModel(
                            id = 1,
                            title = "Titanic",
                            overview = "101-year-old Rose DeWitt Bukater tells the story of her life aboard the Titanic, 84 years later. A young Rose boards the ship with her mother and fiancé. Meanwhile, Jack Dawson and Fabrizio De Rossi win third-class tickets aboard the ship. Rose tells the whole story from Titanic\\'s departure through to its death—on its first and last voyage—on April 15, 1912.",
                            releaseDate = LocalDate.of(1997, 12, 19),
                            posterUri = "https://image.tmdb.org/t/p/original/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg".toUri()
                        )
                    )

                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            )
        }
    }
}

object FilmBottomBarItem : BottomNavigationItem {
    override val screen = WhatToDoAppScreen.Film
    override val selectedIcon = Icons.Filled.Movie
    override val unselectedIcon = Icons.Outlined.Movie
}

@Preview(showBackground = true)
@Composable
fun FilmScreenPreviewDark() {
    WhatToDoTheme(darkTheme = true, dynamicColor = false) {
        Surface {
            FilmScreenContent(
                filmUiState = FilmUiState(
                    filmModel = Result.success(
                        FilmModel(
                            id = 1,
                            title = "Titanic",
                            overview = "101-year-old Rose DeWitt Bukater tells the story of her life aboard the Titanic, 84 years later. A young Rose boards the ship with her mother and fiancé. Meanwhile, Jack Dawson and Fabrizio De Rossi win third-class tickets aboard the ship. Rose tells the whole story from Titanic\\'s departure through to its death—on its first and last voyage—on April 15, 1912.",
                            releaseDate = LocalDate.of(1997, 12, 19),
                            posterUri = "https://image.tmdb.org/t/p/original/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg".toUri()
                        )
                    )

                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            )
        }
    }
}