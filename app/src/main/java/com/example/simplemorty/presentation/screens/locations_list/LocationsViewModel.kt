package com.example.simplemorty.presentation.screens.locations_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.useCase.location.GetAllLocationsUseCase
import com.example.simplemorty.domain.useCase.location.GetInfoLocationByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class LocationsViewModel (
    private val getInfoLocationByIdUseCase: GetInfoLocationByIdUseCase,
    private val getAllLocationsUseCase: GetAllLocationsUseCase
) : ViewModel() {

    private val locationStateFlow: MutableSharedFlow<PagingData<Location>> = MutableSharedFlow()
    val _locationStateFlow: SharedFlow<PagingData<Location>> = locationStateFlow

    private val _locationLiveData = MutableLiveData<Location>()
    val locationLiveData: LiveData<Location> = _locationLiveData

    private val _locationsListLiveData = MutableLiveData<List<Location>>()
    val locationsListLiveData: LiveData<List<Location>> = _locationsListLiveData

    fun dispatch(intentScreenLocations: IntentScreenLocations) {
        when (intentScreenLocations) {
         //   is IntentScreenLocations.GetLocation -> getLocation(intentScreenLocations.id)
            is IntentScreenLocations.GetAllLocations -> getAllLocations()
            is IntentScreenLocations.GetLocation -> TODO()
        }
    }

//    private fun getLocation(id: Int) {
//        viewModelScope.launch {
//            val episode = getInfoLocationByIdUseCase.getInfoCLocation(id = id)
//            _locationLiveData.postValue(episode)
//        }
//    }

    private fun getAllLocations() {
        viewModelScope.launch {
            getAllLocationsUseCase.getAllLocations().flowOn(Dispatchers.IO).collectLatest {
                locationStateFlow.emit(it)
            }
        }
    }
}

sealed interface IntentScreenLocations {

    data class GetLocation(val id: Int) : IntentScreenLocations
    data object GetAllLocations : IntentScreenLocations
}
