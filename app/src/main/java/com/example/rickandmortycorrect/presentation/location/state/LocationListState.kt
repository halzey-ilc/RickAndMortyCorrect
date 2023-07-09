package com.example.rickandmortycorrect.presentation.location.state

import androidx.paging.PagingData
import com.example.rickandmortycorrect.domain.model.LocationDomain

data class LocationListState (
    val locationData: PagingData<LocationDomain>? = PagingData.empty()
        )