package com.example.warehousemanagment.model.models.report_inventory.pickput


import com.google.gson.annotations.SerializedName

data class PickAndPutRow(
    @SerializedName("CreatedOn")
    val createdOn: String,
    @SerializedName("CreatedOnString")
    val createdOnString: String,
    @SerializedName("CustomerFullName")
    val customerFullName: String,
    @SerializedName("InvTypeID")
    val invTypeID: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle: String,
    @SerializedName("IsDone")
    val isDone: Boolean,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("OwnerCode")
    val ownerCode: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("TaskTypeID")
    val taskTypeID: Int,
    @SerializedName("TaskTypeTitle")
    val taskTypeTitle: String
)