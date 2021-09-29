package com.example.dermoscan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermoscan.R
import com.example.dermoscan.adapters.InfoAdapter
import com.example.dermoscan.databinding.FragmentInfoBinding
import com.example.dermoscan.models.InfoModel
import com.example.dermoscan.utils.showToast

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var infoAdapter: InfoAdapter

    private lateinit var infoTitles: Array<String>
    private lateinit var infoSubTitles: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        infoAdapter = InfoAdapter(ArrayList())
        loadInfo()

        binding.rvInfo.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = infoAdapter
        }

        infoAdapter.setOnInfoClickListener(object : InfoAdapter.OnInfoClickListener {
            override fun onInfoClick(position: Int) {
                val infoTitle = infoTitles[position]
                val infoSubTitle = infoSubTitles[position]
                val action = InfoFragmentDirections.navigateToInfoDetailsFragment(infoTitle, infoSubTitle)
                findNavController().navigate(action)

//                activity?.let { showToast(it, "Setting Item $position") }
            }

        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadInfo() {
        infoTitles = arrayOf(
            "What is skin cancer?",
            "Symptoms of Skin Cancer",
            "Causes of Skin cancer.",
            "Risk factors of Skin cancer.",
            "Skin cancer fighting foods.",
            "Skin cancer preventive measures."
        )

        infoSubTitles = arrayOf(
            "What is skin cancer?",
            "Symptoms of Skin Cancer",
            "Causes of Skin cancer.",
            "Risk factors of Skin cancer.",
            "Skin cancer fighting foods.",
            "Skin cancer preventive measures."
        )

        for (i in infoTitles.indices) {
            val info = InfoModel(infoTitles[i], infoSubTitles[i])
            infoAdapter.addInfo(info)
        }
    }
}