package com.example.aseefm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.aseefm.databinding.ActivityMainBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Add rounded corners to bottom navigation
        val bottom1 = binding.mainAppBar.background as MaterialShapeDrawable
        bottom1.shapeAppearanceModel =
            bottom1.shapeAppearanceModel.toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 100f)
                .setTopRightCorner(CornerFamily.ROUNDED, 100f).build()

        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val host = binding.fragmentContainerView.getFragment() as NavHostFragment
        navController = host.navController
        binding.mainNavView.setupWithNavController(navController)
    }
}