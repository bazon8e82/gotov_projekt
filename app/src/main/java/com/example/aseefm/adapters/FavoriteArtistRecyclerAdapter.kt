package com.example.aseefm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.aseefm.ArtistActivity
import com.example.aseefm.R
import com.example.aseefm.adapters.paging.EXTRA_ARTIST
import com.example.aseefm.databinding.ArtistItemViewBinding
import com.example.aseefm.helpers.loadArtistImagePlaceholder
import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.model.ArtistImage
import com.squareup.picasso.Picasso

class FavoriteArtistRecyclerAdapter (
    private val context: Context,
    private val favoritesList: ArrayList<Artist>,
    private val imagesList: ArrayList<ArrayList<ArtistImage>>,
    private val deleteCallback: (Artist) -> Unit
) : RecyclerView.Adapter<FavoriteArtistRecyclerAdapter.FavoriteArtistViewHolder>() {

    class FavoriteArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ArtistItemViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteArtistViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.artist_item_view, parent, false)
        return FavoriteArtistViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: FavoriteArtistViewHolder, position: Int) {
        val artist = favoritesList[position]

        holder.binding.tvPlayerName.text = artist.name
        holder.binding.tvPlayerTeam.text = artist.listeners.toString()

        holder.binding.btnFavorite.setImageResource(R.drawable.ic_star_full)
        holder.binding.btnFavorite.setOnClickListener {
            deleteCallback.invoke(artist)
            favoritesList.remove(artist)
            notifyDataSetChanged()
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, ArtistActivity::class.java).apply {
                putExtra(EXTRA_ARTIST, artist)
            }
            context.startActivity(intent)
        }



        imagesList.forEach {
            for (i in 0..it.size) {
                if (it[0].artistName == artist.name) {
                    Picasso.get().load(it[0].imageUrl).fit().centerCrop()
                        .into(holder.binding.ivPlayerImage)
                    return
                } else {
                    Picasso.get().load(artist.image[3].text).fit().centerCrop()
                        .into(holder.binding.ivPlayerImage)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

}