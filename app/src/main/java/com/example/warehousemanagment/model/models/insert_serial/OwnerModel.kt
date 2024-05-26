package com.example.warehousemanagment.model.models.insert_serial


import com.google.gson.annotations.SerializedName

data class OwnerModel(
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("OwnerInfoID")
    val ownerInfoID: String,
    @SerializedName("OwnerInfoFullName")
    val ownerInfoFullName:String
)