package com.example.warehousemanagment.model.models.shipping


import com.google.gson.annotations.SerializedName

data class ShippingSerialModel(
    @SerializedName("SerialNumber")
    val serialNumber: String,
    @SerializedName("ShippingAddressDetailID")
    val shippingAddressDetailID: String
)