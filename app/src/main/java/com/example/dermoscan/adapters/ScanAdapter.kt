package com.example.dermoscan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.ScanModel

class ScanAdapter(
    private val scanList: MutableList<ScanModel>,
) : RecyclerView.Adapter<ScanAdapter.ViewHolder>() {

    private lateinit var scanClickListener: OnScanClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.scan_item, parent, false)

        return ViewHolder(view, scanClickListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scanModel = scanList[position]

        // sets the image to the imageview from our itemHolder class
        holder.scanImage.setImageResource(scanModel.image)


        // sets the result to the RCNN result textview from our itemHolder class
        holder.resultRCNN.text = "${holder.resultRCNN.text}         ${scanModel.diagnosisRCNN}"

        // sets the result to the ResNet result textview from our itemHolder class
        holder.resultResNet.text = "${holder.resultResNet.text}  ${scanModel.diagnosisResnet50}"
    }

    override fun getItemCount(): Int = scanList.size // return list of items

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, listener: OnScanClickListener) : RecyclerView.ViewHolder(ItemView) {
        val scanImage: ImageView = itemView.findViewById(R.id.ivScanImage)
        val resultRCNN: TextView = itemView.findViewById(R.id.tvScanResultRCNN)
        val resultResNet: TextView = itemView.findViewById(R.id.tvScanResultResNet)

        init {
            itemView.setOnClickListener {
                val adapter = adapterPosition
                if (adapter != RecyclerView.NO_POSITION) {
                    listener.onScanClick(adapterPosition)
                }
            }
        }
    }

    fun addScan(scan: ScanModel) {
        scanList.add(scan)
        notifyItemInserted(scanList.size - 1)
    }

    interface OnScanClickListener {
        fun onScanClick(position: Int)
    }

    fun setOnScanClickListener(listener: OnScanClickListener) {
        scanClickListener = listener
    }
}