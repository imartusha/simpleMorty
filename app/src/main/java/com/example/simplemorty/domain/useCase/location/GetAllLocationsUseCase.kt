package com.example.simplemorty.domain.useCase.location

import androidx.paging.PagingData
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow

class GetAllLocationsUseCase(
    val locationsRepository: LocationsRepository
) {
    suspend fun getAllLocations(): Flow<PagingData<Location>> {
        return locationsRepository.getLocations()
    }
}