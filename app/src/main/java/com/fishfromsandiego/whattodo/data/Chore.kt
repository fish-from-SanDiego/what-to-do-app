package com.fishfromsandiego.whattodo.data

import java.time.LocalDate
import java.util.Date

data class Chore(val title: String, val description: String? = null, val date: LocalDate)
