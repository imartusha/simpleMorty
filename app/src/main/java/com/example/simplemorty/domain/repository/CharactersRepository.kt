package com.example.simplemorty.domain.repository

import androidx.paging.PagingData
import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.entity.character.FavoriteEntity
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.data.models.response.CommonResponse
import com.example.simplemorty.domain.models.CharacterProfile
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacterById(id: Int): Flow<ApiResponse<CharacterProfile?>>
    suspend fun getCharacterByIdFromDB(id: Int): CharacterProfile?

    suspend fun getCharacters(): Flow<PagingData<CharacterEntity>>
    suspend fun getMultipleCharacters(characterIdsList: String): List<CharacterProfile>

    fun getSearch(text: String, status: String): Flow<ApiResponse<CommonResponse<CharacterDTO>?>>
    fun getSearchAll(text: String): Flow<ApiResponse<CommonResponse<CharacterDTO>?>>

    fun getAllFavoriteCharacters(): Flow<List<FavoriteEntity>>
    suspend fun addCharacterToFavoriteList(character: FavoriteEntity)
    suspend fun deleteCharacterFromMyFavoriteList(character: FavoriteEntity)
    suspend fun isCharacterInFavorites(id: Int): Boolean
    fun getFavoriteCharacters(): Flow<List<FavoriteEntity>>

    suspend fun updateFavoriteState(id: Int, isFavorite: Boolean)
    suspend fun updateCharacter(character: FavoriteEntity)
}
