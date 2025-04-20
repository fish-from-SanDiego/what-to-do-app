package com.fishfromsandiego.whattodo.presentation.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fishfromsandiego.whattodo.presentation.ui.film.screen.FilmBottomBarItem
import com.fishfromsandiego.whattodo.presentation.ui.film.screen.FilmScreen
import com.fishfromsandiego.whattodo.presentation.ui.film.viewmodel.FilmViewModel
import com.fishfromsandiego.whattodo.presentation.ui.recipe.viewmodel.RecipeViewModel
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import com.fishfromsandiego.whattodo.presentation.ui.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val recipeViewModel: RecipeViewModel by viewModels()
    private val filmViewModel: FilmViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatToDoTheme(dynamicColor = false) {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen =
                    WhatToDoAppScreen.valueOf(
                        backStackEntry?.destination?.route ?: WhatToDoAppScreen.Weather.name
                    )
                LaunchedEffect(true) {
                    navController.currentBackStackEntryFlow.collect { entry ->
                        navController.graph.findStartDestination()
                        Log.d(
                            "MainActivity",
                            "id: ${entry.id}; destination: ${entry.destination.id}; $entry."
                        )
                    }
                }
                Scaffold(
                    bottomBar = {
                        WhatToDoAppBottomBar(
                            navItems = arrayOf(
                                WeatherBottomBarItem,
                                RecipeBottomBarItem,
                                FilmBottomBarItem
                            ),
                            currentScreen = currentScreen,
                            onClick = { screen ->
                                navController.navigate(screen.name) {
                                    launchSingleTop = true
                                    popUpTo(currentScreen.name) { inclusive = true }
                                }
                            },
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = WhatToDoAppScreen.Weather.name,
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        composable(route = WhatToDoAppScreen.Weather.name) {
                            WeatherScreen(weatherViewModel, Modifier.fillMaxSize())
                        }
                        composable(route = WhatToDoAppScreen.Recipe.name) {
                            RecipeScreen(recipeViewModel, Modifier.fillMaxSize())
                        }
                        composable(route = WhatToDoAppScreen.Film.name) {
                            FilmScreen(filmViewModel, Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}