package com.example.simplemorty.data.network.api.episode

import androidx.paging.PagingData
import com.example.simplemorty.data.models.dto.episode.EpisodeDTO
import com.example.simplemorty.data.models.response.EpisodeResponse
import com.example.simplemorty.domain.models.Episode
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface EpisodeApi {

    @Headers("Cache-Control: max-age=86400")
    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") pageIndex: Int? = null
    ): Response<EpisodeResponse>

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int
    ): Response<Episode>

    @GET("episode/{episodes}")
    suspend fun getCharacterEpisodes(
        @Path("episodes") episodes: String
    ): Response<List<Episode>>
}