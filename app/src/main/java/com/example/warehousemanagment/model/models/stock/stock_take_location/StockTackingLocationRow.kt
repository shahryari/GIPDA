package com.example.warehousemanagment.model.models.stock.stock_take_location


import com.google.gson.annotations.SerializedName

data class StockTackingLocationRow(
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("StockTurnID")
    val stockTurnID: String,
    @SerializedName("StockTurnTeamID")
    val stockTurnTeamID: String,
    @SerializedName("StockTurnTeamLocationID")
    val stockTurnTeamLocationID: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("RealInventory")
    val realInventory: Int,
    @SerializedName("EnableSecondCountBox")
    val enableSecondCountBox: Boolean,
    @SerializedName("FirstQuantity")
    val firstQuantity:Int?,
    @SerializedName("SecondQuantity")
    val secondQuantity:Int?,
    @SerializedName("ThirdQuantity")
    val thirdQuantity: Int?,
    @SerializedName("HistoryCount")
    val historyCount:Int,
)