package com.example.simplemorty.domain.useCase.location

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.example.simplemorty.data.models.entity.episode.toEpisode
import com.example.simplemorty.data.models.entity.location.toLocation
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.LocationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllLocationsUseCase(
    val locationsRepository: LocationsRepository
) {
    fun getAllLocations(): Flow<PagingData<Location>> {
        Log.d("Location", "в юзкейсе ${locationsRepository.getLocations()}")

        return locationsRepository.getLocations()
            .map { pagingData ->
                pagingData.map { cachedLocationsEntity ->
                    cachedLocationsEntity.toLocation()
                }
            }
    }
}