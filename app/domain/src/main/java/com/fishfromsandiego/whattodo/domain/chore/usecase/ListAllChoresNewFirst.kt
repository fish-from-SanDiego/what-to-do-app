package com.fishfromsandiego.whattodo.domain.chore.usecase

import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import javax.inject.Inject

class ListAllChoresNewFirst @Inject constructor(
    val repository: ChoreRepository
) {
    suspend operator fun invoke(pageSize: Int) =
        repository.getAllChoresOrderedByDateDesc()
}