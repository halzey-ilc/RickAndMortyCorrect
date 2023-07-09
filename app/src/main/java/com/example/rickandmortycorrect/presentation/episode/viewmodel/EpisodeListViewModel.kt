package com.example.rickandmortycorrect.presentation.episode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.rickandmortycorrect.domain.model.EpisodeDomain
import com.example.rickandmortycorrect.domain.model.EpisodeListItem
import com.example.rickandmortycorrect.domain.model.season
import com.example.rickandmortycorrect.domain.repository.RickAndMortyRepository
import com.example.rickandmortycorrect.presentation.episode.state.EpisodeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
): ViewModel(){

    private val _state = MutableStateFlow(EpisodeListState())
    val state: StateFlow<EpisodeListState> get() = _state
    init {
        viewModelScope.launch {
            getEpisodeList().collect{
                _state.value = _state.value.copy(
                    episodeList = it
                )
            }
        }
    }

    fun getEpisodeList() : Flow<PagingData<EpisodeListItem>>{
        return repository.getAllEpisode()
            .map { pagingData -> pagingData.map { EpisodeListItem.EpisodeItem(it) } }
            .map {
                it.insertSeparators{ before, after ->
                    if (after ==null){
                        return@insertSeparators null
                    }
                    if (before == null) {
                        return@insertSeparators EpisodeListItem.SeparatorItem("Season 1")
                    }

                    if (before.season != after.season) {
                        return@insertSeparators EpisodeListItem.SeparatorItem(
                            "Season ${after.season}"
                        )
                    } else {
                        null
                    }
                }
            }
    }
}