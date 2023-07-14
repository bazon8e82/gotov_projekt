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
import com.example.aseefm.ArtistActivity
import com.example.aseefm.R
import com.example.aseefm.databinding.ArtistItemViewBinding
import com.example.aseefm.helpers.loadArtistImagePlaceholder
import com.example.aseefm.networking.model.Artist
import com.squareup.picasso.Picasso

const val EXTRA_ARTIST = "com.example.aseefm.extraArtist"
const val EXTRA_ARTIST_IMAGE_PLACEHOLDER = "com.example.aseefm.extraArtistImagePlaceholder"

class ArtistPagingAdapter(
    private val context: Context,
    private val favouriteArtists: ArrayList<Artist>?,
    diffCallback: DiffUtil.ItemCallback<Artist>,
    private val insertCallback: (Artist) -> Unit,
    private val deleteCallback: (Artist) -> Unit
) : PagingDataAdapter<Artist, ArtistPagingAdapter.ArtistViewHolder>(diffCallback) {

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ArtistItemViewBinding.bind(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = getItem(position)

        holder.binding.tvPlayerName.text = artist?.name
        holder.binding.tvPlayerTeam.text = artist?.listeners.toString()
        holder.binding.ivPlayerImage.load(artist?.image?.get(3)?.text) {
            placeholder(R.drawable.ic_person)
        }
        val exists = isArtistFavorite(artist!!.name)

        holder.binding.btnFavorite.isActivated = exists

        holder.binding.btnFavorite.setOnClickListener {
            if (!exists) {
                holder.binding.btnFavorite.isActivated = true
                insertCallback.invoke(artist)
            } else {
                holder.binding.btnFavorite.isActivated = false
                deleteCallback.invoke(artist)
            }
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, ArtistActivity::class.java).apply {
                putExtra(EXTRA_ARTIST, artist)
            }
            context.startActivity(intent)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.artist_item_view, parent, false)
        return ArtistViewHolder(view)
    }



    private fun isArtistFavorite(artist: String): Boolean {
        var exists = false
        favouriteArtists?.forEach {
            if (it.name == artist) exists = true
        }
        return exists
    }
}