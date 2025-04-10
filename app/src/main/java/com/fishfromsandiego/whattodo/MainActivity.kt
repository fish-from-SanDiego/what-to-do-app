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
import com.fishfromsandiego.whattodo.presentation.ui.ChoreEditScreen
import com.fishfromsandiego.whattodo.presentation.ui.WeatherScreen
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatToDoTheme(darkTheme = false, dynamicColor = false) {
                Scaffold { innerPadding ->
                    ChoreEditScreen(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize())
                }
            }
        }
    }
}
