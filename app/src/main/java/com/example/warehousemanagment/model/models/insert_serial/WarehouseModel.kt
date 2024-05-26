package com.example.warehousemanagment.model.models.insert_serial


import com.google.gson.annotations.SerializedName

data class WarehouseModel(
    @SerializedName("WarehouseID")
    val warehouseID: String,
    @SerializedName("WarehouseName")
    val warehouseName: String,
    @SerializedName("WarehouseTypeID")
    val warehouseTypeID: Int,
    @SerializedName("WarehouseCode")
    val warehouseCode: String
)