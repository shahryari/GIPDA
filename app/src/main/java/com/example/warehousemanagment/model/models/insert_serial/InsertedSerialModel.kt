package com.example.warehousemanagment.model.models.insert_serial


import com.google.gson.annotations.SerializedName

data class InsertedSerialModel(
    @SerializedName("ProductID")
    val productId: String,
    @SerializedName("SerialNumber")
    val serialNumber: String,
    @SerializedName("WarehouseID")
    val warehouseId: String,
    @SerializedName("OwnerInfoID")
    val ownerInfoId: String,
    @SerializedName("InvTypeID")
    val invTypeId:Int
)