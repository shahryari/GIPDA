package com.example.warehousemanagment.model.models.putaway.serial_putaway
import com.google.gson.annotations.SerializedName


data class SerialPutawayAssignModel(
    @SerializedName("EnableClose")
    val enableClose: Boolean,
    @SerializedName("EnableConfirm")
    val enableConfirm: Boolean,
    @SerializedName("EnableInsert")
    val enableInsert: Boolean,
    @SerializedName("EnableUpdate")
    val enableUpdate: Boolean,
    @SerializedName("EntityID")
    val entityID: Any,
    @SerializedName("EntityStringKey")
    val entityStringKey: Any,
    @SerializedName("IsSucceed")
    val isSucceed: Boolean,
    @SerializedName("MessageCode")
    val messageCode: Int,
    @SerializedName("MessageType")
    val messageType: Int,
    @SerializedName("Messages")
    val messages: List<String>,
    @SerializedName("ReturnValue")
    val returnValue: String,
    @SerializedName("UpdatedAny")
    val updatedAny: Boolean
)