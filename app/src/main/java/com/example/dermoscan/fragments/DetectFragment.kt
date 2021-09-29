package com.example.dermoscan.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.Classifier
import com.example.dermoscan.adapters.ModelCheckAdapter
import com.example.dermoscan.databinding.FragmentDetectBinding
import com.example.dermoscan.models.ModelCheckModel
import com.example.dermoscan.utils.showToast

class DetectFragment : Fragment() {
    private var _binding: FragmentDetectBinding? = null
    private val binding get() = _binding!!

    private val args: DetectFragmentArgs by navArgs()

    private lateinit var rcnn: Classifier
    private lateinit var resnet50: Classifier
    private lateinit var inception: Classifier
    private lateinit var xception: Classifier
    private lateinit var vgg16: Classifier
    private lateinit var mobileNet: Classifier

    private val mRCNNModelPath = "model.tflite"
    private val mResnet50ModelPath = "resnet50_model.tflite"

    private val mInputSize = 224
    private val mLabelPath = "labels.txt"

    private lateinit var targetImage: Bitmap

    private lateinit var models: MutableList<String>
    private lateinit var predictions: MutableList<String>
    private lateinit var confidence: MutableList<String>


    private lateinit var modelCheckAdapter: ModelCheckAdapter

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetectBinding.inflate(inflater, container, false)
        val view = binding.root
        val assets = resources.assets

        rcnn = Classifier(assets, mRCNNModelPath, mLabelPath, mInputSize)
        resnet50 = Classifier(assets, mResnet50ModelPath, mLabelPath, mInputSize)
        inception = Classifier(assets, mResnet50ModelPath, mLabelPath, mInputSize)
        xception = Classifier(assets, mResnet50ModelPath, mLabelPath, mInputSize)
        vgg16 = Classifier(assets, mResnet50ModelPath, mLabelPath, mInputSize)
        mobileNet = Classifier(assets, mResnet50ModelPath, mLabelPath, mInputSize)


        targetImage = args.scanImage

        binding.ivLesionImage.setImageBitmap(targetImage)

        val modelList = mutableListOf(
            ModelCheckModel("rcnn"),
            ModelCheckModel("resnet50"),
            ModelCheckModel("inception"),
            ModelCheckModel("xception"),
            ModelCheckModel("vgg16"),
            ModelCheckModel("mobileNet"),
        )

        modelCheckAdapter = ModelCheckAdapter(modelList, requireContext())

        binding.rvModelsCheck.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = modelCheckAdapter
        }

        models = modelCheckAdapter.checkedModels
        predictions = mutableListOf()
        confidence = mutableListOf()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDiagnose.setOnClickListener {
            if (models.size > 0) {
                diagnoseLesion()

                val action =
                    DetectFragmentDirections.navigateToScanResultsFragment(
                        models.toTypedArray(),
                        predictions.toTypedArray(),
                        confidence.toTypedArray(),
                        )
                findNavController().navigate(action)

            } else {
                showToast(requireActivity(), "Please Check An Item First")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // pass image to the model and return the results
    private fun doInference(classifier: Classifier, scanImage: Bitmap) {
        val results = classifier.recognizeImage(scanImage).firstOrNull()

        results?.title?.let { predictions.add(it) }
        results?.confidence?.let { confidence.add(it.times(100).toInt().toString() + "%") }
    }

    @SuppressLint("SetTextI18n")
    fun diagnoseLesion() {

        for (i in models.indices) {
            if (models[i] == "rcnn") {
                doInference(rcnn, targetImage)
            }
            if (models[i] == "resnet50") {
                doInference(rcnn, targetImage)
            }
            if (models[i] == "inception") {
                doInference(rcnn, targetImage)
            }
            if (models[i] == "xception") {
                doInference(rcnn, targetImage)
            }
            if (models[i] == "vgg16") {
                doInference(rcnn, targetImage)
            }
            if (models[i] == "mobileNet") {
                doInference(rcnn, targetImage)
            }
        }
    }
}