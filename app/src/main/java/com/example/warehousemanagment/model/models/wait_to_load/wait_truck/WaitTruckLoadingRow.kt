package com.example.warehousemanagment.model.models.wait_to_load.wait_truck


import com.google.gson.annotations.SerializedName

data class WaitTruckLoadingRow(
    @SerializedName("BOLNumber")
    val bOLNumber: String,
    @SerializedName("CarTypeID")
    val carTypeID: Int,
    @SerializedName("CarTypeTitle")
    val carTypeTitle: String,
    @SerializedName("CreatedOn")
    val createdOn: String,
    @SerializedName("CreatedOnString")
    val createdOnString: String,
    @SerializedName("Dock")
    val dock: String,
    @SerializedName("DockAssignTime")
    val dockAssignTime: String,
    @SerializedName("DockAssignTimeString")
    val dockAssignTimeString: String,
    @SerializedName("DriverFirstName")
    val driverFirstName: Any,
    @SerializedName("DriverFullName")
    val driverFullName: String,
    @SerializedName("DriverLastName")
    val driverLastName: String,
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
    @SerializedName("ShippingAddressID")
    val shippingAddressID: String,
    @SerializedName("ShippingID")
    val shippingID: String,
    @SerializedName("ShippingNumber")
    val shippingNumber: String ,
    @SerializedName("DriverImageUrl")
    val driverImageUrl:String?
)