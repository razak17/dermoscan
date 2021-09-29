package com.example.dermoscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermoscan.adapters.ScanResultsAdapter
import com.example.dermoscan.databinding.FragmentScanResultsBinding

class ScanResultsFragment : Fragment() {
    private var _binding: FragmentScanResultsBinding? = null
    private val binding get() = _binding!!

    private lateinit var scanResultAdapter: ScanResultsAdapter

    private val args: ScanResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultsBinding.inflate(inflater, container, false)
        val view = binding.root

        scanResultAdapter = ScanResultsAdapter(mutableListOf(), requireContext())
        loadResults()

        binding.rvScanResults.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = scanResultAdapter
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadResults() {
        val m = args.selectedModels
        val c = args.confidence
        val p = args.predictions

        val confidenceRCNN = 99
        val confidenceResNet50 = 98
        val confidenceInception = 96
        val confidenceXception = 94
        val confidenceVGG16 = 92
        val confidenceMobileNet = 90

        var inferenceResult = ""

        for (i in p.indices) {
            if (p[i] == "Malignant") {
                inferenceResult = c[i] + " Concerning"
            }

            if (p[i] == "Benign") {
                inferenceResult = c[i] + " Not Concerning"
            }

            when {
                m[i] == "rcnn" -> {
                    scanResultAdapter.addScanResult(
                        m[i],
                        inferenceResult,
                        moreInfo(c[i], p[i], confidenceRCNN)
                    )
                }
                m[i] == "resnet50" -> {
                    scanResultAdapter.addScanResult(
                        m[i],
                        inferenceResult,
                        moreInfo(c[i], p[i], confidenceResNet50)
                    )
                }
                m[i] == "inception" -> {
                    scanResultAdapter.addScanResult(
                        m[i],
                        inferenceResult,
                        moreInfo(c[i], p[i], confidenceInception)
                    )
                }
                m[i] == "xception" -> {
                    scanResultAdapter.addScanResult(
                        m[i],
                        inferenceResult,
                        moreInfo(c[i], p[i], confidenceXception)
                    )
                }
                m[i] == "vgg16" -> {
                    scanResultAdapter.addScanResult(
                        m[i],
                        inferenceResult,
                        moreInfo(c[i], p[i], confidenceVGG16)
                    )
                }
                m[i] == "mobileNet" -> {
                    scanResultAdapter.addScanResult(
                        m[i],
                        inferenceResult,
                        moreInfo(c[i], p[i], confidenceMobileNet)
                    )
                }
            }
        }
    }

    private fun moreInfo(c: String, p: String, acc: Number): String {
        var rp = "According to this model, the image is $c ${p.lowercase()}."
        if (p == "Malignant") {
            rp =
                "$rp This means your mole could be cancerous and potentially dangerous. Make sure to see a doctor as soon as possible."
        }
        if (p == "Benign") {
            rp =
                "$rp This means your mole is non-cancerous and you potentially have nothing to worry about. Make sure to see a doctor if you are still unsure."
        }
        rp =
            "$rp This model achieve an accuracy of $acc during training. To learn more about this model checkout the our blog."

        return rp
    }
}