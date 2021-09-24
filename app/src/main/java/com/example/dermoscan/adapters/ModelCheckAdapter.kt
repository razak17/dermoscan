package com.example.dermoscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.ModelCheckModel

class ModelCheckAdapter(
    private val modelCheckList: MutableList<ModelCheckModel>,
) : RecyclerView.Adapter<ModelCheckAdapter.ViewHolder>() {

    private lateinit var modelCheckClickListener: OnModelCheckClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.model_check_item, parent, false)

        return ViewHolder(view, modelCheckClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelCheckModel = modelCheckList[position]

        // sets the model name to modelText
        holder.modelCheckText.text = modelCheckModel.name
    }

    override fun getItemCount(): Int = modelCheckList.size // return list of items

    class ViewHolder(ItemView: View, listener: OnModelCheckClickListener) : RecyclerView.ViewHolder(ItemView) {
        val modelCheckText: TextView = itemView.findViewById(R.id.tvModelCheckName)

        init {
            itemView.setOnClickListener {
                val adapter = adapterPosition
                if (adapter != RecyclerView.NO_POSITION) {
                    listener.onModelCheckClick(adapterPosition)
                }
            }
        }
    }

    fun addModelCheck(model: ModelCheckModel) {
        modelCheckList.add(model)
        notifyItemInserted(modelCheckList.size - 1)
    }

    interface OnModelCheckClickListener {
        fun onModelCheckClick(position: Int)
    }

    fun setOnModelCheckClickListener(listener: OnModelCheckClickListener) {
        modelCheckClickListener = listener
    }
}