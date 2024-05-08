package com.example.simplemorty.data.repository.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.dto.character.mapDTOsToCharacterProfiles
import com.example.simplemorty.data.models.dto.character.mapToCharacterProfileResponse

import com.example.simplemorty.data.models.entity.character.FavoriteEntity
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity
import com.example.simplemorty.data.models.entity.character.cach.toCharacterProfile
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.data.models.response.CommonResponse
import com.example.simplemorty.data.network.api.character.CharacterApi
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import com.example.simplemorty.utils.result


internal class CharactersRepositoryImpl(
    private val characterApi: CharacterApi,
    private val dataBase: DataBase,
    private val scope: CoroutineScope

) : CharactersRepository {

    private val cacheDao = dataBase.cacheCharacterDao
    private val favoriteDao = dataBase.favoriteDao

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getCharacters(): Flow<PagingData<CharacterEntity>> {
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
            mapToCharacterProfileResponse(characterApi.getCharacterById(id = id))
        }.flowOn(Dispatchers.IO)

    override suspend fun getCharacterByIdFromDB(id: Int): CharacterProfile? {
        return dataBase.cacheCharacterDao.getCharacterById(id = id)?.toCharacterProfile()
    }

    override suspend fun getMultipleCharacters(characterIdsList: String): List<CharacterProfile> {
        return mapDTOsToCharacterProfiles(characterApi.getMultipleCharacters(characterIdsList))
    }

    override fun getSearch(
        text: String,
        status: String
    ): Flow<ApiResponse<CommonResponse<CharacterDTO>?>> {
        TODO("Not yet implemented")
    }

    override fun getSearchAll(text: String): Flow<ApiResponse<CommonResponse<CharacterDTO>?>> {
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