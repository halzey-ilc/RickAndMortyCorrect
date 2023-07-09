package com.example.rickandmortycorrect.presentation.episode.state

import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import com.example.rickandmortycorrect.domain.model.EpisodeListItem

data class EpisodeListState(
    val episodeList: PagingData<EpisodeListItem>? = PagingData.empty(),
)