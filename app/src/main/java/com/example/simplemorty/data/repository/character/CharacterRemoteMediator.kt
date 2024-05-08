package com.example.simplemorty.data.repository.character

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity
import com.example.simplemorty.data.models.entity.character.RemoteKeysEntity
import com.example.simplemorty.data.models.entity.character.cach.mapToCachedCharacterEntity
import com.example.simplemorty.data.network.api.character.CharacterApi

@OptIn(ExperimentalPagingApi::class)
internal class CharacterRemoteMediator(
    private val characterApi: CharacterApi,
    private val database: DataBase
) : RemoteMediator<Int, CharacterEntity>() {

    private val remoteKeysDao = database.remoteKeysDao
    private val cacheDao = database.cacheDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        Log.e("MyTag", "я в фун лоад ремоут медиатора")
        val page = when (loadType) {
            LoadType.REFRESH -> {
                Log.d("MyTag", "РЕФРЕШ Loading page , loadType=${loadType.name}")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                Log.d("MyTag", "Loading page $remoteKeys, loadType=${loadType.name}")
                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                Log.d("MyTag", "Loading page $remoteKeys, loadType=${loadType.name}")
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            Log.e("MyTag", "делаю респонсе внутри медиатора:")
            val response = characterApi.getCharacters(page)
            Log.e("MyTag", "запрос: $response")
            val characters = response.body()?.results ?: emptyList()
            val sortedCharacters = characters.sortedBy { it.id }

            database.withTransaction {
                if (loadType== LoadType.REFRESH){
                    database.remoteKeysDao.clearRemoteKeys()
                    database.cacheDao.clearCharacters()
                }
            }
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1)
            val nextKey = if ((endOfPaginationReached)) null else page.plus(1)

            Log.e("MyTag", "Loading page $page, loadType=${loadType.name}")

            val cachedCharacters = mapToCachedCharacterEntity(characters)
            cacheDao.insertAll(cachedCharacters)

            Log.e("MyTag", "cachedCharacters ${cacheDao.insertAll(cachedCharacters)} characters stored ")


            val remoteKeys = sortedCharacters.map {
                RemoteKeysEntity(
                    repoId = it.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            remoteKeysDao.insertAll(remoteKeys)

            return MediatorResult.Success(endOfPaginationReached = characters.isEmpty())
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.remoteKeysDao.remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.remoteKeysDao.remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysDao.remoteKeysRepoId(repoId)
            }
        }
    }
}
