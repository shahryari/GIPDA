package com.example.warehousemanagment.model.models.my_cargo.my_cargo_detail


import com.google.gson.annotations.SerializedName

data class MyCargoDetailModel(
    @SerializedName("rows")
    val myCargoDetailRow: List<MyCargoDetailRow>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("sumQuantity")
    val sumQuantity: Int
)