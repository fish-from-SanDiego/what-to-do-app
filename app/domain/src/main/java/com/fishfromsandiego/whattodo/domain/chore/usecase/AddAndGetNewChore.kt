package com.fishfromsandiego.whattodo.domain.chore.usecase

import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel
import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import javax.inject.Inject

class AddAndGetNewChore @Inject constructor(
    val repository: ChoreRepository
) {
    suspend operator fun invoke(value: ChoreModel) =
        repository.createandGetChore(value)
}