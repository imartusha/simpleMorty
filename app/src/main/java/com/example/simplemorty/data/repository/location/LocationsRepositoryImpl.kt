package com.example.simplemorty.data.repository.location

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.dto.location.mapToLocationResponse
import com.example.simplemorty.data.models.entity.location.LocationEntity
import com.example.simplemorty.data.models.entity.location.toLocation
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.data.network.api.location.LocationApi
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.LocationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.*
import com.example.simplemorty.utils.result
import kotlinx.coroutines.CoroutineScope

internal class LocationsRepositoryImpl(
    private val locationApi: LocationApi,
    private val dataBase: DataBase,
    private val scope: CoroutineScope
) : LocationsRepository {

    private val cacheDao = dataBase.cachedLocationDao

    companion object {
        private const val NETWORK_PAGE_SIZE_LOCATION = 7
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getLocations(): Flow<PagingData<LocationEntity>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE_LOCATION, enablePlaceholders = false
        ),
            initialKey = 1,
            remoteMediator = LocationRemoteMediator(
                locationApi = locationApi,
                database = dataBase
            ),
            pagingSourceFactory = {
                cacheDao.getLocations()
            }
        ).flow
            .cachedIn(scope)
    }

    override fun getLocationById(id: Int): Flow<ApiResponse<Location?>> =
        result {
            mapToLocationResponse(locationApi.getLocationById(id = id))
        }.flowOn(Dispatchers.IO)

    override suspend fun getLocationByIdFromDb(id: Int): Location? =
        dataBase.cachedLocationDao.getLocationById(id = id)?.toLocation()
}