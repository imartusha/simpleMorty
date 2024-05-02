package com.example.simplemorty.data.network.api.episode

import androidx.paging.PagingData
import com.example.simplemorty.data.models.dto.episode.EpisodeDTO
import com.example.simplemorty.data.models.dto.episode.EpisodeResponse
import com.example.simplemorty.data.models.response.CommonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface EpisodeApi {

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int
    ): Response<EpisodeDTO>

    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page") pageIndex: Int? = null
    ): Response<EpisodeResponse>

    @GET("episode/{episodes}")
    suspend fun getCharacterEpisodes(
        @Path("episodes") episodes: String
    ): Response<List<EpisodeDTO>>
}