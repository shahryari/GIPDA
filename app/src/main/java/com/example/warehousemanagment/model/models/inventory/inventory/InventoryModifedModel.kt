package com.example.warehousemanagment.model.models.inventory.inventory


import com.google.gson.annotations.SerializedName

data class InventoryModifedModel(
    @SerializedName("rows")
    val inventoryModifyRows: List<InventoryModifyRow>,
    @SerializedName("total")
    val total: Int
)