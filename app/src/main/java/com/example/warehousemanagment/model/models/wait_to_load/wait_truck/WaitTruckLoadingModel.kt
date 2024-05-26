package com.example.warehousemanagment.model.models.wait_to_load.wait_truck


import com.example.warehousemanagment.model.models.inventory.inventory.InventoryModifyRow
import com.example.warehousemanagment.model.models.wait_to_load.wait_truck.WaitTruckLoadingRow
import com.google.gson.annotations.SerializedName

data class WaitTruckLoadingModel(

    @SerializedName("rows")
    val waitTruckRows: List<WaitTruckLoadingRow>,
    @SerializedName("total")
    val total: Int
)