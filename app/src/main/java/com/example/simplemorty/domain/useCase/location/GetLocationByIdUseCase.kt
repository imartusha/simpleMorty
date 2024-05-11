package com.example.simplemorty.domain.useCase.location

import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow

class GetLocationByIdUseCase (
    val locationsRepository: LocationsRepository
){
    fun getLocationById(id: Int): Flow<ApiResponse<Location?>> {
        return locationsRepository.getLocationById(id = id)
    }
    suspend fun getLocationByIdFromDb(id: Int): Location?{
        return locationsRepository.getLocationByIdFromDb(id = id)
    }
}

