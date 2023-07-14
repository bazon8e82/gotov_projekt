package com.example.aseefm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aseefm.database.converters.ArtistTypeConverter
import com.example.aseefm.database.converters.ImageTypeConverter
import com.example.aseefm.database.model.FavoriteArtist
import com.example.aseefm.database.model.FavoriteTrack

@Database(
    entities = [FavoriteArtist::class, FavoriteTrack::class],
    version = 1
)
abstract class AseeDatabase : RoomDatabase() {
    abstract fun nbaDao(): AseeDao

    companion object {
        private var instance: AseeDatabase? = null

        fun getDatabase(context: Context): AseeDatabase? {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance
        }

        private fun buildDatabase(context: Context): AseeDatabase =
            Room.databaseBuilder(context, AseeDatabase::class.java, "AseeDatabase")
                .allowMainThreadQueries().build()
    }

}