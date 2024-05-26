package com.example.warehousemanagment.model.models.insert_serial


import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("ProductID")
    val productId:String,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductTitle")
    val productTitle: String
)