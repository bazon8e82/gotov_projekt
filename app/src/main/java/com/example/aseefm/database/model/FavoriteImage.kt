package com.example.aseefm.database.model

import androidx.room.Entity

data class FavoriteImage(
    val url: String,
    val size: String,
) {
    fun convertToImage(): FavoriteImage {
        return FavoriteImage(url, size)
    }
}