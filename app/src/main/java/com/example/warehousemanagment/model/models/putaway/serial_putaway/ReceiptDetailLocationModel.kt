package com.example.warehousemanagment.model.models.putaway.serial_putaway


import com.google.gson.annotations.SerializedName

data class ReceiptDetailLocationModel(
    @SerializedName("rows")
    val rows: List<ReceiptDetailLocationRow>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("MasterData")
    val masterData: MySerialReceiptDetailRow?
)

data class ReceiptDetailLocationRow(
    @SerializedName("ItemLocationID")
    val itemLocationID: String,
    @SerializedName("CreatedOnString")
    val createdOnString: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("SerialCount")
    val serialCount: Int
)