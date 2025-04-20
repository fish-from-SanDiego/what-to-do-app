package com.fishfromsandiego.whattodo.data.recipe.api

import com.fishfromsandiego.whattodo.data.recipe.dto.RecipeDataResponse

interface RecipeApi {
    suspend fun getRandomRecipe() : Result<RecipeDataResponse>
}