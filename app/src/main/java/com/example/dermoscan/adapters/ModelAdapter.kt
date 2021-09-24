package com.example.dermoscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.ModelModel

class ModelAdapter(
    private val modelList: MutableList<ModelModel>,
) : RecyclerView.Adapter<ModelAdapter.ViewHolder>() {

    private lateinit var modelClickListener: OnModelClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.model_item, parent, false)

        return ViewHolder(view, modelClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scanModel = modelList[position]

        // sets the model name to modelText
        holder.modelText.text = scanModel.name
    }

    override fun getItemCount(): Int = modelList.size // return list of items
    
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, listener: OnModelClickListener) : RecyclerView.ViewHolder(ItemView) {
        val modelText: TextView = itemView.findViewById(R.id.tvModelName)


        init {
            itemView.setOnClickListener {
                val adapter = adapterPosition
                if (adapter != RecyclerView.NO_POSITION) {
                    listener.onModelClick(adapterPosition)
                }
            }
        }
    }


    fun addModel(model: ModelModel) {
        modelList.add(model)
        notifyItemInserted(modelList.size - 1)
    }

    interface OnModelClickListener {
        fun onModelClick(position: Int)
    }

    fun setOnModelClickListener(listener: OnModelClickListener) {
        modelClickListener = listener
    }
}