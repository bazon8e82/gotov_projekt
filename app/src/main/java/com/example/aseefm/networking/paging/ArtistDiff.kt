package com.example.aseefm.networking.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.aseefm.networking.model.Artist

object ArtistDiff: DiffUtil.ItemCallback<Artist>() {

    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}