package com.example.rickandmortycorrect.presentation.character.viewmodel.states

import com.example.rickandmortycorrect.data.remote.dto.character.CharacterData
import com.example.rickandmortycorrect.domain.model.EpisodeDomain

data class CharacterDetailState(
    val character: CharacterData? = null,
    val navigateArgLocationId: Int? = null,
    val episodeList: List<EpisodeDomain>? = null,
    val isLoadingEpisodeInfo: Boolean = false
)