package com.example.warehousemanagment.model.models.shipping.shipping_truck


import com.google.gson.annotations.SerializedName

data class ShippingTruckRow(
    @SerializedName("BOLNumber")
    val bOLNumber: String,
    @SerializedName("CarTypeID")
    val carTypeID: Int,
    @SerializedName("CarTypeTitle")
    val carTypeTitle: String,
    @SerializedName("CheckTruckCount")
    val checkTruckCount: Any,
    @SerializedName("CreatedOn")
    val createdOn: String,
    @SerializedName("CreatedOnString")
    val createdOnString: String,
    @SerializedName("CurrentStatusCode")
    val currentStatusCode: String ?="",
    @SerializedName("CustomerFullName")
    val customerFullName: String,
    @SerializedName("DockCode")
    val dockCode: String,
    @SerializedName("DriverFirstName")
    val driverFirstName: Any,
    @SerializedName("DriverFullName")
    val driverFullName: String,
    @SerializedName("DriverLastName")
    val driverLastName: Any,
    @SerializedName("HasCheckTruck")
    val hasCheckTruck: Boolean,
    @SerializedName("IsDone")
    val isDone: Boolean,
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
    val shippingNumber: String,
    @SerializedName("TaskTypeID")
    val taskTypeID: Int,
    @SerializedName("TaskTypeTitle")
    val taskTypeTitle: String,
    @SerializedName("WarehouseID")
    val warehouseID: String,
    @SerializedName("WorkerID")
    val workerID: String,
    @SerializedName("WorkerTaskID")
    val workerTaskID: String,
    @SerializedName("DriverImageUrl")
    val driverImageUrl:String?,
    @SerializedName("DockAssignTime")
    val dockAssignTime:String,
    @SerializedName("DockAssignTimeString")
    val dockAssignTimeString:String ,
    @SerializedName("Total")
    val total: Int,
    @SerializedName("DoneCount")
    val doneCount: Int,
    @SerializedName("SumQuantity")
    val sumQuantity: Int,
    @SerializedName("SumDoneQuantity")
    val sumDonQuantity: Int,
    @SerializedName("CustomerCount")
    val customerCount: Int
)