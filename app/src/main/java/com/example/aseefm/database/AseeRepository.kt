package com.example.aseefm.database

import com.example.aseefm.database.model.FavoriteArtist
import com.example.aseefm.database.model.FavoriteTrack

class AseeRepository(private val aseeDao: AseeDao) {

    //Favorite artist

    fun getFavoriteArtists(): List<FavoriteArtist> {
        return aseeDao.getFavoriteArtists()
    }

    suspend fun getFavoriteArtistsAsync(): List<FavoriteArtist> {
        return aseeDao.getFavoriteArtistsAsync()
    }

    fun insertFavoriteArtist(player: FavoriteArtist) {
        aseeDao.insertFavoriteArtist(player)
    }

    fun isArtistFavorite(name: String): Boolean{
        return aseeDao.isArtistFavorite(name)
    }

    fun deleteFavoriteArtist(player: FavoriteArtist) {
        aseeDao.deleteFavoriteArtist(player)
    }

    fun deleteFavoriteArtists() {
        aseeDao.deleteFavoriteArtists()
    }

    //Favorite tracks

    fun getFavoriteTracks(): List<FavoriteTrack> {
        return aseeDao.getFavoriteTracks()
    }

    suspend fun getFavoriteTracksAsync(): List<FavoriteTrack> {
        return aseeDao.getFavoriteTracksAsync()
    }

    fun isTrackFavorite(name: String): Boolean {
        return aseeDao.isTrackFavorite(name)
    }

    fun insertFavoriteTrack(team: FavoriteTrack) {
        aseeDao.insertFavoriteTrack(team)
    }

    fun deleteFavoriteTrack(team: FavoriteTrack) {
        aseeDao.deleteFavoriteTrack(team)
    }

    fun deleteFavoriteTracks() {
        aseeDao.deleteFavoriteTracks()
    }
}