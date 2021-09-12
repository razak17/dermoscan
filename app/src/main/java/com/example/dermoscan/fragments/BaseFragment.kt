package com.example.dermoscan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.*
import com.example.dermoscan.adapters.BlogAdapter
import com.example.dermoscan.adapters.ScanAdapter
import com.example.dermoscan.databinding.FragmentBaseBinding
import com.example.dermoscan.models.BlogModel

class BaseFragment : Fragment() {
    private var _binding: FragmentBaseBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var blogAdapter: BlogAdapter
    private lateinit var scanAdapter: ScanAdapter

    private lateinit var blogImageId: Array<Int>
    private lateinit var blogHeading: Array<String>
    private lateinit var blogArticle: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        val view = binding.root

        // Load dummy blogs
        blogAdapter = BlogAdapter(ArrayList())
        dummyBlogs()

        // Load dummy scans
        scanAdapter = ScanAdapter(mutableListOf())
        dummyScans(scanAdapter, false)

        binding.rvBlog.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = blogAdapter
        }

        binding.rvRecentScans.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = scanAdapter
        }

        blogAdapter.setOnBlogClickListener(object : BlogAdapter.OnBlogClickListener {
            override fun onBlogClick(position: Int) {
                val blogTitle = blogHeading[position]
                val blogContent = blogArticle[position]
                val blogImage = blogImageId[position]
                val action =
                    BaseFragmentDirections.navigateToBlogDetailsFragment(blogTitle, blogContent, blogImage)
                findNavController().navigate(action)
            }
        })

        scanAdapter.setOnScanClickListener(object : ScanAdapter.OnScanClickListener {
            override fun onScanClick(position: Int) {
                Toast.makeText(activity, "Scan Item $position clicked!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnScanImage.setOnClickListener {
            val action = BaseFragmentDirections.navigateToDetectFragment()
            findNavController().navigate(action)
        }

        binding.tvAllScans.setOnClickListener {
            val action = BaseFragmentDirections.navigateToScanHistoryFragment()
            findNavController().navigate(action)
        }

        binding.fabCamera.setOnClickListener {
            val action = BaseFragmentDirections.navigateToDetectFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dummyBlogs() {
        blogImageId = arrayOf(
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo2,
        )

        blogHeading = arrayOf(
            "First Article",
            "Second Article",
            "Third Article",
            "Fourth Article",
        )

        blogArticle = arrayOf(
            getString(R.string.blog_1),
            getString(R.string.blog_1),
            getString(R.string.blog_1),
            getString(R.string.blog_1),
        )

        for (i in blogImageId.indices) {
            val blog = BlogModel(blogHeading[i], blogArticle[i], blogImageId[i])
            blogAdapter.addBlog(blog)
        }
    }
}