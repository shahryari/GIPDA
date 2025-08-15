package com.example.warehousemanagment.model.models.stock.stock_take


import com.google.gson.annotations.SerializedName

data class StockTrackRow(
    @SerializedName("StockTakingID")
    val stockTakingID: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("WarehouseName")
    val warehouseName: String,
    @SerializedName("Cycle")
    val cycle: Int,
    @SerializedName("CycleTitle")
    val cycleTitle: String,
    @SerializedName("Year")
    val year: Int
)
