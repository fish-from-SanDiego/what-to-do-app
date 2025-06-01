package com.fishfromsandiego.whattodo.data.chore.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate

object ChoreTypeConverters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? = date?.toEpochDay()

    @TypeConverter
    fun toLocalDate(epochDay: Long?): LocalDate? =
        epochDay?.let { day -> LocalDate.ofEpochDay(day) }
}

@Entity(
    tableName = "chores",
    indices = [Index(value = ["choreDate"], unique = false)]
)
@TypeConverters(ChoreTypeConverters::class)
data class ChoreEntity(
    @PrimaryKey(autoGenerate = true) val choreId: Long = 0,
    val choreDate: LocalDate,
    val choreTitle: String,
    val choreDescription: String?,
)
