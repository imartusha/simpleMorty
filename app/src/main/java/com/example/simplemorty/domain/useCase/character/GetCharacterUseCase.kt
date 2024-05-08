package com.example.simplemorty.domain.useCase.character

import android.util.Log
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterUseCase(
    private val charactersRepository: CharactersRepository
) {

    suspend fun getCharacterById(id: Int): Flow<ApiResponse<CharacterProfile?>> {
        return charactersRepository.getCharacterById(id = id)
    }
}