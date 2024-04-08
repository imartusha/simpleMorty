package com.example.simplemorty.domain.useCase.episode

import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.CharactersRepository
import com.example.simplemorty.domain.repository.EpisodesRepository

class GetInfoEpisodeByIdUseCase(
    private val episodesRepository: EpisodesRepository,
) {
    suspend fun getInfoEpisode(id: Int): Episode {
        return episodesRepository.getEpisodeById(id = id)
    }

}