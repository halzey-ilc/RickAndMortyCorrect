package com.example.rickandmortycorrect.presentation.location.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rickandmortycorrect.domain.repository.RickAndMortyRepository
import com.example.rickandmortycorrect.presentation.location.state.LocationListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    val repository: RickAndMortyRepository
): ViewModel() {
    private val _state = MutableStateFlow(LocationListState())
    val state: StateFlow<LocationListState> get() = _state

    val getLocationData = repository.getAllLocation()
}