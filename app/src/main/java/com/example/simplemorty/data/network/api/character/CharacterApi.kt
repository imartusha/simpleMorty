package com.example.simplemorty.data.network.api.character

import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.response.CommonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CharacterApi {

    @GET("character/{character-id}")
    suspend fun getCharacterByIdFromApi(
        @Path("character-id") id: Int
    ): CharacterDTO

    @GET("character/{character-id}")
    suspend fun getMultipleCharactersFromApi(
        @Path("character-id") ids: List<Int>
    ): List<CharacterDTO>

    @GET("character/")
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): CommonResponse<CharacterDTO>
}