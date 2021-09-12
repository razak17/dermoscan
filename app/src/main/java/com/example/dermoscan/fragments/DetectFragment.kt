package com.example.dermoscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dermoscan.databinding.FragmentDetectBinding

class DetectFragment : Fragment() {
    private var _binding: FragmentDetectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetectBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}