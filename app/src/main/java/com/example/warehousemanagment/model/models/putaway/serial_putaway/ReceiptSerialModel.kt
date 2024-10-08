package com.example.warehousemanagment.model.models.putaway.serial_putaway


import com.google.gson.annotations.SerializedName

data class ReceiptSerialModel(
    @SerializedName("rows")
    val rows: List<ReceiptSerialRow>,
    @SerializedName("total")
    val total: Int
)

data class ReceiptSerialRow(
    @SerializedName("BusinessUnitID")
    val businessUnitID: String,
    @SerializedName("CreatedOn")
    val createdOn: String,
    @SerializedName("CurrentContainerNumber")
    val currentContainerNumber: String,
    @SerializedName("CurrentDeclarationRegNumber")
    val currentDeclarationRegNumber: String,
    @SerializedName("Description")
    val description: Any?,
    @SerializedName("EntityID")
    val entityID: Any?,
    @SerializedName("EntityResourceID")
    val entityResourceID: String,
    @SerializedName("EntityState")
    val entityState: Int,
    @SerializedName("EntityStringKey")
    val entityStringKey: String,
    @SerializedName("GoodID")
    val goodID: String,
    @SerializedName("GoodsSystemCode")
    val goodsSystemCode: String,
    @SerializedName("GoodsTitle")
    val goodsTitle: String,
    @SerializedName("InsertTypeID")
    val insertTypeID: Int,
    @SerializedName("Inuse")
    val inuse: Boolean,
    @SerializedName("InvTypeID")
    val invTypeID: Any?,
    @SerializedName("InventoryModifyID")
    val inventoryModifyID: Any?,
    @SerializedName("IsActive")
    val isActive: Boolean,
    @SerializedName("IsDamageSerial")
    val isDamageSerial: Boolean,
    @SerializedName("IsDirty")
    val isDirty: Boolean,
    @SerializedName("IsReturnReceiving")
    val isReturnReceiving: Any?,
    @SerializedName("IsVerified")
    val isVerified: Boolean,
    @SerializedName("ItemLocationCancelID")
    val itemLocationCancelID: Any?,
    @SerializedName("ItemLocationID")
    val itemLocationID: Any?,
    @SerializedName("ItemSerialID")
    val itemSerialID: String,
    @SerializedName("ItemSerialLocation")
    val itemSerialLocation: Any?,
    @SerializedName("ItemSerialStatusID")
    val itemSerialStatusID: Any?,
    @SerializedName("LocationCode")
    val locationCode: Any?,
    @SerializedName("LocationID")
    val locationID: Any?,
    @SerializedName("ManagingNumber")
    val managingNumber: Any?,
    @SerializedName("ManagingNumberUpdateBy")
    val managingNumberUpdateBy: Any?,
    @SerializedName("ModalityTypeID")
    val modalityTypeID: Any?,
    @SerializedName("Owner")
    val owner: String,
    @SerializedName("OwnerInfoID")
    val ownerInfoID: String,
    @SerializedName("ReceivingID")
    val receivingID: String,
    @SerializedName("ResourceCode")
    val resourceCode: String,
    @SerializedName("ReturnGoodsRequestID")
    val returnGoodsRequestID: Any?,
    @SerializedName("SerialCreatedOn")
    val serialCreatedOn: String,
    @SerializedName("SerialID")
    val serialID: String,
    @SerializedName("SerialIsActive")
    val serialIsActive: Boolean,
    @SerializedName("SerialNumber")
    val serialNumber: String,
    @SerializedName("SerialOwnerFullName")
    val serialOwnerFullName: String,
    @SerializedName("SerialUploadID")
    val serialUploadID: Any?,
    @SerializedName("Serializable")
    val serializable: Boolean,
    @SerializedName("ShippingID")
    val shippingID: Any?,
    @SerializedName("StatusID")
    val statusID: Int,
    @SerializedName("Version")
    val version: String,
    @SerializedName("WarehouseCode")
    val warehouseCode: Any?,
    @SerializedName("WarehouseID")
    val warehouseID: String
)