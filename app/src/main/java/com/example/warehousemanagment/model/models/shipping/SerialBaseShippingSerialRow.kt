package com.example.warehousemanagment.model.models.shipping

import com.google.gson.annotations.SerializedName


data class SerialBaseShippingSerialRow(
    @SerializedName("IsScanInShip")
    val isScanInShip: Boolean,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("SerialNumber")
    val serialNumber: String,
    @SerializedName("ShippingAddressDetailID")
    val shippingAddressDetailID: String
)