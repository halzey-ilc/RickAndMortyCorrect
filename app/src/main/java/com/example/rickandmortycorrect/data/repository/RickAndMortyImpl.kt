package com.example.rickandmortycorrect.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortycorrect.data.local.RickAndMortyDao
import com.example.rickandmortycorrect.data.remote.RickAndMortyApi
import com.example.rickandmortycorrect.data.remote.dto.character.CharacterData
import com.example.rickandmortycorrect.data.remote.dto.episode.EpisodeResult
import com.example.rickandmortycorrect.data.remote.dto.location.LocationResults
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.domain.model.EpisodeDomain
import com.example.rickandmortycorrect.domain.model.LocationDomain
import com.example.rickandmortycorrect.domain.repository.RickAndMortyRepository
import com.example.rickandmortycorrect.paging.CharactersPagingDataSource
import com.example.rickandmortycorrect.paging.EpisodePagingDataSource
import com.example.rickandmortycorrect.paging.LocationPagingDataSource
import com.example.rickandmortycorrect.utils.GenderState
import com.example.rickandmortycorrect.utils.StatusState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyImpl @Inject constructor(
    val api: RickAndMortyApi,
    private val dao: RickAndMortyDao
) : RickAndMortyRepository {


    override fun getAllCharacters(
        status: StatusState,
        gender: GenderState,
        name: String,
    ): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                CharactersPagingDataSource(
                    api,
                    statusState = status,
                    genderState = gender,
                    nameQuery = name
                )
            }
        ).flow
    }

    override suspend fun getCharacterDetailById(characterId: Int): CharacterData {
        return api.getCharacter(characterId)
    }

    override fun getAllLocation(): Flow<PagingData<LocationDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            pagingSourceFactory = {
                LocationPagingDataSource(api)
            }
        ).flow
    }

    override suspend fun getLocationDetailById(locationId: Int): LocationResults {
        return api.getLocation(locationId)
    }

    override fun getAllEpisode(): Flow<PagingData<EpisodeDomain>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                EpisodePagingDataSource(api)
            }
        ).flow
    }

    override suspend fun getEpisodeById(episodeId: Int): EpisodeResult {
        return api.getEpisodeById(episodeId)
    }

    override fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>> {
        return dao.getAllFavoriteCharacters()
    }

    override suspend fun insertMyFavoriteList(character: CharactersDomain) {
        dao.insertFavoriteCharacter(character = character)
    }

    override suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain) {
        dao.deleteFavoriteCharacter(character)
    }
}
