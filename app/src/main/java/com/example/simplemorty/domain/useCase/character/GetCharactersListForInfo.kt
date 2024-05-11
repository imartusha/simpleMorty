package com.example.simplemorty.domain.useCase.character

import android.util.Log
import androidx.paging.PagingData
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersListForInfo(
    private val charactersRepository: CharactersRepository
) {

    suspend fun getCharactersListForInfo(episode: Episode): List<CharacterProfile> {
        val characterIds = episode.characters.map { characterUrl ->
            characterUrl.substringAfterLast("/")
        }.joinToString(",")
        return charactersRepository.getMultipleCharacters(characterIds)
    }

    suspend fun getCharactersListForInfo(location: Location): List<CharacterProfile> {
        val characterIds = location.residents.map { characterUrl ->
            characterUrl.substringAfterLast("/")
        }.joinToString(",")
        return charactersRepository.getMultipleCharacters(characterIds)
    }
}