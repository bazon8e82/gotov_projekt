package com.example.aseefm.networking.model

import com.example.aseefm.database.model.FavoriteArtist
import java.io.Serializable

data class Artist(
    val name: String,
    val listeners: Int,
    val playcount: Int,
    val url: String,
    val image: List<Image>
): Serializable {
    fun convertToFavoriteArtist(): FavoriteArtist {
        return FavoriteArtist(
            this.name,
            this.listeners,
            this.playcount,
            this.url,
            this.image
        )
    }
}