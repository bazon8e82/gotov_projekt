package com.example.aseefm.adapters.paging

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.aseefm.TrackActivity
import com.example.aseefm.R
import com.example.aseefm.databinding.TrackItemViewBinding
import com.example.aseefm.networking.model.Track

const val EXTRA_TRACK = "com.example.aseefm.extraTrack"
const val EXTRA_TRACK_IMAGE_PLACEHOLDER = "com.example.aseefm.extraTrackImagePlaceholder"

class TrackPagingAdapter(
    private val context: Context,
    private val favouriteTracks: ArrayList<Track>?,
    diffCallback: DiffUtil.ItemCallback<Track>,
    private val insertCallback: (Track) -> Unit,
    private val deleteCallback: (Track) -> Unit
) :
    PagingDataAdapter<Track, TrackPagingAdapter.TrackViewHolder>(diffCallback) {

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = TrackItemViewBinding.bind(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)

        holder.binding.tvPlayerName.text = track?.name
        holder.binding.tvPlayerTeam.text = track?.artist?.name
        holder.binding.ivPlayerImage.load(track?.image?.get(3)?.text) {
            placeholder(R.drawable.ic_album)
        }

        val exists = isTrackFavorite(track!!.name)

        holder.binding.btnFavorite.isActivated = exists

        holder.binding.btnFavorite.setOnClickListener {
            if (!exists) {
                holder.binding.btnFavorite.isActivated = true
                insertCallback.invoke(track)
            } else {
                holder.binding.btnFavorite.isActivated = false
                deleteCallback.invoke(track)
            }
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, TrackActivity::class.java).apply {
                putExtra(EXTRA_TRACK, track)
                putExtra(EXTRA_TRACK_IMAGE_PLACEHOLDER, R.drawable.ic_person)
            }
            context.startActivity(intent)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.track_item_view, parent, false)
        return TrackViewHolder(view)
    }



    private fun isTrackFavorite(track: String): Boolean {
        var exists = false
        favouriteTracks?.forEach {
            if (it.name == track) exists = true
        }
        return exists
    }
}