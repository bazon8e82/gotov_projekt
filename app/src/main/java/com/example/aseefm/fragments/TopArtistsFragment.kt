package com.example.aseefm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aseefm.R
import com.example.aseefm.adapters.paging.ArtistPagingAdapter
import com.example.aseefm.databinding.FragmentTopArtistsBinding
import com.example.aseefm.helpers.isOnline
import com.example.aseefm.helpers.showCustomDialog
import com.example.aseefm.networking.model.Artist
import com.example.aseefm.networking.paging.ArtistDiff
import com.example.aseefm.viewmodels.ArtistsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopArtistsFragment : Fragment() {

    private val viewModel: ArtistsViewModel by activityViewModels()
    private lateinit var binding: FragmentTopArtistsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopArtistsBinding.inflate(inflater, container, false)
        binding.rvArtists.layoutManager = LinearLayoutManager(requireContext())

        setupSpinner()

        return binding.root
    }

    private fun setupSpinner() {
        val directions = resources.getStringArray(R.array.SpinnerDirectionsItems)
        val spinnerAdapter = ArrayAdapter(
            requireContext(), R.layout.drop_down_toolbar_item, directions
        )

        binding.spinner.adapter = spinnerAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> getArtistsList(0)
                    1 -> getArtistsList(1)
                    2 -> getArtistsList(2)
                    3 -> getArtistsList(3)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    fun getArtistsList(selectedFilter: Int) {
        viewModel.getFavoriteArtists()
        val favoritePlayersList = viewModel.favoriteArtists

        binding.tvAll.text = getString(R.string.top_artists)
        val pagingAdapter =
            ArtistPagingAdapter(requireContext(), favoritePlayersList.value, ArtistDiff, {
                viewModel.insertFavoriteArtist(it)
            }, {
                viewModel.deleteFavoriteArtist(it)
            })
        binding.rvArtists.adapter = pagingAdapter

        pagingAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading) {
                binding.progressBar.visibility = ProgressBar.VISIBLE
            } else {
                binding.progressBar.visibility = ProgressBar.GONE
            }
        }

        if (requireContext().isOnline()) {
            lifecycleScope.launch {
                viewModel.flow.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        } else {
            showCustomDialog(getString(R.string.no_internet_connection), requireContext())
        }
    }
}