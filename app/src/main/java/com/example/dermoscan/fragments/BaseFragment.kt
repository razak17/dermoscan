package com.example.dermoscan.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.adapters.BlogAdapter
import com.example.dermoscan.adapters.ScanAdapter
import com.example.dermoscan.databinding.FragmentBaseBinding
import com.example.dermoscan.dummyScans
import com.example.dermoscan.models.BlogModel
import com.example.dermoscan.utils.*
import java.io.IOException

class BaseFragment : Fragment() {
    private var _binding: FragmentBaseBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var blogAdapter: BlogAdapter
    private lateinit var scanAdapter: ScanAdapter

    private lateinit var blogImageId: Array<Int>
    private lateinit var blogHeading: Array<String>
    private lateinit var blogArticle: Array<String>

    private val mInputSize = 224
    private val mSamplePath = "placeholder2.png"

    private lateinit var mBitmap: Bitmap
    private lateinit var handleGalleryLaunch: ActivityResultLauncher<Intent>
    private lateinit var handleCameraLaunch: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        val view = binding.root

        val assets = resources.assets
        assets.open(mSamplePath).use {
            this.mBitmap = BitmapFactory.decodeStream(it)
            this.mBitmap =
                Bitmap.createScaledBitmap(this.mBitmap, mInputSize, mInputSize, true)
        }

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
                    BaseFragmentDirections.navigateToBlogDetailsFragment(
                        blogTitle,
                        blogContent,
                        blogImage
                    )
                findNavController().navigate(action)
            }
        })

        scanAdapter.setOnScanClickListener(object : ScanAdapter.OnScanClickListener {
            override fun onScanClick(position: Int) {
                activity?.let { showToast(it, "Scan Item $position clicked!!!") }
            }
        })

        handleCameraLaunch =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    if (data != null) {
                        mBitmap = data.extras!!.get("data") as Bitmap
                        mBitmap = scaleImage(mBitmap, mInputSize)
                        toastAndNavigateToDetect(mBitmap)
                    }
                } else {
                    activity?.let { showToast(requireActivity(), "Unrecognized request code") }
                }
            }

        handleGalleryLaunch =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    // Handle Image from gallery
                    if (data != null) {
                        val imageUri = data.data
                        val bitmap = activity?.let { uriToBitmap(it, imageUri!!) }
                        try {
                            if (bitmap != null) {
                                mBitmap = bitmap
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        println("Success!!!")
                        mBitmap = scaleImage(mBitmap, mInputSize)
                        toastAndNavigateToDetect(mBitmap)
                    }
                } else {
                    activity?.let { showToast(requireActivity(), "Unrecognized request code") }
                }
            }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnScanImage.setOnClickListener {
            openDialog()
        }

        binding.tvAllScans.setOnClickListener {
            val action = BaseFragmentDirections.navigateToScanHistoryFragment()
            findNavController().navigate(action)
        }

        binding.fabCamera.setOnClickListener {
            openDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toastAndNavigateToDetect(mBitmap: Bitmap) {
        activity?.let {
            showToast(
                it,
                "Image crop to: w = ${mBitmap.width} h = ${mBitmap.height}"
            )
        }
        val action = BaseFragmentDirections.navigateToDetectFragment(mBitmap)
        findNavController().navigate(action)
    }

    private fun openDialog() {
        val dialogView = View.inflate(activity, R.layout.scan_dialog, null)
        activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setView(dialogView)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


            dialog.findViewById<Button>(R.id.btnCamera)?.setOnClickListener {
                dispatchLaunchCameraIntent(handleCameraLaunch)
                dialog.cancel()
            }

            dialog.findViewById<Button>(R.id.btnGallery)?.setOnClickListener {
                dispatchLaunchGalleryIntent(handleGalleryLaunch)
                dialog.cancel()
            }
        } ?: throw IllegalStateException("Activity cannot be null")
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