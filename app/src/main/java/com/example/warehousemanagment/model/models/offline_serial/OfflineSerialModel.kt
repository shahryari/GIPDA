package com.example.warehousemanagment.model.models.offline_serial


import com.google.gson.annotations.SerializedName

data class OfflineSerialModel(
    @SerializedName("serial")
    val serial: String,
//    @SerializedName("serialId")
//    val id: Int
)