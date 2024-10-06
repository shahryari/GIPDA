package com.example.warehousemanagment.model.models.stock

import com.google.gson.annotations.SerializedName

data class StockLocationModel(
    @SerializedName("rows") val rows: List<StockLocationRow>,
    @SerializedName("total") val total: Int
)

data class StockLocationRow(
    @SerializedName("LocationID") val locationId: String,
    @SerializedName("LocationCode") val locationCode: String,
)