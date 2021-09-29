package com.example.dermoscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.ScanResultsModel
import com.gouravkhunger.accolib.widget.Accordion

class ScanResultsAdapter(
    private val scanResultsList: MutableList<ScanResultsModel>
) : RecyclerView.Adapter<ScanResultsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.scan_result_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelCheckModel = scanResultsList[position]

        holder.accordionView.titleTv.text = modelCheckModel.modelName
    }

    override fun getItemCount(): Int = scanResultsList.size // return list of items

    class ViewHolder(ItemView: View) :
        RecyclerView.ViewHolder(ItemView) {
        val accordionView: Accordion = itemView.findViewById(R.id.accordionCard)
    }

    fun addScanResult(scanResult: ScanResultsModel) {
        scanResultsList.add(scanResult)
        notifyItemInserted(scanResultsList.size - 1)
    }
}