package com.example.warehousemanagment.model.models.shipping.detail


import com.google.gson.annotations.SerializedName

data class ShippingDetailModel(
    @SerializedName("rows")
    val shippingDetailRows: List<ShippingDetailRow>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("remain")
    val remain:Int
)