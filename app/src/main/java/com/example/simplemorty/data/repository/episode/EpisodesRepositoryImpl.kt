package com.example.simplemorty.data.repository
import android.util.Log
import androidx.paging.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.simplemorty.utils.result
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.data.network.api.episode.EpisodeApi
import com.example.simplemorty.data.repository.episode.EpisodePagingSource
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

internal class EpisodesRepositoryImpl(
    private val episodeApi: EpisodeApi,
    private val dataBase: DataBase,
    private val scope: CoroutineScope
) : EpisodesRepository {

    companion object {
        private const val NETWORK_PAGE_SIZE_EPISODE = 3
    }
    override fun getEpisodes(): Flow<PagingData<Episode>> {
        Log.e("MyTag", "getEpispdes в импл")
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE_EPISODE, enablePlaceholders = false
        ),
            pagingSourceFactory = { EpisodePagingSource(episodeApi) }
        ).flow
            .cachedIn(scope)
    }

    override fun getEpisodesById(id: Int): Flow<ApiResponse<Episode?>> =
        result {
            episodeApi.getEpisodeById(id)
        }.flowOn(Dispatchers.IO)

}
