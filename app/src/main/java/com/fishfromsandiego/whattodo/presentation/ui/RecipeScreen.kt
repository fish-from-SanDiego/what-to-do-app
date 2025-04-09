package com.fishfromsandiego.whattodo.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.fishfromsandiego.whattodo.R
import com.fishfromsandiego.whattodo.data.CookingRecipe
import com.fishfromsandiego.whattodo.data.WeatherInfo
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import java.util.Locale

@Composable
fun RecipeScreen(recipe: CookingRecipe, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CardDefaults.shape)
                .background(color = MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = recipe.dish.uppercase(),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                letterSpacing = (-0.02).em,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Image(
                painter = recipe.picture,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                    .clip(CardDefaults.shape)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .clip(CardDefaults.shape)
                .background(color = MaterialTheme.colorScheme.tertiaryContainer)
//                .fillMaxHeight()
        ) {
            Text(
                text = recipe.recipe,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .padding(8.dp)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    Surface(color = MaterialTheme.colorScheme.background) {
        WhatToDoTheme(darkTheme = false, dynamicColor = false) {
            Surface {
                RecipeScreen(
                    recipe = CookingRecipe(
                        dish = stringResource(R.string.recipe_preview_dish),
                        recipe = stringResource(R.string.recipe_preview_desc),
                        picture = painterResource(R.drawable.recipe_preview_meal),
                    ),
                    modifier = Modifier.fillMaxSize(),

                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreviewDark() {
    Surface(color = MaterialTheme.colorScheme.background) {
        WhatToDoTheme(darkTheme = true, dynamicColor = false) {
            Surface {
                RecipeScreen(
                    recipe = CookingRecipe(
                        dish = stringResource(R.string.recipe_preview_dish),
                        recipe = stringResource(R.string.recipe_preview_desc),
                        picture = painterResource(R.drawable.recipe_preview_meal),
                    ),
                    modifier = Modifier.fillMaxSize(),

                    )
            }
        }
    }
}