package com.example.simplemorty.domain.useCase.episode

import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.CharactersRepository
import com.example.simplemorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow

class GetInfoEpisodeByIdUseCase(
    private val episodesRepository: EpisodesRepository,
) {
    suspend fun getInfoEpisode(id: Int): Flow<ApiResponse<Episode?>> {
        return episodesRepository.getEpisodesById(id = id)
    }

}