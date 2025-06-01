package com.fishfromsandiego.whattodo.domain.chore.usecase

import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import javax.inject.Inject

class ListAllChoresNewFirst @Inject constructor(
    val repository: ChoreRepository
) {
    operator fun invoke() =
        repository.getAllChoresOrderedByDateDesc()
}