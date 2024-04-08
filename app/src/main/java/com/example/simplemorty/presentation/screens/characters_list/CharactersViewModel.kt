package com.example.simplemorty.presentation.screens.characters_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.useCase.character.GetAllCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
) : ViewModel() {

    private val _characters = MutableStateFlow<PagingData<CharacterProfile>>(PagingData.empty())
    val characters: StateFlow<PagingData<CharacterProfile>> get() = _characters

    fun dispatch(intentScreenCharacters: IntentScreenCharacters) {
        when (intentScreenCharacters) {
            is IntentScreenCharacters.GetAllCharacters -> getCharacters()
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            getAllCharactersUseCase.getAllCharacters()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _characters.value = pagingData
                }
        }
    }
}

sealed interface IntentScreenCharacters {

    data object GetAllCharacters : IntentScreenCharacters
}