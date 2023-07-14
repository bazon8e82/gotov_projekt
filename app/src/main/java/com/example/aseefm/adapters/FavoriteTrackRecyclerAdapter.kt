package com.example.aseefm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aseefm.TrackActivity
import com.example.aseefm.R
import com.example.aseefm.adapters.paging.EXTRA_TRACK
import com.example.aseefm.databinding.TrackItemViewBinding
import com.example.aseefm.networking.model.Track
import com.squareup.picasso.Picasso

class FavoriteTrackRecyclerAdapter(
    private val context: Context,
    private val favoritesList: ArrayList<Track>,
    private val deleteCallback: (Track) -> Unit
) : RecyclerView.Adapter<FavoriteTrackRecyclerAdapter.FavoriteTrackViewHolder>() {

    class FavoriteTrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = TrackItemViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTrackViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.track_item_view, parent, false)
        return FavoriteTrackViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: FavoriteTrackViewHolder, position: Int) {
        val track = favoritesList[position]

        holder.binding.tvPlayerName.text = track.name
        holder.binding.tvPlayerTeam.text = track.artist.name
        Picasso.get().load(track.image[3].text).fit().centerCrop()
            .into(holder.binding.ivPlayerImage)
        holder.binding.btnFavorite.setImageResource(R.drawable.ic_star_full)
        holder.binding.btnFavorite.setOnClickListener {
            deleteCallback.invoke(track)
            favoritesList.remove(track)
            notifyDataSetChanged()
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, TrackActivity::class.java).apply {
                putExtra(EXTRA_TRACK, track)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

}