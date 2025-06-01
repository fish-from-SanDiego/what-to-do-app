package com.fishfromsandiego.whattodo.presentation.ui.chore.list.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.fishfromsandiego.whattodo.common.exceptions.getUserMessage
import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel
import com.fishfromsandiego.whattodo.presentation.R
import com.fishfromsandiego.whattodo.presentation.ui.BottomNavigationItem
import com.fishfromsandiego.whattodo.presentation.ui.ErrorIcon
import com.fishfromsandiego.whattodo.presentation.ui.ProgressIndicator
import com.fishfromsandiego.whattodo.presentation.ui.WhatToDoAppScreen
import com.fishfromsandiego.whattodo.presentation.ui.chore.action.ChoresScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.chore.state.ChoresUiState
import com.fishfromsandiego.whattodo.presentation.ui.chore.viewmodel.ChoresViewModel
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ChoresListScreen(
    viewModel: ChoresViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val state by viewModel.collectAsState()
    ChoresListScreenContent(
        state, modifier,
        dispatch = viewModel::dispatch
    )

    viewModel.collectSideEffect {
        viewModel.handleSideEffect(it, navController)
    }
}

@Composable
fun ChoresListScreenContent(
    state: ChoresUiState,
    modifier: Modifier = Modifier,
    dispatch: (ChoresScreenAction) -> Unit = { _ -> },
) {
    if (state.isLoading) {
        ProgressIndicator()
    } else {
        state.chores!!.onFailure { e ->
            ErrorIcon(errorText = e.getUserMessage(), modifier = Modifier.fillMaxSize())
        }.onSuccess { chores ->
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),

                ) {
                items(chores) { chore ->
                    val expanded = state.expandedIds.contains(chore.id)
                    ChoreItem(
                        chore,
                        expanded = expanded,
                        onExpandButtonClick = {
                            if (!expanded) {
                                dispatch(
                                    ChoresScreenAction.ExpandChoreDescription(
                                        chore.id
                                    )
                                )
                            } else {
                                dispatch(ChoresScreenAction.CollapseChoreDescription(chore.id))
                            }
                        },
                        onEditButtonClick = {
                            dispatch(ChoresScreenAction.EditChore(chore))
                        },
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp)
                            .fillMaxWidth()
                    )

                }
            }
        }
    }
}

@Composable
fun ChoreItem(
    chore: ChoreModel,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onExpandButtonClick: () -> Unit = {},
    onEditButtonClick: () -> Unit = {},
) {
    val canExpand = chore.description != null
    Card(modifier = modifier) {
        val constraints = ConstraintSet {
            val title = createRefFor("title")
            val description = createRefFor("desc")
            val expandButton = createRefFor("expand")
            val editButton = createRefFor("edit")
            val date = createRefFor("date")
            val divider = createRefFor("hr")

            constrain(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }

            constrain(editButton) {
                top.linkTo(parent.top)
                end.linkTo(expandButton.start)
            }

            constrain(expandButton) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
            constrain(date) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)

            }
            constrain(divider) {
                top.linkTo(date.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
            constrain(description) {
                top.linkTo(divider.bottom)
                start.linkTo(parent.start)
            }

        }
        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
        ) {
            Text(
                text = chore.title,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.headlineSmall,
                letterSpacing = (-0.02).em,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 2.dp)
                    .layoutId("title"),
            )
            Text(
                text = chore.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 4.dp)
                    .layoutId("date"),
            )
            EditButton(
                onClick = onEditButtonClick,
                modifier = Modifier
                    .size(45.dp)
                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 2.dp)
                    .layoutId("edit"),
            )
            if (canExpand) {
                ExpandButton(
                    onClick = onExpandButtonClick,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(top = 8.dp, bottom = 8.dp, end = 8.dp, start = 2.dp)
                        .layoutId("expand"),
                    expanded = expanded,
                )
                if (expanded) {
                    HorizontalDivider(
                        modifier = Modifier
                            .layoutId("hr")
                            .padding(start = 8.dp, end = 8.dp, bottom = 2.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                    )
                    Text(
                        text = chore.description!!,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 1.2.em,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                            .layoutId("desc")
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .padding(top = 8.dp, bottom = 8.dp, end = 8.dp, start = 2.dp)
                        .layoutId("expand")
                )
            }
        }
    }
}

@Composable
fun ExpandButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    expanded: Boolean = false
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        val tint = MaterialTheme.colorScheme.secondary
        if (expanded)
            Icon(
                Icons.Filled.ExpandLess,
                contentDescription = stringResource(R.string.hide_chore_description),
                tint = tint,
                modifier = Modifier.fillMaxSize()
            )
        else
            Icon(
                Icons.Filled.ExpandMore,
                contentDescription = stringResource(R.string.expand_chore_description),
                tint = tint,
                modifier = Modifier.fillMaxSize()
            )
    }
}

@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        val tint = MaterialTheme.colorScheme.secondary
        Icon(
            Icons.Filled.Edit,
            contentDescription = "Edit chore",
            tint = tint,
            modifier = Modifier.fillMaxSize()
        )
    }
}

private val previewChores = listOf(
    ChoreModel(
        title = "I have a description",
        description = "Description",
        date = LocalDate.of(2025, 12, 12)
    ),
    ChoreModel(
        title = "I have a description",
        description = "Longer desc Longer desc Longer desc Longer desc"
                + " Longer desc Longer desc Longer desc Longer desc",
        date = LocalDate.of(2025, 12, 13)
    ),
    ChoreModel(
        title = "And I do not",
        date = LocalDate.of(2025, 3, 11)
    ),
)

object ChoresListBottomBarItem : BottomNavigationItem {
    override val screen = WhatToDoAppScreen.ChoresList
    override val selectedIcon = Icons.Filled.Home
    override val unselectedIcon = Icons.Outlined.Home
}

@Preview
@Composable
fun ChoresScreenPreview() {
    WhatToDoTheme(darkTheme = false, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ChoresListScreenContent(
                ChoresUiState(
                    chores = Result.success(previewChores),
                    isLoading = false
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun ChoresScreenDarkPreview() {
    WhatToDoTheme(darkTheme = true, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ChoresListScreenContent(
                ChoresUiState(
                    chores = Result.success(previewChores),
                    isLoading = false
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}