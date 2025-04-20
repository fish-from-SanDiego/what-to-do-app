package com.fishfromsandiego.whattodo.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fishfromsandiego.whattodo.presentation.ui.film.action.FilmScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.film.viewmodel.FilmViewModel
import com.fishfromsandiego.whattodo.presentation.ui.weather.action.WeatherScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.weather.viewmodel.WeatherViewModel
import org.orbitmvi.orbit.compose.collectAsState
import androidx.compose.runtime.getValue
import com.fishfromsandiego.whattodo.presentation.ui.chore.action.ChoresScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.chore.viewmodel.ChoresViewModel
import com.fishfromsandiego.whattodo.presentation.ui.recipe.action.RecipeScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.recipe.viewmodel.RecipeViewModel
import kotlin.Boolean

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.collectAsState()
    TopAppBar(
        title = { Text(WhatToDoAppScreen.Weather.title) },
        modifier = modifier,
        navigationIcon = {},
        actions = {
            RefreshButton(
                enabled = state.weatherModel != null,
                onClick = { viewModel.dispatch(WeatherScreenAction.LoadWeatherModel) }
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopBar(viewModel: RecipeViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.collectAsState()
    TopAppBar(
        title = { Text(WhatToDoAppScreen.Recipe.title) },
        modifier = modifier,
        navigationIcon = {},
        actions = {
            RefreshButton(
                enabled = state.recipeModel != null,
                onClick = { viewModel.dispatch(RecipeScreenAction.LoadRecipeModel) }
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmTopBar(viewModel: FilmViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.collectAsState()
    TopAppBar(
        title = { Text(WhatToDoAppScreen.Film.title) },
        modifier = modifier,
        navigationIcon = {},
        actions = {
            RefreshButton(
                enabled = state.filmModel != null,
                onClick = { viewModel.dispatch(FilmScreenAction.LoadFilmModelWithoutExcludedIfPossible) }
            )
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoresListTopBar(viewModel: ChoresViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.collectAsState()
    TopAppBar(
        title = { Text(WhatToDoAppScreen.ChoresList.title) },
        modifier = modifier,
        navigationIcon = {},
        actions = {
            AddButton(
                onClick = { viewModel.dispatch(ChoresScreenAction.CreateNewChore) },
                contentDesc = "Add new chore",
            )
            RefreshButton(
                enabled = state.chores != null && state.chores!!.isFailure,
                onClick = { viewModel.dispatch(ChoresScreenAction.ListChoresNewFirst) }
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoreEditTopBar(viewModel: ChoresViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.collectAsState()
    TopAppBar(
        title = { Text(WhatToDoAppScreen.EditChore.title) },
        modifier = modifier,
        navigationIcon = {
            GoBackButton(
                onClick = { viewModel.dispatch(ChoresScreenAction.NavigateBackToList) }
            )
        },
        actions = {
            DoneButton(
                onClick = { viewModel.dispatch(ChoresScreenAction.SaveChore) },
                enabled = !state.editChoreTitle.isEmpty() && state.editChoreDateMillis != null,
                contentDesc = "Save chore",
            )
        },
    )
}

@Composable
fun RefreshButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,

    ) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            Icons.Filled.Refresh,
            contentDescription = "Refresh screen",
        )
    }
}

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    contentDesc: String = "Add item",
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,

        ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = contentDesc,
        )
    }
}

@Composable
fun DoneButton(
    modifier: Modifier = Modifier,
    contentDesc: String = "Save item",
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            Icons.Filled.Check,
            contentDescription = contentDesc,
        )
    }
}

@Composable
fun GoBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Go back",
        )
    }
}