package com.example.simplemorty.domain.useCase.episode

import androidx.paging.PagingData
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow

class GetAllEpisodesUseCase(
    val episodesRepository: EpisodesRepository
) {
    suspend fun getAllEpisodes(): Flow<PagingData<Episode>> {
        return episodesRepository.getEpisodes()
    }

}