package com.example.warehousemanagment.model.models.report_inventory.serial_inventory_product


import com.google.gson.annotations.SerializedName

data class SerialInvProductModel(
    @SerializedName("rows")
    val serialInvProductRows: List<SerialInvProductRows>,
    @SerializedName("total")
    val total: Int
)