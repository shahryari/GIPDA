package com.example.warehousemanagment.model.models


import com.google.gson.annotations.SerializedName
import com.google.zxing.BarcodeFormat

data class BarcodeModel(
    @SerializedName("barcodeValue")
    val barcodeValue: BarcodeFormat,
    @SerializedName("name")
    val name: String,
    @SerializedName("barcodeNumber")
    val barcodeNumber:Int
)