package com.example.rickandmortycorrect.presentation.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.domain.repository.RickAndMortyRepository
import com.example.rickandmortycorrect.presentation.favorite.FavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repository: RickAndMortyRepository
): ViewModel(){

    private val _state = MutableStateFlow<FavoriteState>(FavoriteState())
    val state: StateFlow<FavoriteState> get() = _state


    init {
        viewModelScope.launch {
            try {
                getFavoriteCharacters().collect {
                    _state.value = _state.value.copy(
                        characterList = it,
                        isError = true
                    )
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    characterList = emptyList(),
                    isError = true
                )
            }

        }


    }

    suspend fun getFavoriteCharacters(): Flow<List<CharactersDomain>> {
        return repository.getAllFavoriteCharacters()
    }

    fun deleteCharacter(charactersDomain: CharactersDomain) {
        viewModelScope.launch {
            repository.deleteCharacterFromMyFavoriteList(charactersDomain)
        }
    }
}