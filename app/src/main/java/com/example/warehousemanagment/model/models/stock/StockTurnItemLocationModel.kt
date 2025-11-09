package com.example.warehousemanagment.model.models.stock

import com.google.gson.annotations.SerializedName

data class StockTurnItemLocationModel(
    @SerializedName("rows")
    val rows: List<StockTurnItemLocationRow>,
    @SerializedName("total")
    val total: Int,
)

data class StockTurnItemLocationRow(
    @SerializedName("LocationCode")
    val locationCode: String?,
    @SerializedName("EntityResourceNumber")
    val entityResourceNumber: String?,
    @SerializedName("TaskTypeID")
    val taskTypeID: String?,
    @SerializedName("GoodsSystemCode")
    val goodsSystemCode: String?,
    @SerializedName("GoodsTitle")
    val goodsTitle: String?,
    @SerializedName("InvTypeID")
    val invTypeID: String?,
    @SerializedName("OwnerFullname")
    val ownerFullName: String?,
    @SerializedName("OwnerCode")
    val ownerCode: String?,
    @SerializedName("CompleteDateString")
    val completeDate: String?,
    @SerializedName("Quantity")
    val quantity: Int?
)