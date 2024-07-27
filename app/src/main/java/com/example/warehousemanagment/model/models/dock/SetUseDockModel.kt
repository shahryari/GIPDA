package com.example.warehousemanagment.model.models.dock

import com.google.gson.annotations.SerializedName

data class SetUseDockModel(
    @SerializedName("IsSucceed") val isSucceed: Boolean,
    @SerializedName("UpdatedAny") val updatedAny: Boolean,
    @SerializedName("Messages") val messages: List<String>
)