package com.example.simplemorty.presentation.screens.locations_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.useCase.location.GetAllLocationsUseCase
import com.example.simplemorty.domain.useCase.location.GetLocationByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class LocationsViewModel(
    private val getAllLocationsUseCase: GetAllLocationsUseCase
) : ViewModel() {

    private val _locationListStateFlow: MutableSharedFlow<PagingData<Location>> = MutableSharedFlow()
    val locationListStateFlow: SharedFlow<PagingData<Location>> = _locationListStateFlow

    fun dispatch(intentScreenLocations: IntentScreenLocations) {
        when (intentScreenLocations) {
            is IntentScreenLocations.GetAllLocations -> fetchLocations()
        }
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            getAllLocationsUseCase.getAllLocations().flowOn(Dispatchers.IO).collect {
                _locationListStateFlow.emit(it)
            }
        }
    }
}

sealed interface IntentScreenLocations {

    data object GetAllLocations : IntentScreenLocations
}
