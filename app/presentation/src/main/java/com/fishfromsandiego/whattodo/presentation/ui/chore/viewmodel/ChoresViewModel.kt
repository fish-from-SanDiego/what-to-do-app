package com.fishfromsandiego.whattodo.presentation.ui.chore.viewmodel

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fishfromsandiego.whattodo.common.exceptions.WhatToDoAppCaughtException
import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel
import com.fishfromsandiego.whattodo.domain.chore.usecase.AddAndGetNewChore
import com.fishfromsandiego.whattodo.domain.chore.usecase.EditExistingChore
import com.fishfromsandiego.whattodo.domain.chore.usecase.ListAllChoresNewFirst
import com.fishfromsandiego.whattodo.presentation.R
import com.fishfromsandiego.whattodo.presentation.ui.WhatToDoAppScreen
import com.fishfromsandiego.whattodo.presentation.ui.chore.action.ChoresScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.chore.sideeffect.ChoresSideEffect
import com.fishfromsandiego.whattodo.presentation.ui.chore.state.ChoresUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChoresViewModel
@Inject constructor(
    val listAllChoresNewFirst: ListAllChoresNewFirst,
    val addAndGetNewChore: AddAndGetNewChore,
    val editExistingChore: EditExistingChore,
    initialState: ChoresUiState,
) : ContainerHost<ChoresUiState, ChoresSideEffect>, ViewModel() {
    override val container = container<ChoresUiState, ChoresSideEffect>(initialState)

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        intent {
            reduce {
                state.copy(
                    chores = Result.failure(e),
                    expandedIds = setOf(),
                    isLoading = false,
                )
            }
        }
    }

    init {
        dispatch(ChoresScreenAction.ListChoresNewFirst)
    }

    fun dispatch(action: ChoresScreenAction) {
        when (action) {
            ChoresScreenAction.CreateNewChore -> navigateToChoreCreation()
            is ChoresScreenAction.EditChore -> navigateToChoreEdition(action.chore)
            is ChoresScreenAction.ExpandChoreDescription -> expandChoreDescription(action.choreId)
            ChoresScreenAction.ListChoresNewFirst -> listChores()
            is ChoresScreenAction.CollapseChoreDescription -> collapseChoreDescription(action.choreId)
            is ChoresScreenAction.EditDescription -> editDescription(action.newDescription)
            is ChoresScreenAction.EditTitle -> editTitle(action.newTitle)
            ChoresScreenAction.NavigateBackToList -> navigateBackToList()
            ChoresScreenAction.SaveChore -> saveChore()
            ChoresScreenAction.RegisterTitleFocusTrigger -> registerTitleFocusTrigger()
            is ChoresScreenAction.ChangeShowPicker -> showPicker(action.show)
            is ChoresScreenAction.ChangeDateWrong -> changeDateWrong(action.isWrong)
            is ChoresScreenAction.ChangeTitleWrong -> changeTitleWrong(action.isWrong)
            is ChoresScreenAction.EditDate -> editDate(action.newDateMillis)
        }
    }

    fun handleSideEffect(sideEffect: ChoresSideEffect, navController: NavController) {
        when (sideEffect) {
            ChoresSideEffect.NavigateBackToChoreList -> {
                navController.navigate(WhatToDoAppScreen.ChoresList.name) {
                    popUpTo(WhatToDoAppScreen.EditChore.name) { inclusive = true }
                }
            }

            ChoresSideEffect.NavigateToChoreEdition -> {
                navController.navigate(WhatToDoAppScreen.EditChore.name)
            }
        }

    }

    private fun editDate(newDateMillis: Long?) = intent {
        reduce {
            state.copy(
                editChoreDateMillis = newDateMillis
            )
        }
    }

    private fun saveChore() = intent {
        if (state.editChoreTitle == "" || state.editChoreDateMillis == null) {
            postSideEffect(ChoresSideEffect.NavigateBackToChoreList)
            return@intent
        }
        val choreId = state.editChoreId
        val model = ChoreModel(
            title = state.editChoreTitle,
            description = if (state.editChoreDescription.text != "") state.editChoreDescription.text else null,
            date = localDateFromMillis(state.editChoreDateMillis!!)
        )
        if (choreId == null) {
            addAndGetNewChore(model)
        } else {
            editExistingChore(choreId, model)
        }

        reduce {
            state.copy(
                editChoreId = null,
                editChoreTitle = "",
                editChoreDescription = TextFieldValue("", selection = TextRange.Zero),
                editChoreDateMillis = null,
                titleFocusNotTriggeredYet = true,
                titleInputWrong = false,
                dateWrong = false,
                showPicker = false,
            )
        }

        postSideEffect(ChoresSideEffect.NavigateBackToChoreList)
    }

    private fun navigateBackToList() = intent {
        viewModelScope.launch(exceptionHandler) {
            postSideEffect(ChoresSideEffect.NavigateBackToChoreList)
        }
    }

    private fun registerTitleFocusTrigger() = intent {
        reduce {
            state.copy(titleFocusNotTriggeredYet = false)
        }
    }

    private fun editTitle(newTitle: String) = intent {
        reduce {
            state.copy(
                editChoreTitle = newTitle,
            )
        }
    }

    private fun editDescription(newDescription: TextFieldValue) = intent {
        reduce {
            state.copy(
                editChoreDescription = newDescription,
            )
        }
    }

    private fun expandChoreDescription(choreId: Long) = intent {
        viewModelScope.launch(exceptionHandler) {
            reduce {
                state.copy(
                    expandedIds = state.expandedIds.toMutableSet().also { it.add(choreId) }
                )
            }
        }
    }

    private fun collapseChoreDescription(choreId: Long) = intent {
        viewModelScope.launch(exceptionHandler) {
            reduce {
                state.copy(
                    expandedIds = state.expandedIds.toMutableSet().also { it.remove(choreId) }
                )
            }
        }
    }

    private fun listChores() = intent {
        viewModelScope.launch(exceptionHandler) {
            reduce { state.copy(isLoading = true) }
            listAllChoresNewFirst().catch { e ->
                reduce {
                    state.copy(
                        chores = Result.failure(e),
                        expandedIds = setOf(),
                        isLoading = false
                    )
                }
            }
                .collect { newChores ->
                    val newExpandedIds = newChores
                        .mapNotNull { it.id.takeIf { id -> state.expandedIds.contains(id) } }
                        .toSet()

                    reduce {
                        state.copy(
                            chores = Result.success(newChores),
                            expandedIds = newExpandedIds,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun changeTitleWrong(isWrong: Boolean) = intent {
        reduce {
            state.copy(
                titleInputWrong = isWrong
            )
        }
    }

    private fun changeDateWrong(isWrong: Boolean) = intent {
        reduce {
            state.copy(
                dateWrong = isWrong
            )
        }
    }

    private fun showPicker(show: Boolean) = intent {
        reduce {
            state.copy(
                showPicker = show
            )
        }
    }

    private fun navigateToChoreCreation() = intent {
        reduce {
            state.copy(
                editChoreId = null,
                editChoreTitle = "",
                editChoreDescription = TextFieldValue(
                    text = "",
                    selection = TextRange.Zero
                ),
                editChoreDateMillis = null,
                titleFocusNotTriggeredYet = true,
                titleInputWrong = false,
                dateWrong = false,
                showPicker = false,
            )
        }
        viewModelScope.launch(exceptionHandler) {
            postSideEffect(ChoresSideEffect.NavigateToChoreEdition)
        }
    }

    private fun navigateToChoreEdition(chore: ChoreModel) = intent {
        viewModelScope.launch(exceptionHandler) {
            reduce {
                state.copy(
                    editChoreId = chore.id,
                    editChoreTitle = chore.title,
                    editChoreDescription = TextFieldValue(
                        text = chore.description ?: "",
                        selection = TextRange.Zero
                    ),
                    editChoreDateMillis = localDateToMillis(chore.date),
                    titleFocusNotTriggeredYet = true,
                    titleInputWrong = false,
                    dateWrong = false,
                    showPicker = false,
                )
            }
            postSideEffect(ChoresSideEffect.NavigateToChoreEdition)
        }
    }


    private fun localDateFromMillis(millis: Long): LocalDate {
        return Instant.ofEpochMilli(millis)
            .atZone(ZoneOffset.UTC).toLocalDate()
    }

    fun localDateToMillis(date: LocalDate): Long {
        return date.atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
    }
}