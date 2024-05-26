package com.test


import com.google.gson.annotations.SerializedName

data class StockTakeModel(
    @SerializedName("rows")
    val rows: List<StockTrackRow>,
    @SerializedName("total")
    val total: Int
)