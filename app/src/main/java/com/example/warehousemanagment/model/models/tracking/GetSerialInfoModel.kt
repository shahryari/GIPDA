package com.example.warehousemanagment.model.models.tracking


import com.google.gson.annotations.SerializedName

data class GetSerialInfoModel(
    @SerializedName("GTINCode")
    val gTINCode: Any,
    @SerializedName("GoodsSystemCode")
    val goodsSystemCode: String,
    @SerializedName("GoodsTitle")
    val goodsTitle: String,
    @SerializedName("HasLabel")
    val hasLabel: Boolean,
    @SerializedName("IsValidLabel")
    val isValidLabel: Boolean,
    @SerializedName("SerialID")
    val serialID: String,
    @SerializedName("SerialNumber")
    val serialNumber: String
)