package com.example.aseefm.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aseefm.R
import com.example.aseefm.adapters.ArtistPhotoRecyclerAdapter
import com.example.aseefm.databinding.BottomsheetEditPhotoBinding
import com.example.aseefm.networking.model.ArtistImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditPhotosBottomSheet(
    private val imageList: ArrayList<ArtistImage>,
    private val deleteCallback: ((ArtistImage) -> Unit)?,
    private val updateSliderCallback: ((ArrayList<ArtistImage>) -> Unit),
    private val deleteAllCallback: ((ArrayList<ArtistImage>) -> Unit),
    private val showErrorMessageCallback: ((String) -> Unit)
): BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetEditPhotoBinding
    private val adapter by lazy { ArtistPhotoRecyclerAdapter(requireContext(), arrayListOf()) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetEditPhotoBinding.inflate(inflater, container, false)

        binding.rvArtistPhotos.layoutManager = LinearLayoutManager(requireContext())

        adapter.setImageList(imageList)
        binding.rvArtistPhotos.adapter = adapter

        binding.btnEdit.isActivated = false

        binding.btnEdit.setOnClickListener {
            if (!binding.btnEdit.isActivated) {
                binding.btnEdit.isActivated = true
                adapter.editSwitch = true

                adapter.deleteCallback = deleteCallback
                adapter.updateSliderCallback = updateSliderCallback
                adapter.showErrorMessageCallback = showErrorMessageCallback

                adapter.notifyDataSetChanged()
            } else {
                binding.btnEdit.isActivated = false
                adapter.editSwitch = false
                adapter.notifyDataSetChanged()
            }
        }

        binding.btnDeleteAllPhotos.setOnClickListener {
            showCustomDialog("Delete all photos?")
        }


        return binding.root
    }

    private fun showCustomDialog(title: String) {
        val dialog = Dialog(requireContext()).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setCancelable(true)
            this.setContentView(R.layout.custom_dialog)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val tvTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)
        val btnOk = dialog.findViewById<Button>(R.id.btnDialogOk)
        val btnCancel = dialog.findViewById<Button>(R.id.btnDialogCancel)

        tvTitle.text = title
        btnOk.text = getString(R.string.clear)
        btnCancel.text = getString(R.string.cancel)

        btnOk.setOnClickListener {
            deleteAllCallback.invoke(imageList)
            adapter.clearImageList()
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}