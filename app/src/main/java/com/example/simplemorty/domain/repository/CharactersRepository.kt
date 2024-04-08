package com.example.simplemorty.domain.repository

import androidx.paging.PagingData
import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.entity.character.CachedCharacterEntity
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Location
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    //network
    suspend fun getCharacterById(id: Int): CharacterProfile
    suspend fun getCharacters(): Flow<PagingData<CachedCharacterEntity>>
    suspend fun getMultipleCharacters(characters: List<String>): Flow<PagingData<CharacterProfile>>
    //   suspend fun getMultipleCharacters(characters: List<String>): List<CharacterProfile>

    //room db
    suspend fun getAllCharactersFromLocalDb(): List<CharacterProfile>
}

interface EpisodesRepository {
    suspend fun getEpisodeById(id: Int): Episode

  //  suspend fun getAllEpisodes(): List<Episode>
    suspend fun getEpisodes(): Flow<PagingData<Episode>>

    //   suspend fun getCharactersFromEpisode():Flow<PagingData<CachedCharacterEntity>>



}
interface LocationsRepository {
    suspend fun getLocationById(id: Int): Location
    suspend fun getAllLocations(): List<Location>
}