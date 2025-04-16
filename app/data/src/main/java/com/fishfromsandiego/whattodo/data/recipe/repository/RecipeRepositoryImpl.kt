package com.fishfromsandiego.whattodo.data.recipe.repository

import androidx.core.net.toUri
import com.fishfromsandiego.whattodo.domain.recipe.model.RecipeModel
import com.fishfromsandiego.whattodo.domain.recipe.repository.RecipeRepository

class RecipeRepositoryImpl : RecipeRepository {
    override suspend fun getRandomRecipe(): Result<RecipeModel> {
        return Result.success(
            RecipeModel(
                dish = "SPAGHETTI",
                recipe = "STEP 1\\r\\nPut a large saucepan of water on to boil.\\r\\n\\r\\nSTEP 2\\r\\nFinely chop the 100g pancetta, having first removed any rind. Finely grate 50g pecorino cheese and 50g parmesan and mix them together.\\r\\n\\r\\nSTEP 3\\r\\nBeat the 3 large eggs in a medium bowl and season with a little freshly grated black pepper. Set everything aside.\\r\\n\\r\\nSTEP 4\\r\\nAdd 1 tsp salt to the boiling water, add 350g spaghetti and when the water comes back to the boil, cook at a constant simmer, covered, for 10 minutes or until al dente (just cooked).",
                pictureUri = "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg".toUri()
            )
        )
    }
}