package com.example.aseefm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aseefm.R
import com.example.aseefm.adapters.paging.TrackPagingAdapter
import com.example.aseefm.databinding.FragmentTopTracksBinding
import com.example.aseefm.helpers.isOnline
import com.example.aseefm.helpers.showCustomDialog
import com.example.aseefm.networking.model.Track
import com.example.aseefm.networking.paging.TrackDiff
import com.example.aseefm.viewmodels.TrackViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class TopTracksFragment : Fragment() {

    private val viewModel: TrackViewModel by activityViewModels()
    private lateinit var binding: FragmentTopTracksBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopTracksBinding.inflate(inflater, container, false)
        binding.rvTracks.layoutManager = LinearLayoutManager(requireContext())

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
                    0 -> getTracksList(0)
                    1 -> getTracksList(1)
                    2 -> getTracksList(2)
                    3 -> getTracksList(3)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private var insertFavoriteLine = fun(it: Track) { viewModel.insertFavoriteTrack(it) }
    private var deleteFavoriteLine = fun(it: Track) { viewModel.deleteFavoriteTrack(it) }

    fun getTracksList(selectedFilter: Int) {
        viewModel.getFavoriteTracks()
        val favoritePlayersList = viewModel.favoriteTracks

        binding.tvAll.text = getString(R.string.top_tracks)
        val pagingAdapter =
            TrackPagingAdapter(requireContext(), favoritePlayersList.value, TrackDiff, {
                viewModel.insertFavoriteTrack(it)
            }, {
                viewModel.deleteFavoriteTrack(it)
            })
        binding.rvTracks.adapter = pagingAdapter

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
