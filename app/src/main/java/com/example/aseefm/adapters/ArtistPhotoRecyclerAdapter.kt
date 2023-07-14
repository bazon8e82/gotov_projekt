package com.example.aseefm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.aseefm.R
import com.example.aseefm.databinding.ArtistPhotoItemBinding
import com.example.aseefm.networking.model.ArtistImage
import com.squareup.picasso.Picasso

class ArtistPhotoRecyclerAdapter(
    private val context: Context,
    private val imageList: ArrayList<ArtistImage>
) : RecyclerView.Adapter<ArtistPhotoRecyclerAdapter.ArtistPhotoViewHolder>() {

    var editSwitch = false
    var deleteCallback: ((ArtistImage) -> Unit)? = null
    var updateSliderCallback: ((ArrayList<ArtistImage>) -> Unit)? = null
    var showErrorMessageCallback: ((String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setImageList(newImageList: ArrayList<ArtistImage>) {
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearImageList() {
        imageList.clear()
        notifyDataSetChanged()
    }

    class ArtistPhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ArtistPhotoItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistPhotoViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.artist_photo_item, parent, false)
        return ArtistPhotoViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ArtistPhotoViewHolder, position: Int) {
        val playerImage = imageList[position]

        holder.binding.tvImageTitle.text = playerImage.imageCaption
        Picasso.get().load(playerImage.imageUrl).into(holder.binding.ivPlayerImage)

        if (editSwitch) {
            setViewMargin(holder, 64f)
            holder.binding.editContainer.visibility = View.VISIBLE
            holder.binding.btnDelete.setOnClickListener {
                try {
                    deleteCallback?.invoke(playerImage)
                    imageList.remove(playerImage)
                    updateSliderCallback?.invoke(imageList)
                    notifyDataSetChanged()
                } catch (e: Exception) {
                    showErrorMessageCallback?.invoke(context.getString(R.string.error_deleting_image))
                }
            }
        } else {
            setViewMargin(holder, 2f)
            holder.binding.editContainer.visibility = View.GONE
        }
    }

    private fun setViewMargin(holder: ArtistPhotoViewHolder, value: Float) {
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            context.resources.displayMetrics
        )
        val param = holder.binding.photoItemContainer.layoutParams as ConstraintLayout.LayoutParams
        param.setMargins(0, 0, px.toInt(), 0)
        holder.binding.photoItemContainer.layoutParams = param
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

}