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
import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.model.ArtistImage
import com.example.aseefm.networking.paging.ArtistPagingSource

class ArtistsViewModel(application: Application): AndroidViewModel(application) {

    val artistImages = MutableLiveData<ArrayList<ArtistImage>>()
    val favoriteArtists = MutableLiveData<ArrayList<Artist>>()

    private val repository: AseeRepository

    init {
        val nbaDao = AseeDatabase.getDatabase(application)!!.nbaDao()
        repository = AseeRepository(nbaDao)
    }

    val flow = Pager(PagingConfig(pageSize = 20)){
        ArtistPagingSource(Network().getLastFMService())
    }.flow.cachedIn(viewModelScope)

    fun insertFavoriteArtist(artist: Artist) {
        repository.insertFavoriteArtist(artist.convertToFavoriteArtist())
    }

    fun deleteFavoriteArtist(artist: Artist) {
        repository.deleteFavoriteArtist(artist.convertToFavoriteArtist())
    }

    fun getFavoriteArtists() {
        val artists = ArrayList<Artist>()
        repository.getFavoriteArtists().forEach {
            artists.add(it.convertToArtist())
        }
        favoriteArtists.value = artists
    }

}