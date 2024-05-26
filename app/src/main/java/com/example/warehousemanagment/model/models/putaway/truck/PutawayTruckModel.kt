package com.example.warehousemanagment.model.models.putaway.truck


import com.google.gson.annotations.SerializedName

data class PutawayTruckModel(
    @SerializedName("rows")
    val rows: List<PutawayTruckRow>,
    @SerializedName("total")
    var total: Int
)