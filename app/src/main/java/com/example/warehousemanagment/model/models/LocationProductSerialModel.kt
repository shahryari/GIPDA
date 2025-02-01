package com.example.warehousemanagment.model.models

import com.google.gson.annotations.SerializedName


data class LocationProductSerialModel(
    @SerializedName("rows")
    val rows: List<LocationProductSerialRow>?,
    @SerializedName("total")
    val total: Int
)

data class LocationProductSerialRow(
    @SerializedName("SerialID")
    val serialID: String,
    @SerializedName("SerialNumber")
    val serialNumber: String,
    @SerializedName("IsScanned")
    val isScanned: Boolean? = false,
)
