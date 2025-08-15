package com.example.warehousemanagment.model.models.putaway.serial_putaway


import com.google.gson.annotations.SerializedName
data class SerialReceiptOnPutawayRow(
    @SerializedName("BOLNumber")
    val bOLNumber: String?,
    @SerializedName("ContainerNumber")
    val containerNumber: String,
    @SerializedName("CreatedOnString")
    val createdOnString: String,
    @SerializedName("DriverFullName")
    val driverFullName: String?,
    @SerializedName("DriverImageUrl")
    val driverImageUrl: String?,
    @SerializedName("OwnerName")
    val ownerName: String?,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("PlaqueNumber")
    val plaqueNumber: String,
    @SerializedName("PlaqueNumberFirst")
    val plaqueNumberFirst: String,
    @SerializedName("PlaqueNumberFourth")
    val plaqueNumberFourth: String,
    @SerializedName("PlaqueNumberSecond")
    val plaqueNumberSecond: String,
    @SerializedName("PlaqueNumberThird")
    val plaqueNumberThird: String,
    @SerializedName("ReceiptID")
    val receiptID: String,
    @SerializedName("ReceiptNumber")
    val receiptNumber: String,
    @SerializedName("ReceiptTypeTitle")
    val receiptTypeTitle: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: String
)