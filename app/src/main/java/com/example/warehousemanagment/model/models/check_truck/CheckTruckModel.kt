package com.example.warehousemanagment.model.models.check_truck


import com.google.gson.annotations.SerializedName

data class CheckTruckModel(
    @SerializedName("BOLNumber")
    val bOLNumber: String,
    @SerializedName("CreatedOn")
    val createdOn: String,
    @SerializedName("CurrentStatusCode")
    val currentStatusCode: String,
    @SerializedName("CustomerFullName")
    val customerFullName: String,
    @SerializedName("DockCode")
    val dockCode: String,
    @SerializedName("DriverFullName")
    val driverFullName: String,
    @SerializedName("IsDone")
    val isDone: Boolean,
    @SerializedName("PlaqueNumber")
    val plaqueNumber: String,
    @SerializedName("ShippingAddressID")
    val shippingAddressID: String,
    @SerializedName("ShippingID")
    val shippingID: String,
    @SerializedName("ShippingNumber")
    val shippingNumber: String,
    @SerializedName("TaskTypeID")
    val taskTypeID: Int,
    @SerializedName("WarehouseID")
    val warehouseID: String,
    @SerializedName("WorkerID")
    val workerID: String,
    @SerializedName("WorkerTaskID")
    val workerTaskID: String,
    @SerializedName("PlaqueNumberFirst")
    val plaqueNumberFirst: String,
    @SerializedName("PlaqueNumberFourth")
    val plaqueNumberFourth: String,
    @SerializedName("PlaqueNumberSecond")
    val plaqueNumberSecond: String,
    @SerializedName("PlaqueNumberThird")
    val plaqueNumberThird: String,
)