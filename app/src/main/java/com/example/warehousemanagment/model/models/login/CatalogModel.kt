package com.example.warehousemanagment.model.models.login


import com.google.gson.annotations.SerializedName

data class CatalogModel(
    @SerializedName("CatalogValueCode")
    val catalogValueCode: String="",
    @SerializedName("ValueField")
    val valueField: Int,
    @SerializedName("Title")
    val title: String,
)