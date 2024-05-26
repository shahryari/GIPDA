package com.example.warehousemanagment.model.models.notif


import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @SerializedName("PickingCount")
    val pickingCount: Int,
    @SerializedName("PutawayCount")
    val putawayCount: Int,
    @SerializedName("ReceivingCount")
    val receivingCount: Int,
    @SerializedName("ShippingCount")
    val shippingCount: Int,
    @SerializedName("TransferCount")
    val transferCount: Int
)