package com.example.warehousemanagment.model.models.dock

import com.google.gson.annotations.SerializedName


data class DockRow(
    @SerializedName("DockID") val dockId: String,
    @SerializedName("DockCode") val dockCode: String,
    @SerializedName("WarehouseCode") val warehouseCode: String,
    @SerializedName("DockTypeID") val dockTypeID: Int,
)