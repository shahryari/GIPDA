package com.example.warehousemanagment.model.models


import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("EntityID")
    val entityID: Any,
    @SerializedName("IsSucceed")
    val isSucceed: Boolean,
    @SerializedName("Message")
    val message: String,
    @SerializedName("ReturnValue")
    val returnValue: Int
)