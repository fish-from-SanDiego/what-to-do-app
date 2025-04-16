package com.fishfromsandiego.whattodo.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fishfromsandiego.whattodo.data.film.repository.FilmRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            packageName
            FilmRepositoryImpl()
//            WhatToDoTheme(dynamicColor = false) {
//                Surface {
//                    FilmScreen(
//                        film = FilmInfo(
//                            title = "Titanic",
//                            overview = stringResource(R.string.film_preview_overview),
//                            releaseDate = LocalDate.of(1997, 12, 19),
//                            poster = painterResource(R.drawable.film_preview_poster_large),
//                        ),
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(8.dp),
//                    )
//                }
//            }
        }
    }
}