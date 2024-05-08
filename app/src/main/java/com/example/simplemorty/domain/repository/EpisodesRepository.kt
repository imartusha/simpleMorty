package com.example.simplemorty.domain.repository

import androidx.paging.PagingData
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {
    fun getEpisodes(): Flow<PagingData<Episode>>
    fun getEpisodesById(id: Int): Flow<ApiResponse<Episode?>>
}