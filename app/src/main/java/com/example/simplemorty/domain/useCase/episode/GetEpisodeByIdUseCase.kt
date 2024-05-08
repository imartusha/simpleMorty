package com.example.simplemorty.domain.useCase.episode

import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodeByIdUseCase(
    private val episodesRepository: EpisodesRepository,
) {
    fun getEpisodeById(id: Int): Flow<ApiResponse<Episode?>> {
        return episodesRepository.getEpisodesById(id = id)
    }
    suspend fun getEpisodesByIdFromDb(id: Int): Episode?{
        return episodesRepository.getEpisodesByIdFromDb(id = id)
    }

}