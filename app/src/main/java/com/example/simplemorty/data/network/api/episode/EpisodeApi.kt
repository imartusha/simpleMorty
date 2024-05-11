package com.example.simplemorty.data.network.api.episode

import com.example.simplemorty.data.models.dto.episode.EpisodeDTO
import com.example.simplemorty.data.models.response.CommonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface EpisodeApi {

    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") pageIndex: Int? = null
    ): Response<CommonResponse<EpisodeDTO>>

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int
    ): Response<EpisodeDTO>

    @GET("episode/{ids}")
    suspend fun getMultipleEpisode(
        @Path("ids") episodeIds: String
    ): List<EpisodeDTO>
}