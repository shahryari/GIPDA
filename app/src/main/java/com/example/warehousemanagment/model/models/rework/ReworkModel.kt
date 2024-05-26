package com.example.warehousemanagment.model.models.rework


import com.google.gson.annotations.SerializedName

data class ReworkModel(
    @SerializedName("GTINCode")
    val gTINCode: String,
    @SerializedName("InternalCode")
    val internalCode: String,
    @SerializedName("ReworkSerialOwnerID")
    val reworkSerialOwnerID: Int,
    @SerializedName("SerialNumber")
    val serialNumber: String,
    @SerializedName("TrackingNumber")
    val trackingNumber: String
)