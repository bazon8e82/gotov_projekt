package com.example.aseefm.networking.model

import java.io.Serializable

data class ArtistImage(
    val artistName: String,
    val imageUrl: String,
    val imageCaption: String,
    val id: Int?
) : Serializable