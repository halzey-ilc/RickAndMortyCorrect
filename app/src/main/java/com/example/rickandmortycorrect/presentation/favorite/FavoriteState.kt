package com.example.rickandmortycorrect.presentation.favorite

import com.example.rickandmortycorrect.domain.model.CharactersDomain

data class FavoriteState(
    val characterList: List<CharactersDomain> = emptyList(),
    val isError: Boolean = false
)