package com.example.warehousemanagment.model.models.picking

import com.google.gson.annotations.SerializedName

data class GetPickingSerialsModel(
    @SerializedName("ShippingDetail") val shippingDetail: SerialBasePickingRow?,
    @SerializedName("data") val data: List<GetPickingSerialRow>
)
data class GetPickingSerialRow(
    @SerializedName("ItemLocationID")
    val itemLocationID: String,
    @SerializedName("ScanSerial")
    val scanSerial: Boolean,
    @SerializedName("SerialNumber")
    val serialNumber: String
)
