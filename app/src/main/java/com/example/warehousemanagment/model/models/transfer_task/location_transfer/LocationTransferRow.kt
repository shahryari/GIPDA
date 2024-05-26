package com.example.warehousemanagment.model.models.transfer_task.location_transfer


import com.google.gson.annotations.SerializedName

data class LocationTransferRow(
    @SerializedName("AvailableInventory")
    val availableInventory: Int,
    @SerializedName("DestinationLcationCode")
    val destinationLcationCode: String,
    @SerializedName("FirstInvTypeID")
    val firstInvTypeID: Any,
    @SerializedName("FirstInvTypeTitle")
    val firstInvTypeTitle: Any,
    @SerializedName("GoodID")
    val goodID: String,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
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
    @SerializedName("RealInventory")
    val realInventory: Int,
    @SerializedName("SerialNumber")
    val serialNumber: Any,
    @SerializedName("SourceLocationCode")
    val sourceLocationCode: String,
    @SerializedName("TransferTypeID")
    val transferTypeID: Int,
    @SerializedName("UOMName")
    val uOMName: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("WarehouseName")
    val warehouseName: String,
    @SerializedName("TaskTime")
    val taskTime:String,
    @SerializedName("TaskTimeString")
    val taskTimeString:String ,
)