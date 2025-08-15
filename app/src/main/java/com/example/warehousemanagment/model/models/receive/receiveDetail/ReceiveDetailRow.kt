package com.example.warehousemanagment.model.models.receive.receiveDetail


import com.google.gson.annotations.SerializedName

data class ReceiveDetailRow(
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("PalletLayer")
    val palletLayer: Int,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductID")
    val productID: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("ProductOwnerCode")
    val productOwnerCode: String?,
    @SerializedName("ProductOwnerName")
    val productOwnerName: String?,
    @SerializedName("QuantityPerLayer")
    val quantityPerLayer: Int,
    @SerializedName("ReceivingDetailID")
    val receivingDetailID: String,
    @SerializedName("Serializable")
    val serializable: Boolean,
    @SerializedName("UOMName")
    val uOMName: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("WorkerID")
    val workerID: String,
    @SerializedName("WorkerTaskID")
    val workerTaskID: String,
    @SerializedName("OwnerCode")
    val ownerCode:String
)