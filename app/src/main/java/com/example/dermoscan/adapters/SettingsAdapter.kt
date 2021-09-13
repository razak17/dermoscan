package com.example.dermoscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.SettingModel

class SettingsAdapter(
    private val settingsList: MutableList<SettingModel>
): RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    private lateinit var settingClickListener: OnSettingClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.settings_item, parent, false)

        return ViewHolder(view, settingClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val settingsModel = settingsList[position]

        holder.settingTitle.text = settingsModel.settingTitle
        holder.settingSubtitle.text = settingsModel.settingSubTitle
    }


    override fun getItemCount(): Int = settingsList.size // return list of items

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, listener: OnSettingClickListener) : RecyclerView.ViewHolder(ItemView) {
        val settingTitle: TextView = itemView.findViewById(R.id.tvSettingTitle)
        val settingSubtitle: TextView = itemView.findViewById(R.id.tvSettingSubtitle)

        init {
            itemView.setOnClickListener {
                val adapter = adapterPosition
                if (adapter != RecyclerView.NO_POSITION) {
                    listener.onSettingClick(adapterPosition)
                }
            }
        }
    }

    fun addSetting(setting: SettingModel) {
        settingsList.add(setting)
        notifyItemInserted(settingsList.size - 1)
    }

    interface OnSettingClickListener {
        fun onSettingClick(position: Int)
    }

    fun setOnSettingClickListener(listener: OnSettingClickListener) {
        settingClickListener = listener
    }
}