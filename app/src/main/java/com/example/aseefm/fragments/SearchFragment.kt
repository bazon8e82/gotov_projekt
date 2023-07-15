package com.example.aseefm.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.aseefm.LoginActivity
import com.example.aseefm.R
import com.example.aseefm.SavedPreference
import com.example.aseefm.databinding.FragmentSearchBinding
import com.example.aseefm.databinding.FragmentTopArtistsBinding
import com.example.aseefm.helpers.showProfileCustomDialog
import com.example.aseefm.viewmodels.ArtistsViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SearchFragment: Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupUIBasedOnLogin()
        return binding.root
    }

    private fun setupUIBasedOnLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        if (SavedPreference.getEmail(requireContext()) == "") {
            binding.profileDetails.visibility = View.GONE
        } else {
            binding.navView.menu.findItem(R.id.logout).isVisible = true
            binding.navView.menu.findItem(R.id.profile).isVisible = true
            binding.navView.menu.findItem(R.id.login).isVisible = false
            setupUserProfile()
        }

        binding.userProfilePic.setOnClickListener {
            showProfileCustomDialog(requireContext())
            setupUserProfile()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupUserProfile() {
        binding.userProfilePic.load(SavedPreference.getPictureUrl(requireContext())) {
            crossfade(true)
            placeholder(R.drawable.ic_person)
            transformations(CircleCropTransformation())
        }

        if (SavedPreference.getUsername(requireContext()) == "") {
            binding.userProfileName.text = SavedPreference.getGivenName(requireContext()) +
                    " " + SavedPreference.getFamilyName(requireContext())
        } else {
            binding.userProfileName.text = SavedPreference.getUsername(requireContext())
        }
    }


    private fun dashboardOnClicks() {

        binding.navView.bringToFront()
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> Toast.makeText(
                    context,
                    getString(R.string.not_implemented), Toast.LENGTH_SHORT).show()
                    /* Navigation.findNavController(binding.root)
                    .navigate(R.id.action_exploreFragment_to_settingsFragment) */
                R.id.about -> Toast.makeText(
                    context,
                    getString(R.string.not_implemented), Toast.LENGTH_SHORT).show()
                            /* Navigation.findNavController(binding.root)
                    .navigate(R.id.action_exploreFragment_to_aboutActivity) */
                R.id.rate -> Toast.makeText(
                    context,
                    getString(R.string.not_implemented), Toast.LENGTH_SHORT
                ).show()
                R.id.logout -> signOut()
                R.id.login -> {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
                R.id.profile -> showProfileCustomDialog(requireContext())
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.ibSettings.setOnClickListener {
            Toast.makeText(
                context,
                getString(R.string.not_implemented), Toast.LENGTH_SHORT).show()
            /*
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_exploreFragment_to_settingsFragment) */
        }

        binding.news.setOnClickListener {
            Toast.makeText(
                context,
                getString(R.string.not_implemented), Toast.LENGTH_SHORT).show()
            /*
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_exploreFragment_to_settingsFragment) */
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            SavedPreference.clearPreferences(requireContext())
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}