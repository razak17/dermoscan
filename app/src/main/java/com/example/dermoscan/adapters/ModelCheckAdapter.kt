package com.example.dermoscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.ModelCheckModel

class ModelCheckAdapter(
    private val modelsList: MutableList<ModelCheckModel>,
) : RecyclerView.Adapter<ModelCheckAdapter.ViewHolder>() {

    var checkedModels: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.model_check_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = modelsList[position]

        holder.modelCheckText.text = model.name
        holder.modelCheck.isChecked = model.isChecked

        holder.modelCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                model.isChecked = true
                checkedModels.add(model.name)
            } else if (!isChecked) {
                model.isChecked = false
                checkedModels.remove(model.name)
            }
        }
    }

    override fun getItemCount(): Int = modelsList.size // return list of items

    class ViewHolder(ItemView: View) :
        RecyclerView.ViewHolder(ItemView) {
        val modelCheckText: TextView = itemView.findViewById(R.id.tvModelCheckName)
        val modelCheck: CheckBox = itemView.findViewById(R.id.cbModelCheck)
    }
}