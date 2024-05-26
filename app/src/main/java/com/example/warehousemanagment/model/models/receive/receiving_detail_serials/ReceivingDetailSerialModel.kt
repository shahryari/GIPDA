package com.example.warehousemanagment.model.models.receive.receiving_detail_serials


import com.google.gson.annotations.SerializedName

data class ReceivingDetailSerialModel(
    @SerializedName("ByExcel")
    val isExcel: Boolean,
    @SerializedName("ItemSerialID")
    val itemSerialID: String,
    @SerializedName("ReceivingDetailID")
    val receivingDetailID: String,
    @SerializedName("SerialNumber")
    val serialNumber: String
)