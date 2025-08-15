package com.example.warehousemanagment.model.models.putaway.serial_putaway


import com.google.gson.annotations.SerializedName

data class MySerailReceiptDetailModel(
    @SerializedName("rows")
    val rows: List<MySerialReceiptDetailRow>,
    @SerializedName("total")
    val total: Int
)

data class MySerialReceiptDetailRow(
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductName")
    val productName: String,
    @SerializedName("Quantity")
    val  quantity: Int,
    @SerializedName("ReceiptDetailID")
    val receiptDetailID: String,
    @SerializedName("ScanCount")
    val scanCount: Int,
    @SerializedName("HasItemLocation")
    val hasItemLocation: Boolean,
    @SerializedName("Serializable")
    val serializable: Boolean,
)