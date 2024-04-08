package com.example.simplemorty.domain.useCase.location

import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.LocationsRepository

class GetInfoLocationByIdUseCase (
    val locationsRepository: LocationsRepository
){
    suspend fun getInfoCLocation(id: Int): Location{
        return locationsRepository.getLocationById(id = id)
    }
}

