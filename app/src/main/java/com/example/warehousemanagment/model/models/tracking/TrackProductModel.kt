package com.example.warehousemanagment.model.models.tracking


import com.google.gson.annotations.SerializedName

data class TrackProductModel(
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductID")
    val productID: String,
    @SerializedName("ProductTitle")
    val productTitle: String
)