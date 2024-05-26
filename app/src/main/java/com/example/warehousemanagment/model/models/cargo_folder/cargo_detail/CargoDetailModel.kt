package com.example.warehousemanagment.model.models.cargo_folder.cargo_detail


import com.google.gson.annotations.SerializedName

data class CargoDetailModel(
    @SerializedName("rows")
    val cargoDetailRows: List<CargoDetailRow>,
    @SerializedName("total")
    val total: Int
)