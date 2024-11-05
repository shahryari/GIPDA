package com.example.warehousemanagment.model.models.serial_transfer

import com.google.gson.annotations.SerializedName

data class SerialTransferProductModel(
    @SerializedName("rows")
    val rows: List<SerialTransferProductRow>,
    @SerializedName("total")
    val total: Int
)

data class SerialTransferProductRow(
    @SerializedName("AvailableInventory")
    val availableInventory: Int,
    @SerializedName("LocationID")
    val locationID: String,
    @SerializedName("WarehouseID")
    val warehouseID: String,
    @SerializedName("GoodID")
    val goodID: String,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("LocationProductID")
    val locationProductID: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductName")
    val productName: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("WarehouseName")
    val warehouseName: String,
    @SerializedName("RealInventory")
    val realInventory: Int
)


