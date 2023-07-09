package com.example.rickandmortycorrect.presentation.location.state

import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.domain.model.LocationByIdDomain

data class LocationDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val locationInfo: LocationByIdDomain? = null,
    val locationID: Int = 0,
    val characterList: List<CharactersDomain>? = null
)