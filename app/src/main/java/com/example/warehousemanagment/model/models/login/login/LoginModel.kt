package com.example.warehousemanagment.model.models.login.login


import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("FullName")
    val fullName: String,
    @SerializedName("Permissions")
    val permissions: Permissions,
    @SerializedName("TokenID")
    val tokenID: String,
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("WarehouseCode")
    val warehouse:String,
    @SerializedName("RandomCheckCount")
    val randomCheckCount:Int
)