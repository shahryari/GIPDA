package com.example.warehousemanagment.model.models.report_inventory.report_location


import com.google.gson.annotations.SerializedName

data class ReportLocationInventory(

    @SerializedName("rows")
    val rows: List<LocationInventoryRows>,
    @SerializedName("total")
    val total: Int
)