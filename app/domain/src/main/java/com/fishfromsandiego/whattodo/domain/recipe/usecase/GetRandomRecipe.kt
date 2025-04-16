package com.fishfromsandiego.whattodo.domain.recipe.usecase

import com.fishfromsandiego.whattodo.domain.recipe.repository.RecipeRepository
import javax.inject.Inject

class GetRandomRecipe @Inject constructor(val repository: RecipeRepository) {
    suspend operator fun invoke() = repository.getRandomRecipe()
}