package com.example.simplemorty.domain.repository

import androidx.paging.PagingData
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {
    fun getEpisodes(): Flow<PagingData<EpisodeEntity>>
    fun getEpisodesById(id: Int): Flow<ApiResponse<Episode?>>
    suspend fun getEpisodeByIdFromDb(id: Int): Episode?
    suspend fun getMultipleEpisodeFromDb(episodeIdsList: List<String>): List<Episode>
    suspend fun getMultipleEpisode(episodeIdsList: String): List<Episode>
}