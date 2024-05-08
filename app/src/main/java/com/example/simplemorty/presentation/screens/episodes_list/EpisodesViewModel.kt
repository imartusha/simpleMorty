package com.example.simplemorty.presentation.screens.episodes_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.useCase.episode.GetAllEpisodesUseCase
import com.example.simplemorty.domain.useCase.episode.GetEpisodeByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class EpisodesViewModel(
    private val getAllEpisodes: GetAllEpisodesUseCase
) : ViewModel() {

    private val _episodesListStateFlow: MutableSharedFlow<PagingData<Episode>> = MutableSharedFlow()
    val episodesListStateFlow: SharedFlow<PagingData<Episode>> = _episodesListStateFlow

    fun dispatch(intentScreenEpisodes: IntentScreenEpisodes) {
        when (intentScreenEpisodes) {
            is IntentScreenEpisodes.GetAllEpisodes -> fetchEpisodes()
        }
    }

    private fun fetchEpisodes() {
        viewModelScope.launch {
            getAllEpisodes.getAllEpisodes().flowOn(Dispatchers.IO).collect {
                _episodesListStateFlow.emit(it)
            }

        }
    }
}

sealed interface IntentScreenEpisodes {

    data object GetAllEpisodes : IntentScreenEpisodes
}
