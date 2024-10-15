package com.example.warehousemanagment.model.models.picking

import com.google.gson.annotations.SerializedName

class SerialBasePickingModel(
    @SerializedName("rows")
    val rows: List<SerialBasePickingRow>,
    @SerializedName("total")
    val total: Int
)

data class SerialBasePickingRow(
    @SerializedName("CustomerFullName")
    val customerFullName: String,
    @SerializedName("GTINCode")
    val gTINCode: Any?,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("ItemLocationID")
    val itemLocationID: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("LocationID")
    val locationID: String,
    @SerializedName("Locations")
    val locations: Any?,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("ShippingAddressDetailID")
    val shippingAddressDetailID: String,
    @SerializedName("ShippingAddressID")
    val shippingAddressID: String,
    @SerializedName("ShippingLocationCode")
    val shippingLocationCode: String,
    @SerializedName("ShippingLocationID")
    val shippingLocationID: String,
    @SerializedName("ShippingNumber")
    val shippingNumber: String,
    @SerializedName("SumQuantity")
    val sumQuantity: Int,
    @SerializedName("TaskTime")
    val taskTime: String,
    @SerializedName("TaskTimeString")
    val taskTimeString: String,
    @SerializedName("WarehouseID")
    val warehouseID: Any?
)


