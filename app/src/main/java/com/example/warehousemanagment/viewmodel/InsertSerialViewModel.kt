package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.insert_serial.*
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class InsertSerialViewModel(application: Application,context: Context)
    : AndroidViewModel(application)
{
    private val repository= MyRepository( )
    private var context: Context=context
    private var wareHouseList= MutableLiveData<List<WarehouseModel>>()
    private var ownerList= MutableLiveData<List<OwnerModel>>()
    private var productList=MutableLiveData<List<ProductModel>>()

    private var tempSerialList=ArrayList<InsertedSerialModel>()
    private var serialList=MutableLiveData<List<InsertedSerialModel>>()

    fun getSerialList(): MutableLiveData<List<InsertedSerialModel>> {
        return serialList
    }

    fun getProductsList(): MutableLiveData<List<ProductModel>> {
        return productList
    }

    fun getWareHouse(): MutableLiveData<List<WarehouseModel>> {
        return wareHouseList
    }
    fun getOwners(): MutableLiveData<List<OwnerModel>> {
        return ownerList
    }


    fun setWareHouse(
        baseUrl:String,cookie:String)
    {
        viewModelScope.launch()
        {
            repository.getWarehouse(baseUrl,cookie)
                .subscribe({
                    wareHouseList.value=(it)
                    log("WareHouse", it.toString())

                }, {
                    showErrorMsg(it,"WareHouse",context)
                },).let {  }
        }

    }
    fun setOwnerList(
        baseUrl:String,cookie:String)
    {
        viewModelScope.launch()
        {
            repository.getOwner(baseUrl,cookie)
                .subscribe({
                    ownerList.value=(it)
                    log("Owner", it.toString())

                }, {
                    showErrorMsg(it,"Owner",context)
                },).let {  }
        }

    }
    fun setProductList(
        baseUrl:String,productTitle:String,cookie: String)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ProductTitle",productTitle)
            repository.getProducts(baseUrl,jsonObject,cookie)
                .subscribe({
                    productList.value=(it)
                    log("products", it.toString())

                }, {
                    showErrorMsg(it,"products",context)
                },).let {  }
        }
    }
    fun removeSerial(model:InsertedSerialModel)
    {
        tempSerialList.remove(model)
        serialList.value=tempSerialList
    }

    fun insertSerial(productId:String,serialNumber:String,warehouseId:String,
                     ownerInfoId:String,inveTypeId:Int)
    {
        viewModelScope.launch()
        {
            val insertSerialModel=InsertedSerialModel(
                productId,
                serialNumber,
                warehouseId,
                ownerInfoId,
                invTypeId = inveTypeId
            )
            tempSerialList.add(insertSerialModel)
            serialList.value=tempSerialList
        }
    }

    fun insertAllSerial(
        baseUrl:String,progressBar: ProgressBar,cookie: String,callBack:()->Unit)
    {
        if (tempSerialList.size==0)
        {
            toast(context.getString(R.string.serialListEmpty),context)
        }else
        {
            val jsonArray=JsonArray()
            for (model in tempSerialList)
            {
                val jsonObject=JsonObject()
                jsonObject.addProperty("ProductID",model.productId)
                jsonObject.addProperty("SerialNumber",model.serialNumber)
                jsonObject.addProperty("WarehouseID",model.warehouseId)
                jsonObject.addProperty("OwnerInfoID",model.ownerInfoId)
                jsonObject.addProperty("InvTypeID",model.invTypeId)

                jsonArray.add(jsonObject)
            }

            log("insertArray",jsonArray.toString())
            viewModelScope.launch()
            {
                showSimpleProgress(true,progressBar)
                repository.insertSerial(baseUrl,jsonArray,cookie)
                    .subscribe({
                        showSimpleProgress(false,progressBar)
                        log("insertSerial", it.toString())

                        clearList()
                        callBack()


                    }, {
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it,"insertSerial",context)
                    },).let {  }
            }
        }

    }

    private fun clearList() {
        tempSerialList.clear()
        serialList.value = tempSerialList
    }

    fun invList(context: Context): List<CatalogModel> {
        val invList = ArrayList<CatalogModel>()
        invList.add(CatalogModel(valueField =1 , title = context.getString(R.string.healty)))
        invList.add(CatalogModel(valueField =2 , title = context.getString(R.string.defection)))
        invList.add(CatalogModel(valueField =3 , title = context.getString(R.string.toredPaper)))
        invList.add(CatalogModel(valueField =4, title = context.getString(R.string.null2)))
        invList.add(CatalogModel(valueField =5 , title = context.getString(R.string.null2)))
        return invList
    }


}