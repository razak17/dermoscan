package com.example.dermoscan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.dermoscan.databinding.FragmentInfoDetailsBinding

class InfoDetailsFragment : Fragment() {
    private var _binding: FragmentInfoDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: InfoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvInfoDetailsTitle.text = args.infoTitle
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}