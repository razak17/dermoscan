package com.example.dermoscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermoscan.adapters.SettingsAdapter
import com.example.dermoscan.databinding.FragmentSettingsBinding
import com.example.dermoscan.models.SettingModel
import com.example.dermoscan.utils.showToast

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsAdapter: SettingsAdapter

    private lateinit var titles: Array<String>
    private lateinit var subtitles: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        settingsAdapter = SettingsAdapter(ArrayList())
        loadSettings()

        binding.rvSettings.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = settingsAdapter
        }

        settingsAdapter.setOnSettingClickListener(object : SettingsAdapter.OnSettingClickListener {
            override fun onSettingClick(position: Int) {
                if (position == 3) {
                    val action = SettingsFragmentDirections.navigateToScanAboutFragment()
                    findNavController().navigate(action)
                } else {
                    activity?.let { showToast(it, "Setting Item $position") }
                }
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadSettings() {
        titles = arrayOf(
            "FAQs",
            "Disclaimer",
            "Terms and Conditions",
            "About",
            )

        subtitles = arrayOf(
            "A few questions you might have have been answered here",
            "A few things to know while you use this app",
            "Terms for using the app",
            "About this app",
            )

        for (i in titles.indices) {
            val setting = SettingModel(titles[i], subtitles[i])
            settingsAdapter.addSetting(setting)
        }
    }
}