package com.example.simplemorty.data.repository.episode

import androidx.paging.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.simplemorty.utils.result
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.dto.episode.mapDtoToEpisodeList
import com.example.simplemorty.data.models.dto.episode.mapToEpisodeResponse
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity
import com.example.simplemorty.data.models.entity.episode.mapToListEpisode
import com.example.simplemorty.data.models.entity.episode.toEpisode
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.data.network.api.episode.EpisodeApi
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

internal class EpisodesRepositoryImpl(
    private val episodeApi: EpisodeApi,
    private val dataBase: DataBase,
    private val scope: CoroutineScope
) : EpisodesRepository {

    private val cacheDao = dataBase.cachedEpisodeDao

    companion object {
        private const val NETWORK_PAGE_SIZE_EPISODE = 3
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getEpisodes(): Flow<PagingData<EpisodeEntity>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE_EPISODE, enablePlaceholders = false
        ),
            initialKey = 1,
            remoteMediator = EpisodeRemoteMediator(
                episodeApi = episodeApi,
                database = dataBase
            ),
            pagingSourceFactory = {
                cacheDao.getEpisodes()
            }
        ).flow
            .cachedIn(scope)
    }

    override fun getEpisodesById(id: Int): Flow<ApiResponse<Episode?>> =
        result {
            mapToEpisodeResponse(episodeApi.getEpisodeById(id))
        }.flowOn(Dispatchers.IO)

    override suspend fun getEpisodeByIdFromDb(id: Int): Episode? =
        dataBase.cachedEpisodeDao.getEpisodesById(id = id)?.toEpisode()

    override suspend fun getMultipleEpisodeFromDb(episodeIdsList: List<String>): List<Episode> {
        return withContext(Dispatchers.IO) {
            mapToListEpisode(dataBase.cachedEpisodeDao.getMultipleEpisode(episodeIdsList))
        }
    }

    override suspend fun getMultipleEpisode(episodeIdsList: String): List<Episode> =
        mapDtoToEpisodeList(episodeApi.getMultipleEpisode("$episodeIdsList,"))

}
