package com.example.warehousemanagment.model.models.shipping.shipping_truck


import com.google.gson.annotations.SerializedName

data class ShippingTruckModel(
    @SerializedName("rows")
    val shippingTruckRows: List<ShippingTruckRow>,
    @SerializedName("total")
    val total: Int
)