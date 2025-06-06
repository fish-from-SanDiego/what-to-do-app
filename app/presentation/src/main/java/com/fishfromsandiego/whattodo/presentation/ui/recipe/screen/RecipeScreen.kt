package com.fishfromsandiego.whattodo.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.fishfromsandiego.whattodo.common.exceptions.getUserMessage
import com.fishfromsandiego.whattodo.domain.recipe.model.RecipeModel
import com.fishfromsandiego.whattodo.presentation.R
import com.fishfromsandiego.whattodo.presentation.ui.recipe.state.RecipeUiState
import com.fishfromsandiego.whattodo.presentation.ui.recipe.viewmodel.RecipeViewModel
import com.fishfromsandiego.whattodo.presentation.ui.theme.WhatToDoTheme
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.collectAsState()
    RecipeScreenContent(state, modifier)
}

@Composable
fun RecipeScreenContent(recipeState: RecipeUiState, modifier: Modifier = Modifier) {
    if (recipeState.isRecipeModelLoading) {
        ProgressIndicator()
    } else {
        recipeState.recipeModel!!.onFailure { e ->
            ErrorIcon(errorText = e.getUserMessage(), modifier = Modifier.fillMaxSize())
        }.onSuccess { recipeModel ->
            Column(modifier = modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CardDefaults.shape)
                        .background(color = MaterialTheme.colorScheme.primaryContainer),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Text(
                        text = recipeModel.dish,
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        letterSpacing = (-0.02).em,
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    AsyncImage(
                        model = recipeModel.pictureUri,
                        placeholder = imagePlaceholder(R.drawable.recipe_preview_meal),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                            .size(100.dp)
                            .clip(CardDefaults.shape)
                    )
                }
                val decriptionScrollState = rememberScrollState()
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .clip(CardDefaults.shape)
                        .fillMaxWidth()
                        .verticalScroll(decriptionScrollState)
                        .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
                ) {
                    Text(
                        text = recipeModel.recipe,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(8.dp)

                    )
                }
            }

        }
    }
}


object RecipeBottomBarItem : BottomNavigationItem {
    override val screen = WhatToDoAppScreen.Recipe
    override val selectedIcon = Icons.Filled.Cookie
    override val unselectedIcon = Icons.Outlined.Cookie
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    WhatToDoTheme(darkTheme = false, dynamicColor = false) {
        Surface {
            RecipeScreenContent(
                recipeState = RecipeUiState(
                    recipeModel = Result.success(
                        RecipeModel(
                            dish = "SPAGHETTI",
                            recipe = "STEP 1\\r\\nPut a large saucepan of water on to boil.\\r\\n\\r\\nSTEP 2\\r\\nFinely chop the 100g pancetta, having first removed any rind. Finely grate 50g pecorino cheese and 50g parmesan and mix them together.\\r\\n\\r\\nSTEP 3\\r\\nBeat the 3 large eggs in a medium bowl and season with a little freshly grated black pepper. Set everything aside.\\r\\n\\r\\nSTEP 4\\r\\nAdd 1 tsp salt to the boiling water, add 350g spaghetti and when the water comes back to the boil, cook at a constant simmer, covered, for 10 minutes or until al dente (just cooked).",
                            pictureUri = "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg".toUri()
                        )
                    ),
                    isRecipeModelLoading = false,
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreviewDark() {
    WhatToDoTheme(darkTheme = true, dynamicColor = false) {
        Surface {
            RecipeScreenContent(
                recipeState = RecipeUiState(
                    recipeModel = Result.success(
                        RecipeModel(
                            dish = "SPAGHETTI",
                            recipe = "STEP 1\\r\\nPut a large saucepan of water on to boil.\\r\\n\\r\\nSTEP 2\\r\\nFinely chop the 100g pancetta, having first removed any rind. Finely grate 50g pecorino cheese and 50g parmesan and mix them together.\\r\\n\\r\\nSTEP 3\\r\\nBeat the 3 large eggs in a medium bowl and season with a little freshly grated black pepper. Set everything aside.\\r\\n\\r\\nSTEP 4\\r\\nAdd 1 tsp salt to the boiling water, add 350g spaghetti and when the water comes back to the boil, cook at a constant simmer, covered, for 10 minutes or until al dente (just cooked).",
                            pictureUri = "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg".toUri()
                        )
                    ),
                    isRecipeModelLoading = false,
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}