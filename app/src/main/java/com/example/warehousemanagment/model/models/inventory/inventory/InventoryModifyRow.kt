package com.example.warehousemanagment.model.models.inventory.inventory


import com.google.gson.annotations.SerializedName

data class InventoryModifyRow(
    @SerializedName("DestinationLcationCode")
    val destinationLcationCode: String,
    @SerializedName("GoodID")
    val goodID: String,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("IsCompleted")
    val isCompleted: Int,
    @SerializedName("LocationFromID")
    val locationFromID: String,
    @SerializedName("LocationToID")
    val locationToID: String,
    @SerializedName("LocationTransferID")
    val locationTransferID: String,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("SerialNumber")
    val serialNumber: String,
    @SerializedName("SourceLocationCode")
    val sourceLocationCode: String,
    @SerializedName("TransferTypeID")
    val transferTypeID: Int,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("WarehouseName")
    val warehouseName: String,

    @SerializedName("AvailableInventory")
    val availableInventory: Int,
    @SerializedName("RealInventory")
    val realInventory:Int,
    @SerializedName("UOMName")
    val uomName:String,
    @SerializedName("InvTypeTitle")
    val invTypeTitle:String,
    @SerializedName("FirstInvTypeTitle")
    val firstInvTypeTitle:String,
    @SerializedName("TaskTime")
    val taskTime:String,
    @SerializedName("TaskTimeString")
    val taskTimeString:String ,

)