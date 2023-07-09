package com.example.rickandmortycorrect.paging

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortycorrect.data.remote.RickAndMortyApi
import com.example.rickandmortycorrect.data.remote.dto.location.toLocationDomain
import com.example.rickandmortycorrect.domain.model.LocationDomain
import java.lang.Exception
import javax.inject.Inject

class LocationPagingDataSource @Inject constructor(
    val api: RickAndMortyApi
) : PagingSource<Int, LocationDomain>() {

    override fun getRefreshKey(state: PagingState<Int, LocationDomain>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDomain> {

        val pageNumber = params.key ?: 1

        return try {
            val locationDto = api.getAllLocation(pageNumber)
            val data = locationDto.toLocationDomain()
            var nextPageNumber: Int? = null

            val uri = Uri.parse(locationDto.info.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()

            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}