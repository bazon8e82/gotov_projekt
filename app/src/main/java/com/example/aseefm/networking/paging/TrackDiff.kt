package com.example.aseefm.networking.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.aseefm.networking.model.Track

object TrackDiff: DiffUtil.ItemCallback<Track>() {

    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}