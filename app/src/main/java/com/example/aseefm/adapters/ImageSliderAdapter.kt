package com.example.aseefm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.aseefm.R
import com.example.aseefm.networking.model.ArtistImage
import com.squareup.picasso.Picasso

class ImageSliderAdapter(
    private val context: Context,
    private val imageList: ArrayList<ArtistImage>
): PagerAdapter() {

    fun setImageList(newImageList: ArrayList<ArtistImage>) {
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()
    }

    fun updateImageList(image: ArtistImage) {
        imageList.add(image)
        notifyDataSetChanged()
    }

    fun clearImageList(imagesToRemove: ArrayList<ArtistImage>) {
        imageList.removeAll(imagesToRemove)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.artist_image_slider_item, null)
        val ivImages = view.findViewById<ImageView>(R.id.ivImages)

        imageList[position].imageUrl.let {
            Picasso.get().load(it).into(ivImages)
        }

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}