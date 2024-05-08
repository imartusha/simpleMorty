package com.example.simplemorty.domain.useCase.location

import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow

class GetInfoLocationByIdUseCase (
    val locationsRepository: LocationsRepository
){
    suspend fun getInfoCLocation(id: Int): Flow<ApiResponse<Location?>> {
        return locationsRepository.getLocationById(id = id)
    }
}

