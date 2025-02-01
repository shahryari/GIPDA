package com.example.warehousemanagment.model.models.report_inventory.report_location


import com.google.gson.annotations.SerializedName

data class LocationInventoryRows(
    @SerializedName("AvailableInventory")
    val availableInventory: Int,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
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
    @SerializedName("InvTypeTitle")
    val invTypeTitle:String,
    @SerializedName("LocationProductID")
    val locationProductID: String,
    @SerializedName("UomName")
    val uomName:String

)