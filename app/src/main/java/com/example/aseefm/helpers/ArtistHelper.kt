package com.example.aseefm.helpers

import android.widget.ImageView
import coil.load
import com.example.aseefm.R

fun loadArtistImagePlaceholder(position: Int, imageView: ImageView) {
    imageView.load(R.drawable.ic_album)
}