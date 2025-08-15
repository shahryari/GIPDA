package com.example.warehousemanagment.model.models.receive.receiving


import com.google.gson.annotations.SerializedName

data class RowReceivingModel(
    @SerializedName("CarTypeID")
    val carTypeID: String?,
    @SerializedName("CarTypeTitle")
    val carTypeTitle: String?,
    @SerializedName("ContainerNumber")
    val containerNumber: String,
    @SerializedName("CreatedOn")
    val createdOn: String,
    @SerializedName("CreatedOnString")
    val createdOnString: String,
    @SerializedName("DockCode")
    val dockCode: String,
    @SerializedName("DriverFirstName")
    val driverFirstName: String,
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
    @SerializedName("ReceivingID")
    val receivingID: String,
    @SerializedName("ReceivingNumber")
    val receivingNumber: String,
    @SerializedName("ReceivingTypeID")
    val receivingTypeID: Int,
    @SerializedName("ReceivingTypeTitle")
    val receivingTypeTitle: String,
    @SerializedName("TaskTime")
    val taskTime:String,
    @SerializedName("OwnerName")
    val ownerName: String?,
    @SerializedName("OwnerCode")
    val ownerCode: String?,
    @SerializedName("TaskTimeString")
    val taskTimeString:String ,
    @SerializedName("DriverImageUrl")
    val driverImageUrl:String?
)