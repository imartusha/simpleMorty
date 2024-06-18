package com.example.simplemorty.presentation.screens.characters_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.useCase.character.GetAllCharactersUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
) : ViewModel() {

    private val _characters = MutableStateFlow<PagingData<CharacterProfile>>(PagingData.empty())
    val characters: StateFlow<PagingData<CharacterProfile>> get() = _characters

    private val _event = MutableSharedFlow<CharactersScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        getCharacters()
    }

    fun dispatch(intent: IntentScreenCharacters) {
        when (intent) {
            is IntentScreenCharacters.ClickOnCharacter -> {
                Log.d("MyTag", "Dispatching ClickOnCharacter event for character ID: ${intent.characterProfile.id}")
                navigateToCharacter(characterProfile = intent.characterProfile)
            }
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                getAllCharactersUseCase.getAllCharacters()
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _characters.value = pagingData
                        Log.d("MyTag", "New data emitted to UI: $pagingData")
                    }
            } catch (e: Exception) {
                Log.e("MyTag", "Error in getCharacters: ${e.message}", e)
            }
        }
    }

    private fun navigateToCharacter(characterProfile: CharacterProfile) {
        viewModelScope.launch {
            _event.emit(CharactersScreenEvent.NavigateToCharacter(characterProfile.id))
        }
    }
}

sealed interface IntentScreenCharacters {
    data class ClickOnCharacter(val characterProfile: CharacterProfile) : IntentScreenCharacters
}

sealed interface CharactersScreenEvent {
    data class NavigateToCharacter(val characterId: Int) : CharactersScreenEvent
}
