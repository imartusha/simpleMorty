package com.example.simplemorty.presentation.screens.character_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Homeland
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.useCase.character.GetCharacterUseCase
import com.example.simplemorty.domain.useCase.character.IsCharacterInFavoritesUseCase
import com.example.simplemorty.domain.useCase.episode.GetEpisodesListForCharacterInfoUseCase
import com.example.simplemorty.utils.formatDateString
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InfoCharacterViewModel(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val isCharacterInFavoritesUseCase: IsCharacterInFavoritesUseCase,
    private val getEpisodesListForCharacterInfoUseCase: GetEpisodesListForCharacterInfoUseCase
) : ViewModel() {

    private val detailStateFlow: MutableSharedFlow<CharacterProfile?> =
        MutableSharedFlow()
    val _detailStateFlow: SharedFlow<CharacterProfile?> = detailStateFlow

    private val _episodesForCharacterInfo = MutableStateFlow<List<Episode>>(emptyList())
    val episodesForCharacterInfo: StateFlow<List<Episode>> get() = _episodesForCharacterInfo

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
//    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    private val toastMessageObserver: MutableLiveData<String> = MutableLiveData()

    fun dispatch(intentScreenInfoCharacter: IntentScreenInfoCharacter) {
        when (intentScreenInfoCharacter) {
            is IntentScreenInfoCharacter.GetCharacter -> getCharacterById(intentScreenInfoCharacter.id)
            else -> throw IllegalArgumentException("Unknown intent screen: $intentScreenInfoCharacter")
        }
    }

    private fun getCharacterById(id: Int) {

        viewModelScope.launch {
            val characterProfile = getCharacterUseCase.getCharacterByIdFromDb(id = id)
            if (characterProfile != null) {
                detailStateFlow.emit(characterProfile)
                getListEpisodesForCharacter(characterProfile)
            } else {
                getCharacterUseCase.getCharacterById(id).collectLatest {
                    when (it) {
                        is ApiResponse.Progress -> {
                            progressStateFlow.value = true
                        }

                        is ApiResponse.Success -> {
                            val character = it.data
                            val isFavorite = character?.let { id ->
                                isCharacterInFavoritesUseCase.isCharacterInFavorites(id.id!!)
                            }
                            it.data?.let { characterProfile ->
                                getListEpisodesForCharacter(characterProfile)
                            }
                            character?.isFavorite = isFavorite!!
                            detailStateFlow.emit(character)
                            progressStateFlow.value = false
                        }

                        is ApiResponse.Failure -> {
                            try {
                                val errorResponse = Gson().fromJson(
                                    it.data!!.errorBody()!!.string(),
                                    CharacterProfile::class.java
                                )
                                detailStateFlow.emit(errorResponse)
                            } catch (e: Exception) {
                                toastMessageObserver.setValue("Connection failed.")
                            }
                            progressStateFlow.value = false
                        }
                    }
                }
            }
        }
    }

    private suspend fun getListEpisodesForCharacter(characterProfile: CharacterProfile) {
        viewModelScope.launch {
            val episodes = getEpisodesListForCharacterInfoUseCase.getEpisodesListForCharacterInfo(
                characterProfile
            )
            _episodesForCharacterInfo.update {
                Log.e("MyTag", "в методе viewmodel getListEpisodesForCharacter апдейт список эпизодов")
                episodes
            }
        }
    }

    sealed interface IntentScreenInfoCharacter {

        data class GetCharacter(val id: Int) : IntentScreenInfoCharacter
    }
}



