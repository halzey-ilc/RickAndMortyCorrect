package com.example.rickandmortycorrect.data.remote.dto.episode

import com.example.rickandmortycorrect.data.remote.dto.Info
import com.example.rickandmortycorrect.domain.model.EpisodeDomain

data class EpisodeDto(
    val info: Info,
    val result: List<EpisodeResult>
)

fun EpisodeDto.toEpisodeDomain(): List<EpisodeDomain> {
    return result.map {
        EpisodeDomain(
            id = it.id,
            name = it.name,
            air_date = it.air_date,
            episode = it.episode,
            characters = it.characters
        )
    }
}
