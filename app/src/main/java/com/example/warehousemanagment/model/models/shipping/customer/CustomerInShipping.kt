package com.example.warehousemanagment.model.models.shipping.customer

import com.google.gson.annotations.SerializedName

data class CustomerInShipping(
    @SerializedName("ShippingAddressID")
    val shippingAddressId: String,
    @SerializedName("CustomerName")
    val customerName: String,
    @SerializedName("CustomerColorID")
    val customerColorId: Int?,
    @SerializedName("CustomerColorCode")
    val customerColorCode: String?,
    @SerializedName("CustomerColorName")
    val customerColorName: String?
)