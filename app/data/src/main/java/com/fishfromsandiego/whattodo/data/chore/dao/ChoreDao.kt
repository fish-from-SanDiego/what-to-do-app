package com.fishfromsandiego.whattodo.data.chore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fishfromsandiego.whattodo.data.chore.entity.ChoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChoreDao {
    @Query("SELECT * FROM chores")
    fun getAllChores(): Flow<List<ChoreEntity>>

    @Query("SELECT * FROM chores ORDER BY chores.choreDate DESC")
    fun getAllChoresSortedByDateDesc(): Flow<List<ChoreEntity>>


    @Query("SELECT * FROM chores WHERE chores.choreId = :id")
    fun getChoreById(id: Long): ChoreEntity

    @Insert
    suspend fun insertChores(vararg chores: ChoreEntity): List<Long>

    @Update
    suspend fun updateChore(chore: ChoreEntity)


}