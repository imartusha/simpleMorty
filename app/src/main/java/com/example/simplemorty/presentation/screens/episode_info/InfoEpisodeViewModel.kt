package com.example.simplemorty.presentation.screens.episode_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.useCase.character.GetCharactersListForInfo
import com.example.simplemorty.domain.useCase.episode.GetInfoEpisodeByIdUseCase
import com.example.simplemorty.utils.formatDateString
import com.google.gson.Gson
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class InfoEpisodeViewModel(
    private val getInfoEpisodeByIdUseCase: GetInfoEpisodeByIdUseCase,
    private val getCharactersListForInfo: GetCharactersListForInfo
) : ViewModel() {

    private val _charactersForEpisode = MutableStateFlow<List<CharacterProfile>>(emptyList())
    val charactersForEpisode: StateFlow<List<CharacterProfile>> get() = _charactersForEpisode


    private val episodeByIdStateFlow: MutableSharedFlow<Episode?> = MutableSharedFlow()
    val _episodeByIdStateFlow: SharedFlow<Episode?> = episodeByIdStateFlow

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    private val charactersFromLocationStateFlow: MutableSharedFlow<List<CharacterProfile>?> =
        MutableSharedFlow()

    fun dispatch(intentScreenInfoEpisode: IntentScreenInfoEpisode) {
        when (intentScreenInfoEpisode) {
            is IntentScreenInfoEpisode.GetEpisode -> getEpisodeById(intentScreenInfoEpisode.id)
            else -> throw IllegalArgumentException("Unknown intent screen: $intentScreenInfoEpisode")
        }
    }

    private fun getEpisodeById(id: Int) {
        viewModelScope.launch {
            getInfoEpisodeByIdUseCase.getInfoEpisode(id).collectLatest {
                when (it) {
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }

                    is ApiResponse.Success -> {
                        episodeByIdStateFlow.emit(it.data)
                        progressStateFlow.value = false

                        it.data?.let { episode ->
                            getListCharactersForEpisode(episode)
                        }
                    }

                    is ApiResponse.Failure -> {
                        try {
                            val errorResponse = Gson().fromJson(
                                it.data!!.errorBody()!!.string(),
                                Episode::class.java
                            )
                            episodeByIdStateFlow.emit(errorResponse)
                        } catch (e: Exception) {
                            //   toastMessageObserver.setValue("Connection failed.")
                        }
                        progressStateFlow.value = false

                    }
                }
            }
        }
    }

    private suspend fun getListCharactersForEpisode(episode: Episode) {
        getCharactersListForInfo.getCharactersListForInfo(charactersIdes = episode.characters)
    }


    private fun updateEpisodeInfo(
        state: ScreenStateEpisode,
        episode: Episode,
        characters: List<CharacterProfile> // Принимаем список персонажей
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
        val characters: List<CharacterProfile> ? = null,
        val url: String? = null
    )

    sealed interface IntentScreenInfoEpisode {

        data class GetEpisode(val id: Int) : IntentScreenInfoEpisode
    }
}