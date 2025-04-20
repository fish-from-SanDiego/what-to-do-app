package com.fishfromsandiego.whattodo.data.location

class LocationProviderImpl : LocationProvider {
    override suspend fun getLocation(): Result<Location> {
        return Result.success(
            Location(
                latitude = 59.95,
                longitude = 30.31,
            )
        )
    }
}