package com.example.aseefm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.aseefm.database.converters.ImageTypeConverter
import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.model.Image
import java.io.Serializable

@Entity(tableName = "favoriteArtists")
@TypeConverters(ImageTypeConverter::class)
data class FavoriteArtist(
    @PrimaryKey
    val name: String,
    val listeners: Int,
    val playcount: Int,
    val url: String,
    val image: List<Image>
) : Serializable {
    fun convertToArtist(): Artist {
        return Artist(
            this.name,
            this.listeners,
            this.playcount,
            this.url,
            this.image,
        )
    }
}