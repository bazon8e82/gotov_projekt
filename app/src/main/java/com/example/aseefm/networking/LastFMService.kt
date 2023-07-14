package com.example.aseefm.networking

import com.example.aseefm.networking.model.response.*
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMService {

    @GET("?method=chart.gettopartists")
    suspend fun getTopArtists(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): TopArtistsResponse

    @GET("?method=chart.gettoptracks")
    suspend fun getTopTracks(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): TopTracksResponse

    @GET("artist.search")
    suspend fun searchArtists(
        @Query("artist") artist: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): ArtistSearchResponse

    @GET("?method=artist.gettoptracks")
    suspend fun getTopTracksForArtist(
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): ArtistTopTracksResponse
}
