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
        val p = args.predictions
        val c = args.confidence


        for (i in m.indices) {
            scanResultAdapter.addScanResult(m[i])
        }
    }
}