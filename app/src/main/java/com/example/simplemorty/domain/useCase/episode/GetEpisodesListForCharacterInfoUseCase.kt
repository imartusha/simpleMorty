package com.example.simplemorty.domain.useCase.episode

import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.EpisodesRepository

class GetEpisodesListForCharacterInfoUseCase(
    private val episodesRepository: EpisodesRepository
) {
    suspend fun getEpisodesListForCharacterInfo(characterProfile: CharacterProfile): List<Episode> {
        val episodeIdsList = characterProfile.episode!!.map { episodeUrl ->
            episodeUrl.substringAfterLast("/")
        }
        val episodesFromDb = episodesRepository.getMultipleEpisodeFromDb(episodeIdsList)
        return episodesFromDb.ifEmpty {
             episodesRepository.getMultipleEpisode(episodeIdsList.joinToString(","))
        }
    }
}