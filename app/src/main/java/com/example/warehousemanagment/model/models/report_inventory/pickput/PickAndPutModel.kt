package com.example.warehousemanagment.model.models.report_inventory.pickput


import com.google.gson.annotations.SerializedName

data class PickAndPutModel(
    @SerializedName("rows")
    val pickAndPutRows: List<PickAndPutRow>,
    @SerializedName("total")
    val total: Int
)