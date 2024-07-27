package com.example.warehousemanagment.model.models.shipping

import com.google.gson.annotations.SerializedName

data class TruckLoadingRemoveModel(
    @SerializedName("Messages") val messages: List<String>
)