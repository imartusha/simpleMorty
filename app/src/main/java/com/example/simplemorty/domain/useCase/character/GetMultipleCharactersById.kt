package com.example.simplemorty.domain.useCase.character

import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetMultipleCharactersById(
    private val charactersRepository: CharactersRepository
) {
//    suspend fun getMultipleCharactersById(characters: List<String>): Flow<ApiResponse<List<CharacterProfile>?>> {
//        return charactersRepository.getMultipleCharacters(characters)
//    }
}
