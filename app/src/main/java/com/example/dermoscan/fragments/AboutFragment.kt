package com.example.dermoscan.fragments

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dermoscan.databinding.FragmentAboutBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnGetImage.setOnClickListener {
            val progressDialog = ProgressDialog(activity)
            progressDialog.setMessage("Fetching image....")
            progressDialog.setCancelable(false)
            progressDialog.show()

            var imageName = binding.etImageId.text.toString();
            var imageStorageRef = FirebaseStorage.getInstance().reference.child("scans/$imageName.jpg")

            val localFile = File.createTempFile("tempImage", "jpg")
            imageStorageRef.getFile(localFile).addOnSuccessListener {
                if (progressDialog.isShowing)
                    progressDialog.dismiss()

                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                binding.imageView.setImageBitmap(bitmap)
            }.addOnFailureListener {
                if (progressDialog.isShowing)
                    progressDialog.dismiss()

                Toast.makeText(activity, "Failed to retrieve image.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}