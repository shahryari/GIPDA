package com.example.warehousemanagment.model.models.dock_assign

import com.google.gson.annotations.SerializedName


data class DockListOnShippingModel(
    @SerializedName("rows")
    val rows: List<DockListOnShippingRow>,
    @SerializedName("total")
    val total: Int
)

data class DockListOnShippingRow(
    @SerializedName("DockCode")
    val dockCode: String,
    @SerializedName("DockID")
    val dockID: String,
    @SerializedName("DockReceivingCount")
    val dockReceivingCount: Int,
    @SerializedName("DockShippingCount")
    val dockShippingCount: Int,
    @SerializedName("DockTypeID")
    val dockTypeID: Int,
    @SerializedName("DockTypeTitle")
    val dockTypeTitle: String?,
    @SerializedName("GateI")
    val gateI: Int
)