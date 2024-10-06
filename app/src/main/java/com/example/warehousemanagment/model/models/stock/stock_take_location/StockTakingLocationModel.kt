package com.test


import com.example.warehousemanagment.model.models.stock.stock_take_location.StockTackingLocationRow
import com.google.gson.annotations.SerializedName

data class StockTakingLocationModel(
    @SerializedName("rows")
    val rows: List<StockTackingLocationRow>,
    @SerializedName("total")
    val total: Int
)