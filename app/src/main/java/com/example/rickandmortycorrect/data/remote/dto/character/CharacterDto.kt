package com.example.rickandmortycorrect.data.remote.dto.character

import com.example.rickandmortycorrect.domain.model.CharacterDomain
import org.w3c.dom.CharacterData


data class CharacterDto(
    val result: CharacterData
)


fun CharacterDto.toCharacter(): CharacterDomain {
    return CharacterDomain(
        result = result
    )
}