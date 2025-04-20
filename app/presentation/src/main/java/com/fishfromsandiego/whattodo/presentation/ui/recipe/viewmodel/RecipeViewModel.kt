package com.fishfromsandiego.whattodo.presentation.ui.recipe.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fishfromsandiego.whattodo.domain.recipe.usecase.GetRandomRecipe
import com.fishfromsandiego.whattodo.presentation.ui.recipe.action.RecipeScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.recipe.sideeffect.RecipeScreenSideEffect
import com.fishfromsandiego.whattodo.presentation.ui.recipe.state.RecipeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    val getRandomRecipe: GetRandomRecipe,
    initialState: RecipeUiState,
) : ContainerHost<RecipeUiState, RecipeScreenSideEffect>, ViewModel() {
    override val container: Container<RecipeUiState, RecipeScreenSideEffect> =
        container<RecipeUiState, RecipeScreenSideEffect>(initialState)

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        intent {
            reduce {
                state.copy(
                    recipeModel = Result.failure(e),
                    isRecipeModelLoading = false,
                )
            }
        }
    }

    init {
        dispatch(RecipeScreenAction.LoadRecipeModel)
    }


    fun dispatch(action: RecipeScreenAction) {
        when (action) {
            RecipeScreenAction.LoadRecipeModel -> loadRecipeModel()
        }
    }

    private fun loadRecipeModel() = intent {
        viewModelScope.launch(exceptionHandler) {
            reduce { state.copy(isRecipeModelLoading = true) }
            val recipeModel = getRandomRecipe()
            if (recipeModel.isFailure) throw recipeModel.exceptionOrNull()!!
            reduce { state.copy(isRecipeModelLoading = false, recipeModel = recipeModel) }
        }
    }

}