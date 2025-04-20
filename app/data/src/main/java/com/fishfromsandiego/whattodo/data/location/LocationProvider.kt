package com.fishfromsandiego.whattodo.data.location

data class Location(val latitude: Double, val longitude: Double)

interface LocationProvider {
    suspend fun getLocation(): Result<Location>
}