package com.example.warehousemanagment.model.models


import com.google.gson.annotations.SerializedName

data class DimenModel(
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)