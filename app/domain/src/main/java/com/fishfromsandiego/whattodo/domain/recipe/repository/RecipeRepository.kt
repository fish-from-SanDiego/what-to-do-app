package com.fishfromsandiego.whattodo.domain.recipe.repository

import com.fishfromsandiego.whattodo.domain.recipe.model.RecipeModel

interface RecipeRepository {
    suspend fun getRandomRecipe() : Result<RecipeModel>
}