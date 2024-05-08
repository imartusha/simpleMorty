package com.example.simplemorty.data.network.api.location

import com.example.simplemorty.data.models.dto.location.LocationDTO
import com.example.simplemorty.data.models.response.LocationResponse
import com.example.simplemorty.domain.models.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface LocationApi {

    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): Response<Location>

    @GET("location/")
    suspend fun getLocations(
        @Query("page") pageIndex: Int? = null
    ): Response<LocationResponse>
}