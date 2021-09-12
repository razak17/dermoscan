package com.example.dermoscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermoscan.adapters.ScanAdapter
import com.example.dermoscan.databinding.FragmentScanHistoryBinding
import com.example.dermoscan.dummyScans

class ScanHistoryFragment : Fragment() {
    private var _binding: FragmentScanHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var scanAdapter: ScanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        scanAdapter = ScanAdapter(mutableListOf())
        setDummyScanData()

        binding.rvAllScans.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = scanAdapter
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setDummyScanData() {
        dummyScans(scanAdapter, true)

        scanAdapter.setOnScanClickListener(object : ScanAdapter.OnScanClickListener {
            override fun onScanClick(position: Int) {
                Toast.makeText(activity, "Scan Item $position clicked!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}