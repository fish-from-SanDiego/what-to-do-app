package com.fishfromsandiego.whattodo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fishfromsandiego.whattodo.data.chore.dao.ChoreDao
import com.fishfromsandiego.whattodo.data.chore.entity.ChoreEntity

@Database(entities = [ChoreEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun choreDao(): ChoreDao
}