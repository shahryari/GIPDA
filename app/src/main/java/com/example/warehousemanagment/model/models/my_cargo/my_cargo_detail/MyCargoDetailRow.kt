package com.example.warehousemanagment.model.models.my_cargo.my_cargo_detail


import com.google.gson.annotations.SerializedName

data class MyCargoDetailRow(
    @SerializedName("CancelCount")
    val cancelCount: Int ?,
    @SerializedName("CustomerCode")
    val customerCode: String,
    @SerializedName("CustomerFullName")
    val customerFullName: String,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("MeasurementUnitTitle")
    val measurementUnitTitle: String,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductID")
    val productID: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("SerialScanCount")
    val serialScanCount: Any,
    @SerializedName("Serializable")
    val serializable: Boolean,
    @SerializedName("ShippingAddressDetailID")
    val shippingAddressDetailID: String,
    @SerializedName("ShippingID")
    val shippingID: String,
    @SerializedName("ShippingQuantity")
    val shippingQuantity: Any,
    @SerializedName("TaskTime")
    val taskTime: Any,
    @SerializedName("TaskTimeString")
    val taskTimeString: Any,
    @SerializedName("WarehouseID")
    val warehouseID: String,
    @SerializedName("IsDone")
    val isDone:Boolean,
    @SerializedName("HasLowPriorityTitle")
    val hasLowPriorityTitle:String?,
    @SerializedName("ItemLocationID")
    val itemLocationId: String,
    @SerializedName("ShippingAddressID")
    val shippingAddressId: String,
    @SerializedName("CargoDoneBy")
    val doneBy: String
)