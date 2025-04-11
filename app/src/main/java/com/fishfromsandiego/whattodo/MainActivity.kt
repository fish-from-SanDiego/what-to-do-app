package com.fishfromsandiego.whattodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fishfromsandiego.whattodo.data.FilmInfo
import com.fishfromsandiego.whattodo.presentation.ui.ChoreEditScreen
import com.fishfromsandiego.whattodo.presentation.ui.FilmScreen
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatToDoTheme(dynamicColor = false) {
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
    }
}
