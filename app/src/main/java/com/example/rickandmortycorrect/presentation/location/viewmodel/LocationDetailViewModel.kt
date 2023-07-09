package com.example.rickandmortycorrect.presentation.location.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycorrect.data.remote.dto.character.toCharactersDomain
import com.example.rickandmortycorrect.data.remote.dto.location.toLocationByIdDomain
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.domain.repository.RickAndMortyRepository
import com.example.rickandmortycorrect.presentation.location.state.LocationDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    val repository: RickAndMortyRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _state = MutableStateFlow(LocationDetailState())
    val state: StateFlow<LocationDetailState> get() = _state

    init {
        savedStateHandle.get<Int>("locationId")?.let {
            getLocationInfo(locationId = it)
        }
    }

    private fun getLocationInfo(locationId: Int) {
        if (_state.value.characterList ==null)  {
            try {
                _state.value = _state.value.copy(
                    isLoading = true
                )
                viewModelScope.launch {
                    val data =
                        repository.getLocationDetailById(locationId).toLocationByIdDomain()
                        _state.value = _state.value.copy(
                            locationInfo = data
                        )
                    val characterList = mutableListOf<CharactersDomain>()
                    data.residents.forEach{
                        val characterDeferred = async {
                            val charcterID = (it.split("/"))[5].toInt()
                            val characters = repository.getCharacterDetailById(charcterID)
                            characters
                        }
                        characterList.add(characterDeferred.await().toCharactersDomain())
                    }
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _state.value = _state.value.copy(
                        characterList = characterList
                    )
                }
            }catch (e:Exception){
                _state.value = _state.value.copy(
                    error = "An unexpected error occurred"
                )
            }catch (e:HttpException){
                _state.value = _state.value.copy(
                    error = "Please check your internet connection"
                )
            }
        }

    }
}