package com.fishfromsandiego.whattodo.domain.chore.repository

import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel

interface ChoreRepository {
    suspend fun updateChore(id: Int, newValue: ChoreModel): Result<Unit>
    suspend fun createandGetChore(value: ChoreModel): Result<ChoreModel>
    suspend fun createChore(value: ChoreModel): Result<Unit>
    suspend fun getAllChores(): List<ChoreModel>
    suspend fun getAllChoresOrderedByDateDesc(): List<ChoreModel>
}