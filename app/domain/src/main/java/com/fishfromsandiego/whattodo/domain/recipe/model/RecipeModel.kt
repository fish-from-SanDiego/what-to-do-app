package com.fishfromsandiego.whattodo.domain.recipe.model

import android.net.Uri

data class RecipeModel(
    val id: Int = 0,
    val dish: String,
    val recipe: String,
    val pictureUri: Uri,
)