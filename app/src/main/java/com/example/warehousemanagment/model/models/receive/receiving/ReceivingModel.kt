package com.example.warehousemanagment.model.models.receive.receiving


import com.google.gson.annotations.SerializedName

data class ReceivingModel(
    @SerializedName("rows")
    val rows: List<RowReceivingModel>,
    @SerializedName("total")
    val total: Int
)