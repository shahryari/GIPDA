package com.example.warehousemanagment.model.models.report_inventory.serial_inventory


import com.google.gson.annotations.SerializedName

data class SerialInventoryModel(
    @SerializedName("rows")
    val serialInventoryRows: List<SerialInventoryRows>,
    @SerializedName("total")
    val total: Int
)