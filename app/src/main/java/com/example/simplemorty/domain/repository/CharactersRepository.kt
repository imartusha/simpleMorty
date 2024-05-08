package com.example.simplemorty.domain.repository

import androidx.paging.PagingData
import com.example.simplemorty.data.models.entity.character.FavoriteEntity
import com.example.simplemorty.data.models.entity.character.cach.CachedCharacterEntity
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.data.models.response.CharactersResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Location
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    //network
     fun getCharacterById(id: Int): Flow<ApiResponse<CharacterProfile?>>
    suspend fun getCharacters(): Flow<PagingData<CachedCharacterEntity>>
    //  suspend fun getCharacterEpisodes(episodeList: String): Flow<ApiResponse<List<Episode>?>>

    suspend fun getMultipleCharacters(characterIdsList: String): List<CharacterProfile>
    //: Flow<ApiResponse<List<CharacterProfile>?>>


    //room db
    //  suspend fun getAllCharactersFromLocalDb(): List<CharacterProfile>
    fun getSearch(
        text: String,
        status: String
    ): Flow<ApiResponse<CharactersResponse?>>

    fun getSearchAll(
        text: String
    ): Flow<ApiResponse<CharactersResponse?>>

    fun getAllFavoriteCharacters(): Flow<List<FavoriteEntity>>
    suspend fun addCharacterToFavoriteList(character: FavoriteEntity)

    suspend fun deleteCharacterFromMyFavoriteList(character: FavoriteEntity)

    suspend fun updateFavoriteState(id: Int, isFavorite: Boolean)

    suspend fun updateCharacter(character: FavoriteEntity)

    suspend fun isCharacterInFavorites(id: Int): Boolean

    fun getFavoriteCharacters(): Flow<List<FavoriteEntity>>
}

interface EpisodesRepository {
    fun getEpisodes(): Flow<PagingData<Episode>>
    fun getEpisodesById(id: Int): Flow<ApiResponse<Episode?>>
}

interface LocationsRepository {
    fun getLocationById(id: Int): Flow<ApiResponse<Location?>>
    fun getLocations(): Flow<PagingData<Location>>
}
