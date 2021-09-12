package com.example.dermoscan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.dermoscan.databinding.FragmentBlogDetailsBinding

class BlogDetailsFragment : Fragment() {
    private var _binding: FragmentBlogDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: BlogDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvBlogDetailTitle.text = args.blogTitle
        binding.tvBlogArticle.text = args.blogArticle
        binding.ivBlogDetailImage.setImageResource(args.blogImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}