package com.example.simplemorty.domain.useCase.character

import com.example.simplemorty.domain.repository.CharactersRepository

class IsCharacterInFavoritesUseCase (
    private val charactersRepository: CharactersRepository
) {
    suspend fun isCharacterInFavorites(id: Int): Boolean{
        return charactersRepository.isCharacterInFavorites(id = id)
    }
}