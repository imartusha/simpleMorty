package com.example.simplemorty.presentation.screens.character_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Homeland
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.useCase.character.GetCharacterUseCase
import com.example.simplemorty.utils.formatDateString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InfoCharacterViewModel(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ScreenStateCharacter())
    val state: StateFlow<ScreenStateCharacter> = _state

    fun dispatch(intentScreenInfoCharacter: IntentScreenInfoCharacter) {
        when (intentScreenInfoCharacter) {
            is IntentScreenInfoCharacter.GetCharacter -> getCharacterById(intentScreenInfoCharacter.id)
            else -> throw IllegalArgumentException("Unknown intent screen: $intentScreenInfoCharacter")
        }
    }

    private fun getCharacterById(id: Int) {
        viewModelScope.launch {
            val characterProfile = getCharacterUseCase.getCharacterById(id = id)
            _state.update { state ->
                updateCharacterInfo(state, characterProfile)
            }
        }
    }

    private fun updateCharacterInfo(
        state: ScreenStateCharacter,
        characterProfile: CharacterProfile
    ): ScreenStateCharacter {
        return state.copy(
            name = characterProfile.name,
            gender = characterProfile.gender,
            species = characterProfile.species,
            status = characterProfile.status,
            type = characterProfile.type,
            image = characterProfile.image,
            created = characterProfile.created.formatDateString(),
            homeland = characterProfile.homeland,
            episode = characterProfile.episode,
            location = characterProfile.location
        )
    }

    data class ScreenStateCharacter(
        val name: String? = null,
        val gender: String? = null,
        val species: String? = null,
        val status: String? = null,
        val type: String? = null,
        val image: String? = null,
        val created: String? = null,
        val homeland: Homeland? = null,
        val episode: List<String>? = null,
        val location: Location? = null
    )

    sealed interface IntentScreenInfoCharacter {

        data class GetCharacter(val id: Int) : IntentScreenInfoCharacter
    }
}
