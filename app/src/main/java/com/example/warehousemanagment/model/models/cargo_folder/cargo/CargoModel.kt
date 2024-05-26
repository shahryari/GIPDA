package com.example.warehousemanagment.model.models.cargo_folder.cargo


import com.google.gson.annotations.SerializedName

data class CargoModel(
    @SerializedName("rows")
    val rows: List<CargoRow>,
    @SerializedName("total")
    val total: Int
)