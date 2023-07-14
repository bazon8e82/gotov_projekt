package com.example.aseefm.networking.model.response

import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.model.Attr
import com.google.gson.annotations.SerializedName

data class TopArtistsResponse(
    val artists: TopArtists
)

data class TopArtists(
    val artist: List<Artist>,
    @SerializedName("@attr")
    val attr: Attr
)
