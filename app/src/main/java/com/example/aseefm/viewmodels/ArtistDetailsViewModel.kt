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
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ArtistDetailsViewModel(application: Application): AndroidViewModel(application) {

    val imagesList = MutableLiveData<ArrayList<ArtistImage>>()
    val topAlbums = MutableLiveData<ArrayList<Track>>()
    private val repository: AseeRepository

    init {
        val nbaDao = AseeDatabase.getDatabase(application)!!.nbaDao()
        repository = AseeRepository(nbaDao)
    }

    fun isPlayerFavorite(name: String) : Boolean {
        return repository.isArtistFavorite(name)
    }

    fun insertFavoritePlayer(artist: Artist) {
        repository.insertFavoriteArtist(artist.convertToFavoriteArtist())
    }

    fun deleteFavoritePlayer(artist: Artist) {
        repository.deleteFavoriteArtist(artist.convertToFavoriteArtist())
    }

    fun getTopTracks(name: String) {
        viewModelScope.launch {
            try {
                topAlbums.value = Network().getLastFMService().getTopTracksForArtist(
                    name, "74b5c3465c4d1c773e2dbfb48b561745", "json").toptracks.track as ArrayList<Track>
            } catch (e: Exception) {
            }
        }
    }

    fun getPlayerImages(name: String) {
        viewModelScope.launch {
            try {
                imagesList.value = Network().getMyApiService().getArtistImages(name) as ArrayList<ArtistImage>
            } catch (e: Exception) {
            }
        }
    }

    fun addImageForArtist(requestBody: RequestBody) {
        viewModelScope.launch {
            Network().getMyApiService().addImageForStation(requestBody)
        }
    }

    fun deletePlayerImage(imageId: String) {
        viewModelScope.launch {
            Network().getMyApiService().deleteImage(imageId)
        }
    }

    fun deleteAllArtistImages(imageList: ArrayList<ArtistImage>) {
        viewModelScope.launch {
            imageList.forEach {
                Network().getMyApiService().deleteImage(it.id!!.toString())
            }
        }
    }
}