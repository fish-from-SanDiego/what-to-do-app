package com.fishfromsandiego.whattodo.data.chore.repository

import android.util.Log
import com.fishfromsandiego.whattodo.common.Constants
import com.fishfromsandiego.whattodo.common.exceptions.WhatToDoAppCaughtException
import com.fishfromsandiego.whattodo.data.chore.dao.ChoreDao
import com.fishfromsandiego.whattodo.data.chore.entity.ChoreEntity
import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel
import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChoreRepositoryImpl
@Inject constructor(
    val dao: ChoreDao
) : ChoreRepository {


    override suspend fun updateChore(
        id: Long,
        newValue: ChoreModel
    ): Result<Unit> {
        return try {
            dao.updateChore(newValue.toEntity().copy(choreId = id))
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(Constants.LOG_TAG, e.message ?: "unkonwn error")
            Result.failure(WhatToDoAppCaughtException("Couldn't update chore properly"))
        }
    }

    override suspend fun createandGetChore(value: ChoreModel): Result<ChoreModel> {
        return try {
            val newValueId = dao.insertChores(value.toEntity()).first()
            Result.success(value.copy(id = newValueId))
        } catch (e: Throwable) {
            Log.d(Constants.LOG_TAG, e.message ?: "unkonwn error")
            Result.failure(WhatToDoAppCaughtException("Couldn't create new chore"))
        }
    }

    override suspend fun createChore(value: ChoreModel): Result<Unit> {
        return try {
            dao.insertChores(value.toEntity()).first()
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(Constants.LOG_TAG, e.message ?: "unkonwn error")
            Result.failure(WhatToDoAppCaughtException("Couldn't create new chore"))
        }
    }

    override fun getAllChores(): Flow<List<ChoreModel>> {
        return dao.getAllChores().map { results ->
            results.map { it -> it.toModel() }
        }
    }

    override fun getAllChoresOrderedByDateDesc(): Flow<List<ChoreModel>> {
        return dao.getAllChoresSortedByDateDesc().map { results ->
            results.map { it -> it.toModel() }
        }
    }

}

fun ChoreModel.toEntity(): ChoreEntity {
    return ChoreEntity(
        choreDate = this.date,
        choreTitle = this.title,
        choreDescription = this.description
    )
}

fun ChoreEntity.toModel(): ChoreModel {
    return ChoreModel(
        id = this.choreId,
        date = this.choreDate,
        title = this.choreTitle,
        description = this.choreDescription
    )
}