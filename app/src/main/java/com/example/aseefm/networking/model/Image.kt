package com.example.aseefm.networking.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")
    val text: String,
    val size: String
) : java.io.Serializable