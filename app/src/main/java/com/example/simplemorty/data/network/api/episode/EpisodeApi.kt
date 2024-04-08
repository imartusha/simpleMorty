package com.example.simplemorty.data.network.api.episode

import androidx.paging.PagingData
import com.example.simplemorty.data.models.dto.episode.EpisodeDTO
import com.example.simplemorty.data.models.response.CommonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface EpisodeApi {

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") id: Int
    ): EpisodeDTO

    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page") pageIndex: Int? = null
    ): CommonResponse<EpisodeDTO>
}