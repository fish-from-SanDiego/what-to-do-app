package com.fishfromsandiego.whattodo.presentation.ui.chore.state

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel

data class ChoresUiState(
    val chores: Result<List<ChoreModel>>? = null,
    val expandedIds: Set<Long> = setOf(),
    val isLoading: Boolean = true,
    val editChoreId: Long? = null,
    val editChoreTitle: String = "",
    val editChoreDescription: TextFieldValue = TextFieldValue(
        text = "",
        selection = TextRange.Zero
    ),
    val editChoreDateMillis: Long? = null,
    val titleFocusNotTriggeredYet: Boolean = true,
    val showTitleInputWrong: Boolean = false,
    val showDateWrong: Boolean = false,
    val showPicker: Boolean = false,
)
