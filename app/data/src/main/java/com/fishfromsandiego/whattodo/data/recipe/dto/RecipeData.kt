package com.fishfromsandiego.whattodo.data.recipe.dto

import android.net.Uri
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class RecipeData(
    @SerialName("idMeal") val recipeId: Int,
    @SerialName("strMeal") val dish: String,
    @SerialName("strInstructions") val recipe: String,
    @SerialName("strMealThumb") val picturePathStr: String,
)
