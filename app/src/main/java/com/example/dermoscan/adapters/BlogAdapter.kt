package com.example.dermoscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.BlogModel

class BlogAdapter(private val blogList: ArrayList<BlogModel>) :
    RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    private lateinit var blogClickListener: OnBlogClickListener

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.blog_card, parent, false)

        return ViewHolder(view, blogClickListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val blogModel = blogList[position]

        // sets the image to the imageview from our itemHolder class
        holder.blogImage.setImageResource(blogModel.image)

        // sets the title to the title textview from our itemHolder class
        holder.blogTitle.text = blogModel.title

        // sets the description to the description textview from our itemHolder class
        holder.blogDescription.text = blogModel.description
    }

    // return the number of the items in the list
    override fun getItemCount(): Int = blogList.size

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, listener: OnBlogClickListener) : RecyclerView.ViewHolder(ItemView) {
        val blogImage: ImageView = itemView.findViewById(R.id.ivBlogImage)
        val blogTitle: TextView = itemView.findViewById(R.id.tvBlogTitle)
        val blogDescription: TextView = itemView.findViewById(R.id.tvBlogDescription)

        init {
            itemView.setOnClickListener {
                listener.onBlogClick(adapterPosition)
            }
        }
    }

    // add new blog
    fun addBlog(blog: BlogModel) {
        blogList.add(blog)
        notifyItemInserted(blogList.size - 1)
    }

    interface OnBlogClickListener {
        fun onBlogClick(position: Int)
    }

    fun setOnBlogClickListener(listener: OnBlogClickListener) {
        blogClickListener = listener
    }

}