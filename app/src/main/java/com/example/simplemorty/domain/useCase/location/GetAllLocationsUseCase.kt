package com.example.simplemorty.domain.useCase.location

import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.LocationsRepository

class GetAllLocationsUseCase(
    val locationsRepository: LocationsRepository
) {
    suspend fun getAllLocations(): List<Location> {
        return locationsRepository.getAllLocations()
    }
}