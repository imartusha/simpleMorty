package com.example.simplemorty.presentation.screens.episode_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.useCase.character.GetCharactersListForInfo
import com.example.simplemorty.domain.useCase.episode.GetInfoEpisodeByIdUseCase
import com.example.simplemorty.utils.formatDateString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InfoEpisodeViewModel(
    private val getInfoEpisodeByIdUseCase: GetInfoEpisodeByIdUseCase,
    private val getCharactersListForInfo: GetCharactersListForInfo
) : ViewModel() {

    private val _characters = MutableStateFlow<PagingData<CharacterProfile>>(PagingData.empty())
    val characters: StateFlow<PagingData<CharacterProfile>> get() = _characters

    private val _state = MutableStateFlow(ScreenStateEpisode())
    val state: StateFlow<ScreenStateEpisode> = _state

    fun dispatch(intentScreenInfoEpisode: IntentScreenInfoEpisode) {
        when (intentScreenInfoEpisode) {
            is IntentScreenInfoEpisode.GetEpisode -> getEpisodeById(intentScreenInfoEpisode.id)
            else -> throw IllegalArgumentException("Unknown intent screen: $intentScreenInfoEpisode")
        }
    }

    private fun getEpisodeById(id: Int) {
        viewModelScope.launch {
            val episode = getInfoEpisodeByIdUseCase.getInfoEpisode(id = id)
            getListCharactersForEpisode(episode)
            _state.update { state ->
                updateEpisodeInfo(state, episode, characters)
            }
        }
    }

    private fun getListCharactersForEpisode(episode: Episode) {
        viewModelScope.launch {
            getCharactersListForInfo.getCharactersListForInfo(charactersIdes = episode.characters)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _characters.value = pagingData
                }
        }
    }
}

private fun updateEpisodeInfo(
    state: ScreenStateEpisode,
    episode: Episode,
    characters: StateFlow<PagingData<CharacterProfile>> // Принимаем список персонажей
): ScreenStateEpisode {
    return state.copy(
        name = episode.name,
        episode = episode.episode,
        airDate = episode.airDate,
        created = episode.created.formatDateString(),
        characters = characters,
        url = episode.url
    )
}

data class ScreenStateEpisode(
    val name: String? = null,
    val episode: String? = null,
    val airDate: String? = null,
    val created: String? = null,
    val characters: StateFlow<PagingData<CharacterProfile>>? = null,
    val url: String? = null
)

sealed interface IntentScreenInfoEpisode {

    data class GetEpisode(val id: Int) : IntentScreenInfoEpisode
}