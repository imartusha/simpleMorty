package com.example.simplemorty.data.repository

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.ui.pages.episodes.EpisodePagingSource
import com.example.simplemorty.data.database.character.CharactersDataBase
import com.example.simplemorty.data.models.dto.episode.mapDtoToEntityEpisodeList
import com.example.simplemorty.data.models.dto.episode.mapDtoToEpisodeList
import com.example.simplemorty.data.models.entity.episode.mapFromEntityToEpisode
import com.example.simplemorty.data.models.entity.episode.mapToListEpisode
import com.example.simplemorty.data.network.api.episode.EpisodeApi
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class EpisodesRepositoryImpl(
    private val episodeApi: EpisodeApi,
    private val charactersDataBase: CharactersDataBase
) : EpisodesRepository {

    private val dao = charactersDataBase.getEpisodeDao()
    override suspend fun getEpisodeById(id: Int): Episode {
        return mapFromEntityToEpisode(dao.getEpisodeByIdFromDB(id = id))
        //return mapDtoToEpisode(episodeApi.getEpisodeByIdFromApi(id))
    }

    override suspend fun getEpisodes(): Flow<PagingData<Episode>> {
        return Pager(config = PagingConfig(
            pageSize = 3, enablePlaceholders = false
        ),
            pagingSourceFactory = { EpisodePagingSource(episodeApi) }
        ).flow
    }
//    override suspend fun getAllEpisodes(): List<Episode> {
//        return withContext(Dispatchers.IO) {
//            if (dao.getAllEpisodes().isEmpty()) {
//
//                val commonResponse = episodeApi.getAllEpisodes()
//                val episodesDtoList = commonResponse.results
//                val episodesEntities = mapDtoToEntityEpisodeList(episodesDtoList)
//                dao.insertAllEpisodes(episodesEntities)
//                mapDtoToEpisodeList(episodes = episodesDtoList)
//
//            } else {
//                mapToListEpisode(dao.getAllEpisodes())
//            }
//        }
//    }
}
