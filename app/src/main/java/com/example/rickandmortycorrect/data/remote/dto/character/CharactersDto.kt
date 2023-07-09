package com.example.rickandmortycorrect.data.remote.dto.character

import com.example.rickandmortycorrect.data.remote.dto.Info


data class CharactersDto(
    val info: Info,
    val results: List<CharacterData>
)
