package com.example.warehousemanagment.model.models.putaway.truck_detail


import com.google.gson.annotations.SerializedName

data class PutawayTruckDetailModel(
    @SerializedName("rows")
    val putawayDetailRows: List<PutawayDetailRow>,
    @SerializedName("total")
    val total: Int
)