package com.example.simplemorty.data.repository.character

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.simplemorty.data.database.character.CharacterDao
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.dto.character.mapDTOsToCharacterProfiles
import com.example.simplemorty.data.models.dto.character.mapDtoToCharacterProfile
import com.example.simplemorty.data.models.dto.character.mapEntitiesToCharacterProfiles
import com.example.simplemorty.data.models.dto.character.mapDtoToCharacterEntity
import com.example.simplemorty.data.models.entity.character.cach.CachedCharacterEntity
import com.example.simplemorty.data.models.entity.character.mapEntityToCharacterProfile
import com.example.simplemorty.data.network.api.character.CharacterApi
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


internal class CharactersRepositoryImpl(
    private val characterApi: CharacterApi,
    private val characterDao: CharacterDao,
    private val dataBase: DataBase

) : CharactersRepository {

    override suspend fun getCharacterById(id: Int): CharacterProfile {
        val characterFromLocalDb = characterDao.getCharacterByIdFromDB(id = id)?.let {
            mapEntityToCharacterProfile(it)
        }
        return if (characterFromLocalDb != null) {
            characterFromLocalDb
        } else {
            val characterDto = characterApi.getCharacterByIdFromApi(id)
            val characterProfile = mapDtoToCharacterProfile(characterDto)
            val characterEntity = mapDtoToCharacterEntity(characterDto)

            characterDao.insertCharacter(characterEntity)

            characterProfile
        }
    }

    private val cacheDao = dataBase.cacheDao

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getCharacters(): Flow<PagingData<CachedCharacterEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 42, enablePlaceholders = false),
            initialKey = 1,
            remoteMediator = CharacterRemoteMediator(
                characterApi = characterApi,
                database = dataBase
            ),
            pagingSourceFactory = {
                cacheDao.getCharacters()
            }
        ).flow
    }

    override suspend fun getMultipleCharacters(characters: List<String>): Flow<PagingData<CharacterProfile>> {
        val charactersDTOList =
            characterApi.getMultipleCharactersFromApi(getIdesCharactersList(characters))
        val profiles = mapDTOsToCharacterProfiles(characters = charactersDTOList)
        val pagingData: PagingData<CharacterProfile> = PagingData.from(profiles)
        return flowOf(pagingData)
    }


//    override suspend fun getMultipleCharacters(characters: List<String>): List<CharacterProfile> {
//        val charactersDTOList =
//            characterApi.getMultipleCharactersFromApi(getIdesCharactersList(characters))
//        return mapCharacterDTOsToProfiles(characters = charactersDTOList)
//    }

    override suspend fun getAllCharactersFromLocalDb(): List<CharacterProfile> {
        return mapEntitiesToCharacterProfiles(characterDao.getAllCharacters())
    }

    private fun getIdesCharactersList(characters: List<String>): List<Int> {
        val listCharacters: MutableList<Int> = mutableListOf()
        characters.forEach { it ->
            val characterId = it.substringAfterLast("/", "").toInt()
            if (characterId == 0) {
                Log.e("Except in Episodes", "Not found id")
            }
            listCharacters.add(characterId)
        }
        return listCharacters
    }
}