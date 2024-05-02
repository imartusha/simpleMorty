package com.example.simplemorty.data.network.api.character

import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.dto.character.CharactersResponse
import com.example.simplemorty.data.models.response.CommonResponse
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
    ): Response<CharactersResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<CharacterDTO>

    @GET("character/")
    suspend fun getSearch(
        @Query("name") name: String,
        @Query("status") status: String
    ): Response<CharactersResponse>

    @GET("character/")
    suspend fun getSearchAll(
        @Query("name") name: String,
    ): Response<CharactersResponse>

    @GET("character/{list}")
    suspend fun getMoreCharactersThanOne(
        @Path("list") characterList: List<String>
    ): Response<List<CharacterDTO>>
}