package com.example.aseefm.networking.paging

import android.content.res.Resources
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.aseefm.R
import com.example.aseefm.networking.LastFMService
import com.example.aseefm.networking.model.Track
import kotlin.coroutines.coroutineContext

class TrackPagingSource (private val service: LastFMService): PagingSource<Int, Track>() {

    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = service.getTopTracks(20, nextPageNumber, "74b5c3465c4d1c773e2dbfb48b561745", "json")
            LoadResult.Page(
                data = response.tracks.track,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}