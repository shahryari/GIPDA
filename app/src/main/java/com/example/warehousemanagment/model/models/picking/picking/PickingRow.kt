package com.example.warehousemanagment.model.models.picking.picking


import com.google.gson.annotations.SerializedName

data class PickingRow(
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("Locations")
    val locations: String,
    @SerializedName("SumQuantity")
    val sumQuantity: Int
)