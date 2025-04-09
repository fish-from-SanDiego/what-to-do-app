package com.fishfromsandiego.whattodo.data

import java.time.LocalDate

data class Chore(val title: String, val description: String? = null, val date: LocalDate)
