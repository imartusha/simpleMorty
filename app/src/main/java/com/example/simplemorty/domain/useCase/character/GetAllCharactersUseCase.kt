package com.example.simplemorty.domain.useCase.character

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.example.simplemorty.data.models.entity.character.cach.toCharacterProfile
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllCharactersUseCase(
    private val charactersRepository: CharactersRepository
) {

    suspend fun getAllCharacters(): Flow<PagingData<CharacterProfile>> {
        return charactersRepository.getCharacters()
            .map { pagingData ->
                pagingData.map { cachedCharacterEntity ->
                    cachedCharacterEntity.toCharacterProfile()
                }
            }
    }
}