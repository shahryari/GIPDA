package com.example.warehousemanagment.model.models.shipping.customer


import com.google.gson.annotations.SerializedName

data class CustomerModel(
    @SerializedName("CustomerCode")
    val customerCode: String,
    @SerializedName("CustomerFullName")
    val customerFullName: String,
    @SerializedName("CustomerID")
    val customerID: String
)