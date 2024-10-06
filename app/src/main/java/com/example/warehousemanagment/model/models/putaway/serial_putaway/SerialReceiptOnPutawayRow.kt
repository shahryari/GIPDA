package com.example.warehousemanagment.model.models.putaway.serial_putaway


import com.google.gson.annotations.SerializedName
data class SerialReceiptOnPutawayRow(
    @SerializedName("BOLNumber")
    val bOLNumber: Any,
    @SerializedName("ContainerNumber")
    val containerNumber: String,
    @SerializedName("CreatedOn")
    val createdOn: String,
    @SerializedName("CreatedOnString")
    val createdOnString: String,
    @SerializedName("CurrentStatusCode")
    val currentStatusCode: String,
    @SerializedName("CurrentStatusTitle")
    val currentStatusTitle: String,
    @SerializedName("OwnerInfoID")
    val ownerInfoID: String,
    @SerializedName("PlaqueNumber")
    val plaqueNumber: String,
    @SerializedName("ReceiptID")
    val receiptID: String,
    @SerializedName("ReceiptNumber")
    val receiptNumber: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("WarehouseID")
    val warehouseID: String
)

