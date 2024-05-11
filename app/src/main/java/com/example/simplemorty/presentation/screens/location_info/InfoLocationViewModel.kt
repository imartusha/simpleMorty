package com.example.simplemorty.presentation.screens.location_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemorty.data.models.response.ApiResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.useCase.character.GetCharactersListForInfo
import com.example.simplemorty.domain.useCase.episode.GetEpisodeByIdUseCase
import com.example.simplemorty.domain.useCase.location.GetLocationByIdUseCase
import com.example.simplemorty.presentation.screens.episode_info.InfoEpisodeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InfoLocationViewModel(
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getCharactersListForInfo: GetCharactersListForInfo
) : ViewModel() {
    private val _charactersForLocation = MutableStateFlow<List<CharacterProfile>>(emptyList())
    val charactersForLocation: StateFlow<List<CharacterProfile>> get() = _charactersForLocation

    private val locationByIdStateFlow: MutableSharedFlow<Location?> = MutableSharedFlow()
    val _locationByIdStateFlow: SharedFlow<Location?> = locationByIdStateFlow

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    fun dispatch(intentScreenInfoLocation:IntentScreenInfoLocation) {
        when (intentScreenInfoLocation) {
            is IntentScreenInfoLocation.GetLocation -> getLocationById(intentScreenInfoLocation.id)
            else -> throw IllegalArgumentException("Unknown intent screen: $intentScreenInfoLocation")
        }
    }
    private fun getLocationById(id: Int) {
        viewModelScope.launch {
            val locationFromDb = getLocationByIdUseCase.getLocationByIdFromDb(id = id)
            if (locationFromDb != null) {
                locationByIdStateFlow.emit((locationFromDb))
                getListCharactersForLocation(locationFromDb)
            } else {
                getLocationByIdUseCase.getLocationById(id).collectLatest {
                    when (it) {
                        is ApiResponse.Progress -> {
                            progressStateFlow.value = true
                        }

                        is ApiResponse.Success -> {
                            locationByIdStateFlow.emit(it.data)
                            progressStateFlow.value = false

                            it.data?.let { location ->
                                getListCharactersForLocation(location)
                            }
                        }

                        is ApiResponse.Failure -> {
                            try {
                                val errorResponse = Gson().fromJson(
                                    it.data!!.errorBody()!!.string(),
                                    Location::class.java
                                )
                                locationByIdStateFlow.emit(errorResponse)
                            } catch (e: Exception) {
                                //   toastMessageObserver.setValue("Connection failed.")
                            }
                            progressStateFlow.value = false

                        }
                    }
                }
            }
        }
    }

    private suspend fun getListCharactersForLocation(location: Location) {
        val characters = getCharactersListForInfo.getCharactersListForInfo(location)
        _charactersForLocation.update {
            characters
        }
    }

    sealed interface IntentScreenInfoLocation {

        data class GetLocation(val id: Int) : IntentScreenInfoLocation
    }
}