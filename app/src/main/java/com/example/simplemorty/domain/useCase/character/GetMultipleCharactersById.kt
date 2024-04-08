package com.example.simplemorty.domain.useCase.character

import androidx.paging.PagingData
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetMultipleCharactersById(
    private val charactersRepository: CharactersRepository
) {
    suspend fun getMultipleCharactersById(characters: List<String>): Flow<PagingData<CharacterProfile>> {
        return charactersRepository.getMultipleCharacters(characters = characters)
    }
}
