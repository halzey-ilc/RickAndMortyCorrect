package com.example.rickandmortycorrect.data.remote.dto.location

import com.example.rickandmortycorrect.data.remote.dto.Info
import com.example.rickandmortycorrect.domain.model.LocationDomain

data class LocationDto(
    val info: Info,
    val results: List<LocationResults>
)

fun LocationDto.toLocationDomain(): List<LocationDomain> {
    return results.map {
        LocationDomain(
            dimensions = it.dimension,
            id = it.id,
            name = it.name,
            type = it.type
        )
    }

}
