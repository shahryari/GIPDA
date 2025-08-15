package com.example.warehousemanagment.model.models.putaway.truck_detail


import com.google.gson.annotations.SerializedName

data class PutawayDetailRow(
    @SerializedName("ContainerNumber")
    val containerNumber: String,
    @SerializedName("DockCode")
    val dockCode: String,
    @SerializedName("GoodsID")
    val goodsID: String,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("ItemLocationID")
    val itemLocationID: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("OwnerCode")
    val productOwnerCode: String?,
    @SerializedName("OwnerName")
    val productOwnerName: String?,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("ReceiptDetailID")
    val receiptDetailID: String,
    @SerializedName("UOMName")
    val uOMName: String
)