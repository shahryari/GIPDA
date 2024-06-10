package com.example.warehousemanagment.model.models.shipping.customer

import com.google.gson.annotations.SerializedName

data class ColorModel(
    @SerializedName("CustomerColorID")
    val customerColorId: Int,
    @SerializedName("CustomerColorCode")
    val customerColorCode: String,
    @SerializedName("CustomerColorName")
    val customerColorName: String
)