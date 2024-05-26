package com.example.warehousemanagment.model.models.transfer_task.location_transfer


import com.google.gson.annotations.SerializedName

data class LocationTransferTaskModel(
    @SerializedName("rows")
    val rows: List<LocationTransferRow>,
    @SerializedName("total")
    val total: Int
)