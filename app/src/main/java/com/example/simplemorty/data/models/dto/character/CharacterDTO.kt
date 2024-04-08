package com.example.simplemorty.data.models.dto.character

import com.example.simplemorty.data.models.entity.character.CharacterEntity
import com.example.simplemorty.data.models.entity.character.mapEntityToCharacterProfile
import com.example.simplemorty.data.models.entity.character.mapFromDTOToCharacterEntity
import com.example.simplemorty.data.models.entity.character.mapToCharacterEntity
import com.example.simplemorty.data.models.response.Result
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.models.Homeland
import com.google.gson.annotations.SerializedName

class CharacterDTO(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String?,

    @SerializedName("origin")
    val homeland: Homeland?,

    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Result


//    internal fun mapCommonResponseToCharacterDTO(commonResponse: CommonResponse<CharacterDTO>): List<CharacterDTO> {
//        return commonResponse.results.map { result ->
//            map
//        }
//    }
    internal fun mapDtoToCharacterProfile(characterDTO: CharacterDTO): CharacterProfile {
        return CharacterProfile(
            created = characterDTO.created,
            episode = characterDTO.episode,
            gender = characterDTO.gender,
            id = characterDTO.id,
            image = characterDTO.image,
            location = characterDTO.location,
            name = requireNotNull(characterDTO.name),
            homeland = requireNotNull(characterDTO.homeland),
            species = characterDTO.species,
            status = characterDTO.status,
            type = characterDTO.type,
            url = characterDTO.url
        )
    }

    internal fun mapDtoToCharacterEntity(characterDTO: CharacterDTO): CharacterEntity {
        return CharacterEntity(
            created = characterDTO.created,
            episode = characterDTO.episode,
            gender = characterDTO.gender,
            id = characterDTO.id,
            image = characterDTO.image,
            location = characterDTO.location,
            name = requireNotNull(characterDTO.name),
            homeland = requireNotNull(characterDTO.homeland),
            species = characterDTO.species,
            status = characterDTO.status,
            type = characterDTO.type,
            url = characterDTO.url
        )
    }

    internal fun mapProfileToCharacterDto(characterProfile: CharacterProfile): CharacterDTO {
        return CharacterDTO(
            created = characterProfile.created,
            episode = characterProfile.episode,
            gender = characterProfile.gender,
            id = characterProfile.id,
            image = characterProfile.image,
            location = characterProfile.location,
            name = characterProfile.name,
            homeland = characterProfile.homeland,
            species = characterProfile.species,
            status = characterProfile.status,
            type = characterProfile.type,
            url = characterProfile.url
        )
    }

    fun mapCharacterDTOsToProfiles(characters: List<CharacterDTO>): List<CharacterProfile> {
        return characters.map { characterDTO ->
            mapDtoToCharacterProfile(characterDTO)
        }
    }

    fun mapCharacterEntitiesToProfiles(characters: List<CharacterEntity>): List<CharacterProfile> {
        return characters.map { characterEntity ->
            mapEntityToCharacterProfile(characterEntity)
        }
    }

    fun mapProfilesToCharacterEntities(characters: List<CharacterProfile>): List<CharacterEntity> {
        return characters.map { characterProfile ->
            mapToCharacterEntity(characterProfile)
        }
    }

    fun mapDTOsToCharacterEntities(characters: List<CharacterDTO>): List<CharacterEntity> {
        return characters.map { characterDTO ->
            mapFromDTOToCharacterEntity(characterDTO)
        }
    }