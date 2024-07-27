package com.example.warehousemanagment.model.models.dock

import com.google.gson.annotations.SerializedName

data class DockModel(
    @SerializedName("rows") val rows: List<DockRow>,
    @SerializedName("total") val total: Int
)