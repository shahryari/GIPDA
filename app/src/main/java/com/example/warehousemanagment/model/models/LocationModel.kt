package com.example.warehousemanagment.model.models

import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("LocationCode")
    val locationCode: String
)