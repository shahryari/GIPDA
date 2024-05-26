package com.example.warehousemanagment.model.models.report_inventory.serial_inventory_product


import com.google.gson.annotations.SerializedName

data class SerialInvProductRows(
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductID")
    val productID: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("AvailableInventory")
    val availableInventory:Int,
    @SerializedName("RealInventory")
    val realInventory:Int,
)