package com.example.simplemorty.data.repository.character

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import coil.util.CoilUtils.result
import com.example.simplemorty.data.database.character.CharacterDao
import com.example.simplemorty.data.database.character.DataBase

import com.example.simplemorty.data.models.entity.character.FavoriteEntity
import com.example.simplemorty.data.models.entity.character.cach.CachedCharacterEntity
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.data.models.response.CharactersResponse
import com.example.simplemorty.data.network.api.character.CharacterApi
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import com.example.simplemorty.utils.result


internal class CharactersRepositoryImpl(
    private val characterApi: CharacterApi,
    private val dataBase: DataBase,
    private val scope: CoroutineScope

) : CharactersRepository {


    private val cacheDao = dataBase.cacheDao
    private val favoriteDao = dataBase.favoriteDao

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
            .cachedIn(scope)
    }

    override fun getFavoriteCharacters(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavoriteCharacters()
    }

    override fun getCharacterById(id: Int): Flow<ApiResponse<CharacterProfile?>> =
        result {
            characterApi.getCharacterById(id = id)
        }.flowOn(Dispatchers.IO)


    //    override suspend fun getCharacterEpisodes(episodeList: String): Flow<ApiResponse<List<Episode>?>> =
//        result {
//            characterApi.getCharacterEpisodes(episodeList)
//        }.flowOn(Dispatchers.IO)

//    override fun getMultipleCharacters(characterIdsList: List<String>): Flow<ApiResponse<List<CharacterProfile>?>> =
//        result {
//            characterApi.getMultipleCharacters(characterIdsList = characterIdsList)
//        }.flowOn(Dispatchers.IO)
override suspend fun getMultipleCharacters(characterIdsList: String): List<CharacterProfile> {
    return characterApi.getMultipleCharacters(characterIdsList)
}



    override fun getSearch(text: String, status: String): Flow<ApiResponse<CharactersResponse?>> {
        TODO("Not yet implemented")
    }

    override fun getSearchAll(text: String): Flow<ApiResponse<CharactersResponse?>> {
        TODO("Not yet implemented")
    }

    override fun getAllFavoriteCharacters(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavoriteCharacters()
    }

    override suspend fun addCharacterToFavoriteList(character: FavoriteEntity) {
        character.isFavorite = true
        favoriteDao.addFavoriteCharacter(character)
        favoriteDao.updateCharacter(character)
        favoriteDao.updateFavoriteState(character.id!!, true)
    }

    override suspend fun deleteCharacterFromMyFavoriteList(character: FavoriteEntity) {
        character.isFavorite = false
        favoriteDao.deleteFavoriteCharacter(character)
        favoriteDao.updateCharacter(character)
        favoriteDao.updateFavoriteState(character.id!!, false)
    }

    override suspend fun updateFavoriteState(id: Int, isFavorite: Boolean) {
        favoriteDao.updateFavoriteState(id, isFavorite)
    }

    override suspend fun updateCharacter(character: FavoriteEntity) {
        favoriteDao.updateCharacter(character)
    }

    override suspend fun isCharacterInFavorites(id: Int): Boolean =
        withContext(Dispatchers.IO) {
            favoriteDao.isCharacterInFavorites(id)
        }


//    override suspend fun getMultipleCharacters(characters: List<String>): Flow<PagingData<CharacterProfile>> {
//        val charactersDTOList =
//            characterApi.getMultipleCharactersFromApi(getIdesCharactersList(characters))
//        val profiles = mapDTOsToCharacterProfiles(characters = charactersDTOList)
//        val pagingData: PagingData<CharacterProfile> = PagingData.from(profiles)
//        return flowOf(pagingData)
//    }
//
//    override suspend fun getAllCharactersFromLocalDb(): List<CharacterProfile> {
//        return mapEntitiesToCharacterProfiles(characterDao.getAllCharacters())
//    }
//
//    private fun getIdesCharactersList(characters: List<String>): List<Int> {
//        val listCharacters: MutableList<Int> = mutableListOf()
//        characters.forEach { it ->
//            val characterId = it.substringAfterLast("/", "").toInt()
//            if (characterId == 0) {
//                Log.e("Except in Episodes", "Not found id")
//            }
//            listCharacters.add(characterId)
//        }
//        return listCharacters
//    }
}