package com.example.warehousemanagment.model.models.login.current_user


import com.google.gson.annotations.SerializedName

data class CurrentUserModel(
    @SerializedName("FullName")
    val fullName: String,
    @SerializedName("TokenID")
    val tokenID: Any,
    @SerializedName("UserName")
    val userName: String
)