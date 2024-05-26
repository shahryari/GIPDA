package com.example.warehousemanagment.model.models.receive.receiveDetail


import com.google.gson.annotations.SerializedName

data class ReceiveDetailModel(
    @SerializedName("rows")
    val receiveDetailRows: List<ReceiveDetailRow>,
    @SerializedName("total")
    val total: Int
)