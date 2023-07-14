package com.example.aseefm.networking.model.response

import com.example.aseefm.networking.model.ArtistMatches

data class ArtistSearchResponse(
    val results: ArtistSearchResults
)

data class ArtistSearchResults(
    val artistmatches: ArtistMatches
)