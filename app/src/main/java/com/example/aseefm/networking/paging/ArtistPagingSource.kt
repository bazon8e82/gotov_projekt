package com.example.aseefm.networking.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.aseefm.networking.LastFMService
import com.example.aseefm.networking.model.Artist

class ArtistPagingSource(private val service: LastFMService): PagingSource<Int, Artist>() {

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = service.getTopArtists(20, nextPageNumber, "74b5c3465c4d1c773e2dbfb48b561745", "json")
            LoadResult.Page(
                data = response.artists.artist,
                prevKey = null,
                nextKey = response.artists.attr.page.toInt() + 1
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}