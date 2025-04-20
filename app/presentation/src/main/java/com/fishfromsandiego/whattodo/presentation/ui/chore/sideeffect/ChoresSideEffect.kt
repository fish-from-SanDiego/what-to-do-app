package com.fishfromsandiego.whattodo.presentation.ui.chore.sideeffect

sealed class ChoresSideEffect {
    data object NavigateBackToChoreList : ChoresSideEffect()
    data object NavigateToChoreEdition: ChoresSideEffect()
}