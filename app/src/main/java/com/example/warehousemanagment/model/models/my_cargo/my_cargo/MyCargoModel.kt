package com.example.warehousemanagment.model.models.my_cargo.my_cargo


import com.google.gson.annotations.SerializedName

data class MyCargoModel(
    @SerializedName("rows")
    val rows: List<MyCargoRow>,
    @SerializedName("total")
    val total: Int
)