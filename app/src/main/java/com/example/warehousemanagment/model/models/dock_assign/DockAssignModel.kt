package com.example.warehousemanagment.model.models.dock_assign

import com.google.gson.annotations.SerializedName

data class DockAssignModel(
    @SerializedName("EnableClose")
    val enableClose: Boolean,
    @SerializedName("EnableConfirm")
    val enableConfirm: Boolean,
    @SerializedName("EnableInsert")
    val enableInsert: Boolean,
    @SerializedName("EnableUpdate")
    val enableUpdate: Boolean,
    @SerializedName("EntityID")
    val entityID: String?,
    @SerializedName("EntityStringKey")
    val entityStringKey: String?,
    @SerializedName("ErrorCode")
    val errorCode: Int,
    @SerializedName("IsSucceed")
    val isSucceed: Boolean,
    @SerializedName("MessageCode")
    val messageCode: Int,
    @SerializedName("MessageType")
    val messageType: Int,
    @SerializedName("Messages")
    val messages: List<String>,
    @SerializedName("ReturnValue")
    val returnValue: Int,
    @SerializedName("UpdatedAny")
    val updatedAny: Boolean
)


