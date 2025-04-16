package com.fishfromsandiego.whattodo.presentation.ui.recipe.action


sealed class RecipeScreenAction {
    data object LoadRecipeModel : RecipeScreenAction()
}