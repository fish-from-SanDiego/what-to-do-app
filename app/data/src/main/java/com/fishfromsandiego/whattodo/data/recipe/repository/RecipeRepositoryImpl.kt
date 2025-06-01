package com.fishfromsandiego.whattodo.data.recipe.repository

import androidx.core.net.toUri
import com.fishfromsandiego.whattodo.data.recipe.api.RecipeApi
import com.fishfromsandiego.whattodo.data.recipe.dto.RecipeData
import com.fishfromsandiego.whattodo.data.weather.repository.toModel
import com.fishfromsandiego.whattodo.domain.recipe.model.RecipeModel
import com.fishfromsandiego.whattodo.domain.recipe.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl
@Inject constructor(
    val recipeApi: RecipeApi
) : RecipeRepository {
    override suspend fun getRandomRecipe(): Result<RecipeModel> {
        return recipeApi.getRandomRecipe().getOrElse { e ->
            return Result.failure(e)
        }.let { response ->
            Result.success(response.data.first().toModel())
        }
    }
}

fun RecipeData.toModel(): RecipeModel {
    return RecipeModel(
        id = this.recipeId,
        dish = this.dish,
        recipe = this.recipe,
        pictureUri = this.picturePathStr.toUri(),
    )
}