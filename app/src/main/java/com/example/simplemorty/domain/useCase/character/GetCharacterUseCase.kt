package com.example.simplemorty.domain.useCase.character

import android.util.Log
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.repository.CharactersRepository

class GetCharacterUseCase(
    private val charactersRepository: CharactersRepository
) {

    suspend fun getCharacterById(id: Int): CharacterProfile {
        return charactersRepository.getCharacterById(id = id)
    }
}