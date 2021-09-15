package com.example.dermoscan.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.dermoscan.Classifier
import com.example.dermoscan.R
import com.example.dermoscan.databinding.FragmentDetectBinding

class DetectFragment : Fragment() {
    private var _binding: FragmentDetectBinding? = null
    private val binding get() = _binding!!

    private val args: DetectFragmentArgs by navArgs()

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

        binding.ivLesionImage.setImageBitmap(args.scanImage)

        binding.btnDiagnose.setOnClickListener {
            diagnoseLesion()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // pass image to the model and return the results
    private fun doInference(classifier: Classifier, scanImage: Bitmap): String {
        val results = classifier.recognizeImage(scanImage).firstOrNull()

        return results?.confidence?.times(100)?.toInt()
            .toString() + "% " + results?.title
    }

    @SuppressLint("SetTextI18n")
    fun diagnoseLesion() {
        val resultRCNN = doInference(mClassifier, args.scanImage)
        val resultsResnet50 = doInference(mClassifierResnet50, args.scanImage)

        binding.tvResultsFirst.text = "$resultRCNN according to RCNN model"
        binding.tvResultsSecond.text = "$resultsResnet50 according to ResNet50 model"
    }
}