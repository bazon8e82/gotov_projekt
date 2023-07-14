package com.example.aseefm.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.aseefm.database.AseeDatabase
import com.example.aseefm.database.AseeRepository
import com.example.aseefm.networking.Network
import com.example.aseefm.networking.model.Track
import com.example.aseefm.networking.paging.TrackPagingSource

class TrackViewModel(application: Application): AndroidViewModel(application) {

    val favoriteTracks = MutableLiveData<ArrayList<Track>>()

    private val repository: AseeRepository

    init {
        val nbaDao = AseeDatabase.getDatabase(application)!!.nbaDao()
        repository = AseeRepository(nbaDao)
    }

    val flow = Pager(PagingConfig(pageSize = 20)){
        TrackPagingSource(Network().getLastFMService())
    }.flow.cachedIn(viewModelScope)

    fun insertFavoriteTrack(track: Track) {
        repository.insertFavoriteTrack(track.convertToFavoriteTrack())
    }

    fun deleteFavoriteTrack(track: Track) {
        repository.deleteFavoriteTrack(track.convertToFavoriteTrack())
    }

    fun getFavoriteTracks() {
        val tracks = ArrayList<Track>()
        repository.getFavoriteTracks().forEach {
            tracks.add(it.convertToTrack())
        }
        favoriteTracks.value = tracks
    }

}