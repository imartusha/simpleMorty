package com.example.simplemorty.data.repository

import com.example.simplemorty.data.models.dto.location.mapDtoToEntityLocationList
import com.example.simplemorty.data.models.dto.location.mapDtoToLocationList
import com.example.simplemorty.data.models.dto.location.mapLocationDtoToDomain
import com.example.simplemorty.data.network.api.location.LocationApi
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.LocationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class LocationsRepositoryImpl(
    private val locationApi: LocationApi
) : LocationsRepository {

    override suspend fun getLocationById(id: Int): Location {
        return mapLocationDtoToDomain(locationApi.getLocationById(id))
    }

    override suspend fun getAllLocations(): List<Location> {
        return withContext(Dispatchers.IO) {
            val commonResponse = locationApi.getAllLocations()
            val locationsDtoList = commonResponse.results
            val locationsEntities = mapDtoToEntityLocationList(locationsDtoList)

            mapDtoToLocationList(locations = locationsDtoList)
        }
    }
}