package com.example.rickandmortycorrect.presentation.episode.state

import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.domain.model.EpisodeDetail

data class EpisodeDetailState(
    val isLoading: Boolean = false,
    val characterList: List<CharactersDomain>? = null,
    val episodeId: Int = 0,
    val episodeDetailInfo: EpisodeDetail? = null,
    val error: String = ""
)