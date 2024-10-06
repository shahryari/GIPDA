package com.example.warehousemanagment.model.models.putaway.serial_putaway


import com.google.gson.annotations.SerializedName

data class SerialReceiptOnPutawayModel(
    @SerializedName("rows")
    val rows: List<SerialReceiptOnPutawayRow>,
    @SerializedName("total")
    val total: Int
)