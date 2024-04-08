package com.example.simplemorty.data.network.api.location

import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.dto.location.LocationDTO
import com.example.simplemorty.data.models.response.CommonResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface LocationApi {

    @GET("location/{location-id}")
    suspend fun getLocationById(
        @Path("location-id") id: Int
    ): LocationDTO

    @GET("location")
    suspend fun getAllLocations(): CommonResponse<LocationDTO>
}