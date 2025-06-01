package com.fishfromsandiego.whattodo.presentation.ui.chore.action

import androidx.compose.ui.text.input.TextFieldValue
import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel

sealed class ChoresScreenAction {
    data object ListChoresNewFirst : ChoresScreenAction()
    data object CreateNewChore : ChoresScreenAction()
    data class EditChore(val chore: ChoreModel) : ChoresScreenAction()
    data class ExpandChoreDescription(val choreId: Long) : ChoresScreenAction()
    data class CollapseChoreDescription(val choreId: Long) : ChoresScreenAction()
    data class EditTitle(val newTitle: String) : ChoresScreenAction()
    data class EditDate(val newDateMillis: Long?) : ChoresScreenAction()
    data class EditDescription(val newDescription: TextFieldValue) : ChoresScreenAction()
    data class ChangeShowPicker(val show: Boolean) : ChoresScreenAction()
    data object RegisterTitleFocusTrigger : ChoresScreenAction()
    data class ChangeTitleWrong(val isWrong: Boolean) : ChoresScreenAction()
    data class ChangeDateWrong(val isWrong: Boolean) : ChoresScreenAction()
    data class EditTitleAndUpdateShowWrong(val newTitle: String) : ChoresScreenAction()
    data class SelectDate(val newDateMillis: Long?) : ChoresScreenAction()

    data object SaveChore : ChoresScreenAction()
    data object NavigateBackToList : ChoresScreenAction()
}