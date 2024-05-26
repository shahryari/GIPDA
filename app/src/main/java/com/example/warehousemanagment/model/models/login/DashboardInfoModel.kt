package com.example.warehousemanagment.model.models.login


import com.google.gson.annotations.SerializedName

data class DashboardInfoModel(
    @SerializedName("PickingCount")
    val pickingCount: Int,
    @SerializedName("PutawayCount")
    val putawayCount: Int,
    @SerializedName("ReceivingCount")
    val receivingCount: Int,
    @SerializedName("ShippingCount")
    val shippingCount: Int,
    @SerializedName("TransferCount")
    val transferCount: Int,
    @SerializedName("CargoCount")
    val cargoCount:Int,
    @SerializedName("MyCargoCount")
    val myCargoCount:Int,
)