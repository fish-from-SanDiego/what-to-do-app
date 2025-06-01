package com.fishfromsandiego.whattodo.domain.chore.model

import java.time.LocalDate

data class ChoreModel(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val date: LocalDate,
)