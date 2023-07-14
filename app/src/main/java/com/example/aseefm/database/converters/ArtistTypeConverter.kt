package com.example.aseefm.database.converters

import androidx.room.TypeConverter
import com.example.aseefm.networking.model.Artist
import com.google.gson.Gson

class ArtistTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromArtist(artist: Artist): String {
        return gson.toJson(artist)
    }

    @TypeConverter
    fun toArtist(json: String): Artist {
        return gson.fromJson(json, Artist::class.java)
    }
}