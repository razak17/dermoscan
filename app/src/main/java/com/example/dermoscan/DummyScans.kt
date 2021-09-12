package com.example.dermoscan

import com.example.dermoscan.adapters.ScanAdapter
import com.example.dermoscan.models.ScanModel

private lateinit var scanImageId: Array<Int>
private lateinit var scanResultRCNN: Array<String>
private lateinit var scanResultResNet: Array<String>

fun dummyScans(scanAdapter: ScanAdapter, showAll: Boolean) {
    scanImageId = arrayOf(
        R.drawable.benign01,
        R.drawable.benign02,
        R.drawable.malignant01,
        R.drawable.malignant02,
    )
    scanResultRCNN = arrayOf(
        "99%",
        "89%",
        "90%",
        "95%",
    )
    scanResultResNet = arrayOf(
        "89%",
        "99%",
        "95%",
        "90%",
    )

    var range = scanImageId.indices

    if (!showAll) {
        range = 1..3
    }

    for (i in range) {
        val scan = ScanModel(scanImageId[i], scanResultRCNN[i], scanResultResNet[i])
        scanAdapter.addScan(scan)
    }
}