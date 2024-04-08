package com.example.simplemorty.domain.useCase.character

import androidx.paging.PagingData
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersListForInfo (
    private val charactersRepository: CharactersRepository
){
    suspend fun getCharactersListForInfo(charactersIdes: List<String>): Flow<PagingData<CharacterProfile>> {
        return charactersRepository.getMultipleCharacters(charactersIdes)
    }
}