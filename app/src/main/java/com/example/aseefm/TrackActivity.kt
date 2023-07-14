package com.example.aseefm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.aseefm.adapters.ImageSliderAdapter
import com.example.aseefm.adapters.TrackRecyclerAdapter
import com.example.aseefm.adapters.paging.EXTRA_ARTIST
import com.example.aseefm.adapters.paging.EXTRA_TRACK
import com.example.aseefm.databinding.ActivityArtistBinding
import com.example.aseefm.helpers.isOnline
import com.example.aseefm.helpers.showCustomDialog
import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.model.ArtistImage
import com.example.aseefm.networking.model.Track
import com.example.aseefm.viewmodels.ArtistDetailsViewModel
import com.example.aseefm.views.AddPhotoBottomSheet
import com.example.aseefm.views.EditPhotosBottomSheet
import com.google.android.material.snackbar.Snackbar

class TrackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtistBinding
    private lateinit var izabrana: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        izabrana= intent.getSerializableExtra(EXTRA_TRACK) as Track
        // TODO
        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.playerToolbar)
        supportActionBar?.apply {
            title = izabrana.name
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