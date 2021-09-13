package com.example.dermoscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.InfoModel

class InfoAdapter(private var infoList: MutableList<InfoModel>) :
    RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    private lateinit var infoClickListener: OnInfoClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.info_card, parent, false)

        return InfoAdapter.ViewHolder(view, infoClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infoModel = infoList[position]

        holder.image.setImageResource(infoModel.image)
        holder.title.text = infoModel.title
    }

    override fun getItemCount(): Int = infoList.size // return list of items

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, listener: OnInfoClickListener) :
        RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.tvInfoTitle)
        val image: ImageView = itemView.findViewById(R.id.ivInfoImage)

        init {
            itemView.setOnClickListener {
                val adapter = adapterPosition
                if (adapter != RecyclerView.NO_POSITION) {
                    listener.onInfoClick(adapterPosition)
                }
            }
        }
    }

    fun addInfo(info: InfoModel) {
        infoList.add(info)
        notifyItemInserted(infoList.size - 1)
    }

    interface OnInfoClickListener {
        fun onInfoClick(position: Int)
    }

    fun setOnInfoClickListener(listener: OnInfoClickListener) {
        infoClickListener = listener
    }

}