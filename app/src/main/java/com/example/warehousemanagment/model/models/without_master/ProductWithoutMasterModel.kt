package com.example.warehousemanagment.model.models.without_master


import com.google.gson.annotations.SerializedName

data class ProductWithoutMasterModel(
    @SerializedName("OwnerCode")
    val ownerCode: Any,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductGroup")
    val productGroup: String,
    @SerializedName("ProductID")
    val productID: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("SearchKeyword")
    val searchKeyword: Any,
    @SerializedName("CreatedOn")
    val createdOn:String,
    @SerializedName("CreatedOnString")
    val createdOnString:String ,
)