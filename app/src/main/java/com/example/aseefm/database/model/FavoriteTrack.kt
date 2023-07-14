package com.example.aseefm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.aseefm.database.converters.ArtistTypeConverter
import com.example.aseefm.database.converters.ImageTypeConverter
import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.model.Image
import com.example.aseefm.networking.model.Track
import java.io.Serializable

@Entity(tableName = "favoriteTracks")
@TypeConverters(ArtistTypeConverter::class, ImageTypeConverter::class)
data class FavoriteTrack(
    @PrimaryKey
    val name: String,
    val artist: Artist,
    val url: String,
    val image: List<Image>,
) : Serializable {

    fun convertToTrack(): Track {
        return Track(
            this.name,
            this.artist,
            this.url,
            this.image
        )
    }

}