package com.example.warehousemanagment.model.models.login.login


import com.google.gson.annotations.SerializedName

data class Permissions(
    @SerializedName("InsertSerial")
    val insertSerial: Boolean,
    @SerializedName("InventoryTypeModifyTask")
    val inventoryTypeModifyTask: Boolean,
    @SerializedName("LocationInventory")
    val locationInventory: Boolean,
    @SerializedName("LocationTransfer")
    val locationTransfer: Boolean,
    @SerializedName("OfflineSerial")
    val offlineSerial: Boolean,
    @SerializedName("PickPutReport")
    val pickPutReport: Boolean,
    @SerializedName("Picking")
    val picking: Boolean,
    @SerializedName("ProductWithoutMaster")
    val productWithoutMaster: Boolean,
    @SerializedName("Putaway")
    val putaway: Boolean,
    @SerializedName("Receiving")
    val receiving: Boolean,
    @SerializedName("SerialReport")
    val serialReport: Boolean,
    @SerializedName("Shipping")
    val shipping: Boolean,
    @SerializedName("ShippingCancel")
    val shippingCancel: Boolean,
    @SerializedName("TrackingSerial")
    val trackingSerial: Boolean,
    @SerializedName("TransferTask")
    val transferTask: Boolean,
    @SerializedName("WaitForLoading")
    val waitForLoading: Boolean
){
    override fun toString(): String
    {
        return "{" +
                "pickPutReport=" + pickPutReport +
                ", picking=" + picking +
                ", receiving=" + receiving +
                ", offlineSerial=" + offlineSerial +
                ", putaway=" + putaway +
                ", waitForLoading=" + waitForLoading +
                ", transferTask=" + transferTask +
                ", inventoryTypeModifyTask=" + inventoryTypeModifyTask +
                ", serialReport=" + serialReport +
                ", shippingCancel=" + shippingCancel +
                ", trackingSerial=" + trackingSerial +
                ", locationTransfer=" + locationTransfer +
                ", productWithoutMaster=" + productWithoutMaster +
                ", locationInventory=" + locationInventory +
                ", insertSerial=" + insertSerial +
                ", shipping=" + shipping +
                '}'
    }
}