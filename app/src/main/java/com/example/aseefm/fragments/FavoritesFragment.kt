package com.example.aseefm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aseefm.R
import com.example.aseefm.adapters.FavoriteArtistRecyclerAdapter
import com.example.aseefm.adapters.FavoriteTrackRecyclerAdapter
import com.example.aseefm.databinding.FragmentFavoritesBinding
import com.example.aseefm.viewmodels.FavoritesViewModel


class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel: FavoritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        binding.rvFavoriteArtists.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavoriteTracks.layoutManager = LinearLayoutManager(requireContext())

        loadFavoriteArtists()
        loadFavoriteTracks()

        try {
            viewModel.getFavoriteArtistsAndImages()
            viewModel.getFavoriteTracks()
        } catch (e: Exception) {

        }

        return binding.root
    }

    private fun loadFavoriteArtists() {
        viewModel.favoriteArtists.observe(viewLifecycleOwner) { artistList ->
            viewModel.artistImages.observe(viewLifecycleOwner) { imagesList ->
                binding.progressBar.visibility = ProgressBar.VISIBLE

                val adapter = FavoriteArtistRecyclerAdapter(
                    requireContext(), artistList,
                    imagesList
                ) { artist ->
                    viewModel.deleteFavoriteArtist(artist)
                }
                binding.rvFavoriteArtists.adapter = adapter

                binding.progressBar.visibility = ProgressBar.GONE
            }
            setEmptyState(binding.artistsEmptyState, artistList.isNotEmpty())
        }
    }

    private fun loadFavoriteTracks() {
        viewModel.favoriteTracks.observe(viewLifecycleOwner) {
            setEmptyState(binding.tracksEmptyState, it.isNotEmpty())
            val adapter = FavoriteTrackRecyclerAdapter(
                requireContext(), it,
            ) { track ->
                viewModel.deleteFavoriteTrack(track)
            }
            binding.rvFavoriteTracks.adapter = adapter
        }
    }

    private fun setEmptyState(view: View, isNotEmpty: Boolean) {
        if (isNotEmpty) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }
}