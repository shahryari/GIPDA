package com.example.warehousemanagment.model.models.transfer_task.source_location


import com.google.gson.annotations.SerializedName

data class SourceLocationTransferModel(
    @SerializedName("rows")
    val rows: List<SourceLocationRow>,
    @SerializedName("total")
    val total: Int
)