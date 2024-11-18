package com.example.warehousemanagment.model.models.dock_assign

import com.google.gson.annotations.SerializedName

data class ShippingListOnDockModel(
    @SerializedName("rows")
    val rows: ShippingListOnDockRow,
    @SerializedName("total")
    val total: Int
)

data class ShippingListOnDockRow(
    @SerializedName("ArrivalTime")
    val arrivalTime: String?,
    @SerializedName("BOLDate")
    val bOLDate: String,
    @SerializedName("BOLNumber")
    val bOLNumber: String,
    @SerializedName("PlaqueNumber")
    val plaqueNumber: String,
    @SerializedName("PlaqueNumberFirst")
    val plaqueNumberFirst: String,
    @SerializedName("PlaqueNumberFourth")
    val plaqueNumberFourth: String,
    @SerializedName("PlaqueNumberSecond")
    val plaqueNumberSecond: String,
    @SerializedName("PlaqueNumberThird")
    val plaqueNumberThird: String,
    @SerializedName("CarTypeID")
    val carTypeID: Int,
    @SerializedName("CarTypeTitle")
    val carTypeTitle: String,
    @SerializedName("FirstDriverFullName")
    val firstDriverFullName: String,
    @SerializedName("SecondDriverFullName")
    val secondDriverFullName: String?,
    @SerializedName("ShippingAddressID")
    val shippingAddressID: String,
    @SerializedName("ShippingBolTypeID")
    val shippingBolTypeID: Int,
    @SerializedName("ShippingBolTypeTitle")
    val shippingBolTypeTitle: String?,
    @SerializedName("ShippingDate")
    val shippingDate: String,
    @SerializedName("ShippingNumber")
    val shippingNumber: String,
    @SerializedName("ThreePLDriverFullName")
    val threePLDriverFullName: String?,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("WarehouseID")
    val warehouseID: String,
    @SerializedName("WarehouseName")
    val warehouseName: String
)