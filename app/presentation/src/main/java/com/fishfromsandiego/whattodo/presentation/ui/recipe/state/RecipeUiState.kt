package com.fishfromsandiego.whattodo.presentation.ui.recipe.state

import com.fishfromsandiego.whattodo.domain.recipe.model.RecipeModel

data class RecipeUiState(
    val recipeModel: Result<RecipeModel>? = null,
    val isRecipeModelLoading: Boolean = true,
    val isImageLoading: Boolean = true,
)