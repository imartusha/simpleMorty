package com.example.simplemorty.data.network.api.character

import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.response.CommonResponse
import com.example.simplemorty.domain.models.CharacterProfile
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CharacterApi {
    @Headers("Cache-Control: max-age=86400")
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") pageIndex: Int? = null
    ): Response<CommonResponse<CharacterDTO>>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<CharacterDTO>

    @GET("character/")
    suspend fun getSearch(
        @Query("name") name: String,
        @Query("status") status: String
    ): Response<CommonResponse<CharacterDTO>>

    @GET("character/")
    suspend fun getSearchAll(
        @Query("name") name: String,
    ): Response<CommonResponse<CharacterDTO>>

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(
        @Path("ids") characterIds: String
    ): List<CharacterDTO>
}