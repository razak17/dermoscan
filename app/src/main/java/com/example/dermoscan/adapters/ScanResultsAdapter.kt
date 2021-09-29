package com.example.dermoscan.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dermoscan.R
import com.example.dermoscan.models.ScanResultsModel
import com.gouravkhunger.accolib.widget.Accordion

class ScanResultsAdapter(
    private val scanResultsList: MutableList<ScanResultsModel>,
    private val context: Context
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

    fun addScanResult(m: String) {
        var formattedString = ""

        if (m == "rcnn") {
            formattedString = context.getString(R.string.strRCNN)
        }
        if (m == "resnet50") {
            formattedString = context.getString(R.string.strResNet50)
        }
        if (m == "inception") {
            formattedString = context.getString(R.string.strInception)
        }
        if (m == "xception") {
            formattedString = context.getString(R.string.strXception)
        }
        if (m == "vgg16") {
            formattedString = context.getString(R.string.strVGG16)
        }
        if (m == "mobileNet") {
            formattedString = context.getString(R.string.strMobileNet)
        }

        val model = ScanResultsModel(formattedString)
        scanResultsList.add(model)
        notifyItemInserted(scanResultsList.size - 1)
    }
}