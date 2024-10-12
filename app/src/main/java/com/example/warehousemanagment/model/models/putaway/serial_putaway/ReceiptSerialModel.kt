package com.example.warehousemanagment.model.models.putaway.serial_putaway


import com.google.gson.annotations.SerializedName

data class ReceiptSerialModel(
    @SerializedName("rows")
    val rows: List<ReceiptSerialRow>,
    @SerializedName("total")
    val total: Int
)

data class ReceiptSerialRow(
    @SerializedName("ItemSerialID")
    val itemSerialID: String,
    @SerializedName("Serial")
    val serial: String,
    @SerializedName("CreatedOnString")
    val createdOnString: String
)