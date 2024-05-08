package com.example.simplemorty.domain.repository

import androidx.paging.PagingData
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.Location
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {
    fun getLocationById(id: Int): Flow<ApiResponse<Location?>>
    fun getLocations(): Flow<PagingData<Location>>
}
