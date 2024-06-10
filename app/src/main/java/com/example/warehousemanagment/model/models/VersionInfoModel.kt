package com.example.warehousemanagment.model.models

import com.google.gson.annotations.SerializedName

data class VersionInfoModel(
    @SerializedName("CurrentVersion") val version: Int,
    @SerializedName("DownloadUrl") val downloadUrl: String,
    @SerializedName("ShowVersion") val showVersion: String
)
