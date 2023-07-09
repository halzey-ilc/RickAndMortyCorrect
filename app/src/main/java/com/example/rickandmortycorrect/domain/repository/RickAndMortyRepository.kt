package com.example.rickandmortycorrect.domain.repository

import androidx.paging.PagingData
import com.example.rickandmortycorrect.data.remote.dto.character.CharacterData
import com.example.rickandmortycorrect.data.remote.dto.episode.EpisodeResult
import com.example.rickandmortycorrect.data.remote.dto.location.LocationResults
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.domain.model.EpisodeDomain
import com.example.rickandmortycorrect.domain.model.LocationDomain
import com.example.rickandmortycorrect.utils.GenderState
import com.example.rickandmortycorrect.utils.StatusState
import retrofit2.http.Path
import kotlinx.coroutines.flow.Flow


interface RickAndMortyRepository {

    fun getAllCharacters(
        status: StatusState = StatusState.NONE,
        gender: GenderState = GenderState.NONE,
        name: String = "",
    ):Flow<PagingData<CharacterData>>

    suspend fun getCharacterDetailById(characterId: Int): CharacterData

    fun getAllLocation(): Flow<PagingData<LocationDomain>>

    suspend fun getLocationDetailById(locationId: Int): LocationResults

    fun getAllEpisode(): Flow<PagingData<EpisodeDomain>>

    suspend fun getEpisodeById(@Path("id") episodeId: Int): EpisodeResult

    fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>>

    suspend fun insertMyFavoriteList(character: CharactersDomain)

    suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain)
}