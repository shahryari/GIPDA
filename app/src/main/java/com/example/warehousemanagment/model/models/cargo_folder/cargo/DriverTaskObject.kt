package com.example.warehousemanagment.model.models.cargo_folder.cargo


import com.google.gson.annotations.SerializedName

data class DriverTaskObject(
    @SerializedName("DocumentInfoID")
    val documentInfoID: Any,
    @SerializedName("DocumentName")
    val documentName: Any,
    @SerializedName("DriverImageUrl")
    val driverImageUrl: String,
    @SerializedName("PersonFullName")
    val personFullName: String,
    @SerializedName("RoleCode")
    val roleCode: String,
    @SerializedName("RoleName")
    val roleName: String,
    @SerializedName("IsDone")
    val isDone:Boolean
)