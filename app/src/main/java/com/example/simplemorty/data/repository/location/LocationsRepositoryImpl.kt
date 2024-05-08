package com.example.simplemorty.data.repository.location

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
    private val scope: CoroutineScope
) : LocationsRepository {

    override fun getLocationById(id: Int): Flow<ApiResponse<Location?>> =
        result {
            locationApi.getLocationById(id=id)
        }.flowOn(Dispatchers.IO)

    override fun getLocations(): Flow<PagingData<Location>> {
        return Pager(config = PagingConfig(
            pageSize = 7, enablePlaceholders = false
        ),
            pagingSourceFactory = { LocationPagingSource(locationApi) }
        ).flow
            .cachedIn(scope)
    }


//    override suspend fun getAllLocations(): List<Location> {
//        return withContext(Dispatchers.IO) {
//            val commonResponse = locationApi.getAllLocations()
//            val locationsDtoList = commonResponse.results
//            val locationsEntities = mapDtoToEntityLocationList(locationsDtoList)
//
//            mapDtoToLocationList(locations = locationsDtoList)
//        }
//    }
}