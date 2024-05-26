package com.example.warehousemanagment.model.models.report_inventory.serial_inventory


import com.google.gson.annotations.SerializedName

data class SerialInventoryRows(
    @SerializedName("AvailableInventory")
    val availableInventory: Int,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("Owner")
    val owner: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("RealInventory")
    val realInventory: Int,
    @SerializedName("UomName")
    val uomName: String
)