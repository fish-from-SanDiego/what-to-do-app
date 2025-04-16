package com.fishfromsandiego.whattodo.data.chore.repository

import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel
import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import java.time.LocalDate

class ChoreRepositoryImpl : ChoreRepository {

    private var nextChoreId: Int = 0
    private val chores = mutableMapOf<Int, ChoreModel>()

    //    not thread safe at all
    private fun addChore(chore: ChoreModel): ChoreModel {
        val id = nextChoreId
        ++nextChoreId
        val value = chore.copy(id = id)
        chores.set(id, value)
        return value
    }

    init {
        addChore(
            ChoreModel(
                title = "I have a description",
                description = "Description",
                date = LocalDate.of(2025, 12, 12)
            )
        )
        addChore(
            ChoreModel(
                title = "I have a description",
                description = "Longer desc Longer desc Longer desc Longer desc"
                        + " Longer desc Longer desc Longer desc Longer desc",
                date = LocalDate.of(2025, 12, 13)
            )
        )
        addChore(
            ChoreModel(
                title = "And I do not",
                date = LocalDate.of(2025, 3, 11)
            )
        )
    }

    override suspend fun updateChore(
        id: Int,
        newValue: ChoreModel
    ): Result<Unit> {
        try {
            chores.set(id, newValue.copy(id = id))
        } catch (e: Throwable) {
            return Result.failure(e)
        }
        return Result.success(Unit)
    }

    override suspend fun createandGetChore(value: ChoreModel): Result<ChoreModel> {
        return Result.success(addChore(value))
    }

    override suspend fun createChore(value: ChoreModel): Result<Unit> {
        addChore(value)
        return Result.success(Unit)
    }

    override suspend fun getAllChores(): List<ChoreModel> {
        return chores.values.toList()
    }

    override suspend fun getAllChoresOrderedByDateDesc(): List<ChoreModel> {
        return chores.values.sortedByDescending { it.date }.toList()
    }

}