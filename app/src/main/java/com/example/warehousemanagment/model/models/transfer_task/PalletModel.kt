package com.example.warehousemanagment.model.models.transfer_task


import com.google.gson.annotations.SerializedName

data class PalletModel(
    @SerializedName("PalletCode")
    val palletCode: String,
    @SerializedName("PalletID")
    val palletID: String,
    @SerializedName("PalletName")
    val palletName: String
)