package com.test


import com.example.warehousemanagment.model.models.stock.stock_take.StockTrackRow
import com.google.gson.annotations.SerializedName

data class StockTakeModel(
    @SerializedName("rows")
    val rows: List<StockTrackRow>,
    @SerializedName("total")
    val total: Int
)