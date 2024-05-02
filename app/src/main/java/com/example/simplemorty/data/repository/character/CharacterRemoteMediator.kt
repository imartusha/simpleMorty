package com.example.simplemorty.data.repository.character

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.entity.character.cach.CachedCharacterEntity
import com.example.simplemorty.data.models.entity.character.RemoteKeysEntity
import com.example.simplemorty.data.models.entity.character.cach.mapToCachedCharacterEntity
import com.example.simplemorty.data.network.api.character.CharacterApi

@OptIn(ExperimentalPagingApi::class)
internal class CharacterRemoteMediator(
    private val characterApi: CharacterApi,
    private val database: DataBase
) : RemoteMediator<Int, CachedCharacterEntity>() {

    private val remoteKeysDao = database.remoteKeysDao
    private val cacheDao = database.cacheDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedCharacterEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                Log.d("MyRemoteMediator", "Loading page , loadType=${loadType.name}")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1)
                    ?: 1// always start from the first page when refreshing
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                Log.d("MyRemoteMediator", "Loading page $remoteKeys, loadType=${loadType.name}")
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                Log.d("MyRemoteMediator", "Loading page $remoteKeys, loadType=${loadType.name}")
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            Log.i("Saves", "Получаем данные с ")

            val response = characterApi.getAllCharacters(page = page)
            val characters = response.results

           database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao.clearRemoteKeys()
                    database.cacheDao.clearCharacters()
                }
            }
            val prevKey = if (page == 1) null else page.minus(1)
            val nextKey = if ((endOfPaginationReached)) null else page.plus(1)

            Log.d("MyRemoteMediator", "Loading page $page, loadType=${loadType.name}")

            val cachedCharacters = mapToCachedCharacterEntity(characters)
            cacheDao.insertAll(cachedCharacters)

            Log.d(
                "MyRemoteMediator",
                "cachedCharacters ${cacheDao.insertAll(cachedCharacters)} characters stored "
            )

            val listRemoteKeysEntity = listOf<RemoteKeysEntity>()

            val remoteKeys = listRemoteKeysEntity.map {
                RemoteKeysEntity(
                    repoId = it.repoId,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            remoteKeysDao.insertAll(remoteKeys)

            return MediatorResult.Success(endOfPaginationReached = characters.isEmpty())

        } catch (e: Exception) {
            // В случае ошибки возвращаем ошибку
            Log.e("CharacterRemoteMediator", "Error loading data", e)
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CachedCharacterEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.remoteKeysDao.remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CachedCharacterEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.remoteKeysDao.remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CachedCharacterEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysDao.remoteKeysRepoId(repoId)
            }
        }
    }
}
