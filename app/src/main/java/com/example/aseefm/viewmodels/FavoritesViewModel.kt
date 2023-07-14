package com.example.aseefm.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aseefm.database.AseeDatabase
import com.example.aseefm.database.AseeRepository
import com.example.aseefm.networking.Network
import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.model.ArtistImage
import com.example.aseefm.networking.model.Track
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application): AndroidViewModel(application) {

    val favoriteArtists = MutableLiveData<ArrayList<Artist>>()
    val favoriteTracks = MutableLiveData<ArrayList<Track>>()

    val artistImages = MutableLiveData<ArrayList<ArrayList<ArtistImage>>>()

    private val repository: AseeRepository

    init {
        val nbaDao = AseeDatabase.getDatabase(application)!!.nbaDao()
        repository = AseeRepository(nbaDao)
    }

    fun getFavoriteArtistsAndImages() {
        viewModelScope.launch {
            val artists = ArrayList<Artist>()
            repository.getFavoriteArtistsAsync().forEach {
                artists.add(it.convertToArtist())
            }

            val asyncTasks = artists.map { artist ->
                async {
                    try {
                        Network().getMyApiService().getArtistImages(artist.name)
                    } catch (e: Exception) {
                        arrayListOf(ArtistImage("0", "", "", null))
                    }
                }
            }
            val response = asyncTasks.awaitAll()

            favoriteArtists.value = artists
            if (response.isNotEmpty()) {
                artistImages.value = response as ArrayList<ArrayList<ArtistImage>>
            }
        }
    }

    fun deleteFavoriteArtist(artist: Artist) {
        repository.deleteFavoriteArtist(artist.convertToFavoriteArtist())
    }

    fun getFavoriteTracks() {
        viewModelScope.launch {
            val tracks = ArrayList<Track>()
            repository.getFavoriteTracksAsync().forEach {
                tracks.add(it.convertToTrack())
            }
            favoriteTracks.value = tracks
        }
    }

    fun deleteFavoriteTrack(track: Track) {
        repository.deleteFavoriteTrack(track.convertToFavoriteTrack())
    }
}