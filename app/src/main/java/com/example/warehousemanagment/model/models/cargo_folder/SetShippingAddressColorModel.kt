package com.example.warehousemanagment.model.models.cargo_folder

import com.google.gson.annotations.SerializedName

data class SetShippingAddressColorModel(
    @SerializedName("IsSuccess") val isSuccess: Boolean,
    @SerializedName("Messages") val messages: List<String>
)