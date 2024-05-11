package com.example.simplemorty.data.network.api.location

import com.example.simplemorty.data.models.dto.location.LocationDTO
import com.example.simplemorty.data.models.response.CommonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface LocationApi {

    @GET("location/")
    suspend fun getLocations(
        @Query("page") pageIndex: Int? = null
    ): Response<CommonResponse<LocationDTO>>

    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): Response<LocationDTO>

    @GET("location/{ids}")
    suspend fun getMultipleLocations(
        @Path("ids") locationIds: String
    ): List<LocationDTO>
}