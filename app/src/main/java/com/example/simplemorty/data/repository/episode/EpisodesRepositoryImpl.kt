package com.example.simplemorty.data.repository
import androidx.paging.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import coil.util.CoilUtils.result
import com.example.rickandmorty.ui.pages.episodes.EpisodePagingSource
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.entity.episode.mapFromEntityToEpisode
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.data.network.api.episode.EpisodeApi
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

internal class EpisodesRepositoryImpl(
    private val episodeApi: EpisodeApi,
    private val dataBase: DataBase,
    private val scope: CoroutineScope
) : EpisodesRepository {

    companion object {
        private const val NETWORK_PAGE_SIZE_EPISODE = 3
    }

    override fun getEpisodesById(id: Int): Flow<ApiResponse<Episode?>> =
        result {
            api.getEpisodeById(id)
        }.flowOn(Dispatchers.IO)


    override fun getEpisodes(): Flow<PagingData<Episode>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE_EPISODE, enablePlaceholders = false
        ),
            pagingSourceFactory = { EpisodePagingSource(api) }
        ).flow
            .cachedIn(scope)
    }

}
