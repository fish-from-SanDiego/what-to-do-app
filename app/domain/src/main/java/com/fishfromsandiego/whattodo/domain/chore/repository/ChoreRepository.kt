package com.fishfromsandiego.whattodo.domain.chore.repository

import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel
import kotlinx.coroutines.flow.Flow

interface ChoreRepository {
    suspend fun updateChore(id: Long, newValue: ChoreModel): Result<Unit>
    suspend fun createandGetChore(value: ChoreModel): Result<ChoreModel>
    suspend fun createChore(value: ChoreModel): Result<Unit>
    fun getAllChores(): Flow<List<ChoreModel>>
    fun getAllChoresOrderedByDateDesc(): Flow<List<ChoreModel>>
}