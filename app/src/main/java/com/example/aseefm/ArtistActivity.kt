package com.example.aseefm

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.aseefm.adapters.ImageSliderAdapter
import com.example.aseefm.adapters.TrackRecyclerAdapter
import com.example.aseefm.adapters.paging.EXTRA_ARTIST
import com.example.aseefm.adapters.paging.TrackPagingAdapter
import com.example.aseefm.databinding.ActivityArtistBinding
import com.example.aseefm.helpers.isOnline
import com.example.aseefm.helpers.showCustomDialog
import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.model.ArtistImage
import com.example.aseefm.viewmodels.ArtistDetailsViewModel
import com.example.aseefm.views.AddPhotoBottomSheet
import com.example.aseefm.views.EditPhotosBottomSheet
import com.google.android.material.snackbar.Snackbar

class ArtistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtistBinding
    private val viewModel: ArtistDetailsViewModel by viewModels()
    private val imageSliderAdapter by lazy { ImageSliderAdapter(this, arrayListOf()) }
    private var playerImageList = ArrayList<ArtistImage>()
    private lateinit var selectedArtist: Artist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedArtist = intent.getSerializableExtra(EXTRA_ARTIST) as Artist
        val isFavorite = viewModel.isPlayerFavorite(selectedArtist.name)

        setupActionBar()
        setButtonListener(isFavorite)

        binding.rvAlbums.layoutManager = LinearLayoutManager(this)
        binding.listeners.text = "Slušatelji:: " + selectedArtist.listeners
        binding.clicks.text = "Broj slušanja: " + selectedArtist.playcount
        viewModel.topAlbums.observe(this) {
            Log.d("e", "e")
            val adapter = TrackRecyclerAdapter(
                this, it
            )
            binding.rvAlbums.adapter = adapter
        }

        if (imageSliderAdapter.count == 0) {
            binding.ivPlaceholder.load(selectedArtist.image[3].text)
            binding.imageProgressBar.visibility = ProgressBar.GONE
        }

        binding.imageSliderViewPager.adapter = imageSliderAdapter

        viewModel.imagesList.observe(this) { imageList ->
            binding.imageProgressBar.visibility = View.VISIBLE

            playerImageList.addAll(imageList)

            binding.ivPlaceholder.visibility = View.GONE
            imageSliderAdapter.setImageList(playerImageList)
            binding.indicator.setViewPager(binding.imageSliderViewPager)

            binding.imageProgressBar.visibility = View.GONE

            binding.btnEditImage.setOnClickListener {
                openEditPhotoBottomSheet(imageList)
            }
        }

        binding.btnAddImage.setOnClickListener {
            openAddPhotoBottomSheet()
        }

        if (this.isOnline()) {
            viewModel.getTopTracks(selectedArtist.name)
            viewModel.getPlayerImages(selectedArtist.name)
        } else {
            showCustomDialog(getString(R.string.no_internet_connection), this)
        }
    }

    private fun openAddPhotoBottomSheet() {
        val bottomSheet = AddPhotoBottomSheet(selectedArtist, {
            viewModel.addImageForArtist(it)
        }, {
            imageSliderAdapter.updateImageList(it)
            playerImageList.add(it)
            binding.indicator.setViewPager(binding.imageSliderViewPager)
        }, {
            binding.ivPlaceholder.visibility = View.GONE
            binding.imageProgressBar.visibility = View.GONE
        })
        bottomSheet.show(this.supportFragmentManager, "AddPhoto")
    }

    private fun openEditPhotoBottomSheet(imageList: ArrayList<ArtistImage>) {
        val bottomSheet = EditPhotosBottomSheet(imageList, {
            viewModel.deletePlayerImage(it.artistName)
            playerImageList.remove(it)
        }, {
            imageSliderAdapter.setImageList(it)
            playerImageList.clear()
            playerImageList.addAll(it)
            binding.indicator.setViewPager(binding.imageSliderViewPager)
        },
            {
                viewModel.deleteAllArtistImages(it)
                playerImageList.removeAll(it.toSet())
                imageSliderAdapter.clearImageList(it)
                if (playerImageList.isEmpty()) binding.ivPlaceholder.visibility = View.VISIBLE
                binding.indicator.setViewPager(binding.imageSliderViewPager)
            }, {
                val snack = Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                snack.view.setBackgroundResource(R.drawable.background_custom_snackbar)
                snack.show()
            })
        bottomSheet.show(this.supportFragmentManager, "EditPhoto")
    }


    private fun setButtonListener(isFavorite: Boolean) {
        binding.btnFavorite.setOnClickListener {
            if (!isFavorite) {
                binding.btnFavorite.isActivated = true
                viewModel.insertFavoritePlayer(selectedArtist)
            } else {
                binding.btnFavorite.isActivated = false
                viewModel.deleteFavoritePlayer(selectedArtist)
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.playerToolbar)
        supportActionBar?.apply {
            title = selectedArtist.name
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back) // Replace with your back button icon
            titleColor = getColor(R.color.white)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}