package com.example.simplemorty.domain.useCase.episode

import androidx.paging.PagingData
import androidx.paging.map
import com.example.simplemorty.data.models.entity.character.cach.toCharacterProfile
import com.example.simplemorty.data.models.entity.episode.toEpisode
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllEpisodesUseCase(
    val episodesRepository: EpisodesRepository
) {
    fun getAllEpisodes(): Flow<PagingData<Episode>> {
        return episodesRepository.getEpisodes()
            .map { pagingData ->
                pagingData.map { cachedEpisodesEntity ->
                    cachedEpisodesEntity.toEpisode()
                }
            }
    }

}