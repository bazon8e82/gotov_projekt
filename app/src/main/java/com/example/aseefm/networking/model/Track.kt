package com.example.aseefm.networking.model

import com.example.aseefm.database.model.FavoriteTrack
import java.io.Serializable

data class Track(
    val name: String,
    val artist: Artist,
    val url: String,
    val image: List<Image>
): Serializable {
    fun convertToFavoriteTrack(): FavoriteTrack {
        return FavoriteTrack(
            this.name,
            this.artist,
            this.url,
            this.image
        )
    }
}