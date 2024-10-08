package com.example.warehousemanagment.model.models.putaway.serial_putaway


import com.google.gson.annotations.SerializedName

data class ReceiptDetailLocationModel(
    @SerializedName("rows")
    val rows: List<ReceiptDetailLocationRow>,
    @SerializedName("total")
    val total: Int
)

data class ReceiptDetailLocationRow(
    @SerializedName("BusinessUnitID")
    val businessUnitID: String,
    @SerializedName("CancelCount")
    val cancelCount: Any?,
    @SerializedName("CompleteDate")
    val completeDate: Any?,
    @SerializedName("CompletedByFullname")
    val completedByFullname: Any?,
    @SerializedName("CompletedByID")
    val completedByID: Any?,
    @SerializedName("CompletedOn")
    val completedOn: Any?,
    @SerializedName("ContainerNumber")
    val containerNumber: String,
    @SerializedName("CreatedOn")
    val createdOn: String,
    @SerializedName("CustomerFullName")
    val customerFullName: Any?,
    @SerializedName("DriverFullName")
    val driverFullName: Any?,
    @SerializedName("EntityID")
    val entityID: Any?,
    @SerializedName("EntityResourceID")
    val entityResourceID: String,
    @SerializedName("EntityResourceNumber")
    val entityResourceNumber: String,
    @SerializedName("EntityState")
    val entityState: Int,
    @SerializedName("EntityStringKey")
    val entityStringKey: String,
    @SerializedName("GoodsID")
    val goodsID: String,
    @SerializedName("GoodsSystemCode")
    val goodsSystemCode: String,
    @SerializedName("GoodsTitle")
    val goodsTitle: String,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("IsActive")
    val isActive: Boolean,
    @SerializedName("IsCompleted")
    val isCompleted: Boolean,
    @SerializedName("IsDirty")
    val isDirty: Boolean,
    @SerializedName("IsSystemic")
    val isSystemic: Boolean,
    @SerializedName("ItemClassID")
    val itemClassID: String,
    @SerializedName("ItemLocationDescription")
    val itemLocationDescription: String,
    @SerializedName("ItemLocationID")
    val itemLocationID: String,
    @SerializedName("ItemSerialCount")
    val itemSerialCount: Int,
    @SerializedName("LocationCancelID")
    val locationCancelID: Any?,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("LocationID")
    val locationID: String,
    @SerializedName("LocationTypeID")
    val locationTypeID: Int,
    @SerializedName("Owner")
    val owner: String,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("OwnerFullname")
    val ownerFullname: String,
    @SerializedName("OwnerInfoID")
    val ownerInfoID: String,
    @SerializedName("Pack")
    val pack: Int,
    @SerializedName("PickingLocationCode")
    val pickingLocationCode: Any?,
    @SerializedName("PickingLocationID")
    val pickingLocationID: Any?,
    @SerializedName("Piece")
    val piece: Int,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("ReceiveDate")
    val receiveDate: Any?,
    @SerializedName("RemainTaskCount")
    val remainTaskCount: Int,
    @SerializedName("ResourceCode")
    val resourceCode: String,
    @SerializedName("SentDate")
    val sentDate: Any?,
    @SerializedName("Serializable")
    val serializable: Boolean,
    @SerializedName("ShippingBOLNumber")
    val shippingBOLNumber: Any?,
    @SerializedName("TaskTypeID")
    val taskTypeID: Int,
    @SerializedName("UOMCount")
    val uOMCount: Int,
    @SerializedName("Version")
    val version: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: String,
    @SerializedName("WarehouseID")
    val warehouseID: String,
    @SerializedName("WorkerFullname")
    val workerFullname: String,
    @SerializedName("WorkerID")
    val workerID: String
)