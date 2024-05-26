package com.example.warehousemanagment.model.models.picking.picking


import com.google.gson.annotations.SerializedName

data class PickingTruckModel(
    @SerializedName("rows")
    val rows: List<PickingRow>,
    @SerializedName("total")
    val total: Int
)