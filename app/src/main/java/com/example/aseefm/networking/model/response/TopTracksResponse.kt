package com.example.aseefm.networking.model.response

import com.example.aseefm.networking.model.Track

data class ArtistTopTracksResponse(
    val toptracks: TopTracks
)

data class TopTracksResponse(
    val tracks: TopTracks
)

data class TopTracks(
    val track: List<Track>
)