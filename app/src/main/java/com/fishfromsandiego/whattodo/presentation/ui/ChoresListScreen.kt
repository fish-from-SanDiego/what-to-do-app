package com.fishfromsandiego.whattodo.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.fishfromsandiego.whattodo.R
import com.fishfromsandiego.whattodo.data.Chore
import com.fishfromsandiego.whattodo.data.WeatherInfo
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ChoresListScreen(chores: List<Chore>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
    ) {
        items(chores) { chore ->
            var expanded by rememberSaveable { mutableStateOf(false) }
            ChoreItem(
                chore,
                expanded = expanded,
                onExpandButtonClick = { expanded = !expanded },
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun ChoreItem(
    chore: Chore,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onExpandButtonClick: () -> Unit = {},
) {
    val canExpand = chore.description != null
    Card(modifier = modifier) {
        val constraints = ConstraintSet {
            val title = createRefFor("title")
            val description = createRefFor("desc")
            val expandButton = createRefFor("expand")
            val date = createRefFor("date")
            val divider = createRefFor("hr")

            constrain(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
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
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
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
            if (canExpand) {
                ExpandButton(
                    onClick = onExpandButtonClick,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
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
                        text = chore.description,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 1.2.em,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                            .layoutId("desc")
                    )
                }
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
            )
        else
            Icon(
                Icons.Filled.ExpandMore,
                contentDescription = stringResource(R.string.expand_chore_description),
                tint = tint,
            )
    }
}

private val previewChores = listOf(
    Chore(
        title = "I have a description",
        description = "Description",
        date = LocalDate.of(2025, 12, 12)
    ),
    Chore(
        title = "I have a description",
        description = "Longer desc Longer desc Longer desc Longer desc"
                + " Longer desc Longer desc Longer desc Longer desc",
        date = LocalDate.of(2025, 12, 13)
    ),
    Chore(
        title = "And I do not",
        date = LocalDate.of(2025, 3, 11)
    ),
)

@Preview
@Composable
fun ChoresScreenPreview() {
    WhatToDoTheme(darkTheme = false, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ChoresListScreen(
                previewChores,
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
            ChoresListScreen(
                previewChores,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}