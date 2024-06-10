package com.example.warehousemanagment.model.models.shipping.detail


import com.google.gson.annotations.SerializedName

data class ShippingDetailRow(
    @SerializedName("WarehouseID")
    val warehouseId:String,
    @SerializedName("CustomerCode")
    val customerCode: String,
    @SerializedName("CustomerFullName")
    val customerFullName: String,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductID")
    val productID: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("SerialScanCount")
    val serialScanCount: Int,
    @SerializedName("Serializable")
    val serializable: Boolean,
    @SerializedName("ShippingAddressDetailID")
    val shippingAddressDetailID: String,
    @SerializedName("ShippingID")
    val shippingID: String,
    @SerializedName("ShippingQuantity")
    val shippingQuantity: Int,
    @SerializedName("OwnerCode")
    val ownerCode:String,
    @SerializedName("TaskTime")
    val taskTime:String,
    @SerializedName("TaskTimeString")
    val taskTimeString:String ,
    @SerializedName("CancelCount")
    val cancelCount:Int ?,
    @SerializedName("CustomerColorID")
    val customerColorId: Int?,
    @SerializedName("CustomerColorCode")
    val customerColorCode: String?,
    @SerializedName("CustomerColorName")
    val customerColorName: String?
)