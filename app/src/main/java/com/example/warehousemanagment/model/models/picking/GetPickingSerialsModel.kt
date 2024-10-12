package com.example.warehousemanagment.model.models.picking

import com.google.gson.annotations.SerializedName

class GetPickingSerialsModel : ArrayList<GetPickingSerialRow>()
data class GetPickingSerialRow(
    @SerializedName("ItemLocationID")
    val itemLocationID: String,
    @SerializedName("ScanSerial")
    val scanSerial: Boolean,
    @SerializedName("SerialNumber")
    val serialNumber: String
)
