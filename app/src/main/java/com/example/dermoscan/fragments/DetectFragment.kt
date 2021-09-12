package com.example.dermoscan.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dermoscan.Classifier
import com.example.dermoscan.R
import com.example.dermoscan.databinding.FragmentDetectBinding
import com.example.dermoscan.utils.*
import java.io.IOException

class DetectFragment : Fragment() {
    private var _binding: FragmentDetectBinding? = null
    private val binding get() = _binding!!

    private lateinit var mClassifier: Classifier
    private lateinit var mClassifierResnet50: Classifier
    private lateinit var mBitmap: Bitmap

    private val mInputSize = 224
    private val mModelPath = "model.tflite"
    private val mResNet50ModelPath = "resnet50_model.tflite"
    private val mLabelPath = "labels.txt"
    private val mSamplePath = "placeholder2.png"

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetectBinding.inflate(inflater, container, false)
        val view = binding.root
        val assets = resources.assets

        assets.open(mSamplePath).use {
            this.mBitmap = BitmapFactory.decodeStream(it)
            this.mBitmap =
                Bitmap.createScaledBitmap(this.mBitmap, mInputSize, mInputSize, true)
            view.findViewById<ImageView>(R.id.ivLesionImage).setImageBitmap(this.mBitmap)
        }

        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
        mClassifierResnet50 = Classifier(assets, mResNet50ModelPath, mLabelPath, mInputSize)

        binding.btnDiagnose.setOnClickListener { diagnoseLesion() }

        binding.btnLaunchCamera.setOnClickListener {
            dispatchLaunchCameraIntent(cameraLauncher)
        }

        binding.btnOpenGallery.setOnClickListener {
            dispatchLaunchGalleryIntent(galleryLauncher)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                if (data != null) {
                    this.mBitmap = data.extras!!.get("data") as Bitmap
                    this.mBitmap = scaleImage(this.mBitmap, mInputSize)
                    activity?.let {
                        showToast(
                            it,
                            "Image crop to: w = ${this.mBitmap.width} h = ${this.mBitmap.height}"
                        )
                    }
                    binding.ivLesionImage.setImageBitmap(this.mBitmap)
                }
            }
        }

    private var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                // Handle Image from gallery
                if (data != null) {
                    val imageUri = data.data
                    val bitmap = activity?.let { uriToBitmap(it, imageUri!!) }
                    try {
                        if (bitmap != null) {
                            mBitmap = bitmap
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    println("Success!!!")
                    mBitmap = scaleImage(mBitmap, mInputSize)
                    binding.ivLesionImage.setImageBitmap(mBitmap)
                }
            } else {
                activity?.let { showToast(it, "Unrecognized request code") }
            }
        }

    // pass image to the model and return the results
    private fun doInference(classifier: Classifier): String {
        val results = classifier.recognizeImage(this.mBitmap).firstOrNull()

        return results?.confidence?.times(100)?.toInt()
            .toString() + "% " + results?.title
    }

    @SuppressLint("SetTextI18n")
    fun diagnoseLesion() {
        val resultRCNN = doInference(mClassifier)
        val resultsResnet50 = doInference(mClassifierResnet50)

        binding.tvResultsFirst.text = "$resultRCNN according to RCNN model"
        binding.tvResultsSecond.text = "$resultsResnet50 according to ResNet50 model"
    }
}
