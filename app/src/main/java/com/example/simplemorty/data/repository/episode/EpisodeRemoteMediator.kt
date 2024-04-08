//package com.example.simplemorty.data.repository.episode
//
//import android.util.Log
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadState.Loading.endOfPaginationReached
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import androidx.room.withTransaction
//import com.example.simplemorty.data.database.episode.EpisodesDataBase
//import com.example.simplemorty.data.models.entity.character.CharacterRemoteKeysEntity
//import com.example.simplemorty.data.models.entity.episode.CachedEpisodeEntity
//import com.example.simplemorty.data.models.entity.episode.EpisodeRemoteKeyEntitiy
//import com.example.simplemorty.data.models.entity.episode.mapToCachedEpisodeEntity
//import com.example.simplemorty.data.network.api.episode.EpisodeApi
//
//@OptIn(ExperimentalPagingApi::class)
//internal class EpisodeRemoteMediator (
//        private val episodeApi: EpisodeApi,
//        private val database: EpisodesDataBase
//    ) : RemoteMediator<Int, CachedEpisodeEntity>() {
//
//    private val remoteKeysDao = database.remoteKeysDao
//    private val cacheDao = database.cacheDao
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, CachedEpisodeEntity>
//    ): MediatorResult {
//
//        val page = when (loadType) {
//            LoadType.REFRESH -> {
//                Log.d("MyRemoteMediatorEpisode", "Loading page , loadType=${loadType.name}")
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1)
//                    ?: 1// always start from the first page when refreshing
//            }
//
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                Log.d("MyRemoteMediator", "Loading page $remoteKeys, loadType=${loadType.name}")
//                remoteKeys?.prevKey
//                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//            }
//
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                Log.d("MyRemoteMediator", "Loading page $remoteKeys, loadType=${loadType.name}")
//                remoteKeys?.nextKey
//                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//            }
//        }
//
//        try {
//            Log.i("Saves", "Получаем данные с епизодов ")
//
//            val response = episodeApi.getAllEpisodes(page = page)
//            val episodes = response.results
//
//            database.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    database.remoteKeysDao.clearRemoteKeys()
//                    database.cacheDao.clearEpisodes()
//                }
//            }
//            val prevKey = if (page == 1) null else page.minus(1)
//            val nextKey = if ((endOfPaginationReached)) null else page.plus(1)
//
//            Log.d("MyRemoteMediator", "Loading page $page, loadType=${loadType.name}")
//
//            val cachedEpisodes = mapToCachedEpisodeEntity(episodes)
//            cacheDao.insertAll(cachedEpisodes)
//
//            Log.d(
//                "MyRemoteMediator",
//                "cachedEpisodes ${cacheDao.insertAll(cachedEpisodes)} episodes stored "
//            )
//
//            val listRemoteKeysEntity = listOf<CharacterRemoteKeysEntity>()
//
//            val remoteKeys = listRemoteKeysEntity.map {
//                EpisodeRemoteKeyEntitiy(
//                    repoId = it.repoId,
//                    prevKey = prevKey,
//                    nextKey = nextKey
//                )
//            }
//            remoteKeysDao.insertAll(remoteKeys)
//
//            return MediatorResult.Success(endOfPaginationReached = episodes.isEmpty())
//
//        } catch (e: Exception) {
//            // В случае ошибки возвращаем ошибку
//            Log.e("EpisodeRemoteMediator", "Error loading data", e)
//            return MediatorResult.Error(e)
//        }
//    }
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CachedEpisodeEntity>): EpisodeRemoteKeyEntitiy? {
//        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let {
//                database.remoteKeysDao.remoteKeysRepoId(it.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CachedEpisodeEntity>): EpisodeRemoteKeyEntitiy? {
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let {
//                database.remoteKeysDao.remoteKeysRepoId(it.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CachedEpisodeEntity>): EpisodeRemoteKeyEntitiy? {
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { repoId ->
//                database.remoteKeysDao.remoteKeysRepoId(repoId)
//            }
//        }
//    }
//}
//
//
