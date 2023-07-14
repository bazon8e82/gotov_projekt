package com.example.aseefm.database.converters

import androidx.room.TypeConverter
import com.example.aseefm.networking.model.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageTypeConverter {

    @TypeConverter
    fun fromCountryLangList(value: List<Image>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Image>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): List<Image> {
        val gson = Gson()
        val type = object : TypeToken<List<Image>>() {}.type
        return gson.fromJson(value, type)
    }
}
