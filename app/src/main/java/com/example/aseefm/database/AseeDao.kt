package com.example.aseefm.database

import androidx.room.*
import com.example.aseefm.database.model.FavoriteArtist
import com.example.aseefm.database.model.FavoriteTrack

@Dao
interface AseeDao {

    //Favorite artist

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteArtist(player: FavoriteArtist)

    @Query("SELECT * FROM favoriteArtists")
    fun getFavoriteArtists(): List<FavoriteArtist>

    @Query("SELECT * FROM favoriteArtists")
    fun getFavoriteArtistsAsync(): List<FavoriteArtist>

    @Query("SELECT * FROM favoriteArtists WHERE name = :name")
    fun isArtistFavorite(name: String): Boolean

    @Delete
    fun deleteFavoriteArtist(artist: FavoriteArtist)

    @Query("DELETE FROM favoriteArtists")
    fun deleteFavoriteArtists()


    //Favorite track

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTrack(team: FavoriteTrack)

    @Query("SELECT * FROM favoriteTracks")
    fun getFavoriteTracks(): List<FavoriteTrack>

    @Query("SELECT * FROM favoriteTracks")
    fun getFavoriteTracksAsync(): List<FavoriteTrack>

    @Query("SELECT * FROM favoriteTracks WHERE name = :name")
    fun isTrackFavorite(name: String): Boolean

    @Delete
    fun deleteFavoriteTrack(team: FavoriteTrack)

    @Query("DELETE FROM favoriteTracks")
    fun deleteFavoriteTracks()

}