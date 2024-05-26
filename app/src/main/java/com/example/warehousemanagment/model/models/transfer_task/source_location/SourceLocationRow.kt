package com.example.warehousemanagment.model.models.transfer_task.source_location


import com.google.gson.annotations.SerializedName

data class SourceLocationRow(
    @SerializedName("AvailableInventory")
    val availableInventory: Int,
    @SerializedName("GoodID")
    val goodID: String,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("LocationID")
    val locationID: String,
    @SerializedName("LocationProductID")
    val locationProductID: String,
    @SerializedName("LocationTypeID")
    val locationTypeID: Int,
    @SerializedName("LocationTypeTitle")
    val locationTypeTitle: String,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("RealInventory")
    val realInventory: Int,
    @SerializedName("UOMName")
    val uOMName: String,
    @SerializedName("WarehouseID")
    val warehouseID: String
)