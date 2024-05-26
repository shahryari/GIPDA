package com.example.warehousemanagment.model.models.transfer_task


import com.google.gson.annotations.SerializedName

data class DestinyLocationTransfer(
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("LocationID")
    val locationID: String
)