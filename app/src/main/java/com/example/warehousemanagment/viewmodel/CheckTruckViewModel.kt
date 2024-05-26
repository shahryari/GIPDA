package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.check_truck.CheckTruckModel
import com.example.warehousemanagment.model.models.check_truck.confirm.ConfirmCheckTruckModel
import com.example.warehousemanagment.model.models.check_truck.deny.DenyCheckTruckModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class CheckTruckViewModel(application: Application,context: Context)
    : AndroidViewModel(application)
{
    private val repository= MyRepository( )
    private var context: Context=context
    private var checkTruckList= MutableLiveData<List<CheckTruckModel>>()
    private var confirmTruckModel= MutableLiveData<ConfirmCheckTruckModel>()
    private var denyTruckModel=MutableLiveData<DenyCheckTruckModel>()

    fun getDenyReason(): List<CatalogModel>
    {
        val reasons:ArrayList<CatalogModel> = ArrayList()
        reasons.add(CatalogModel("",1
            ,context.getString(R.string.notApprovingCar)))
        reasons.add(CatalogModel("",2
            ,context.getString(R.string.notApprovingDriver)))
        reasons.add(CatalogModel("",3
            ,context.getString(R.string.resign)))


        return reasons
    }

    fun getCheckTruckList(): LiveData<List<CheckTruckModel>> {
        return checkTruckList
    }

    fun getDenyTruck(): MutableLiveData<DenyCheckTruckModel> {
        return denyTruckModel
    }

    fun getConfirmTruck(): MutableLiveData<ConfirmCheckTruckModel> {
        return confirmTruckModel
    }

    fun setConfirmTruck(
        baseUrl:String,shippingAddressId:String,cookie:String)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            repository.confirmCheckTruck(baseUrl,jsonObject,cookie)
                .subscribe({
                    confirmTruckModel.postValue(it)
                    log("confirmCheckTruck", it.toString())

                }, {
                    showErrorMsg(it,"confirmCheckTruck",context)
                },).let {  }
        }

    }
    fun setDenyTruck(
        baseUrl:String,shippingAddressId:String,
                     shippingId:String,reason:Int,cookie:String,progressBar: ProgressBar)
    {
        viewModelScope.launch {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            jsonObject.addProperty("ShippingID",shippingId)
            jsonObject.addProperty("Reason",reason)
            showSimpleProgress(true,progressBar)
            log("denyJson",jsonObject.toString())
            repository.denyCheckTruck(baseUrl,jsonObject,cookie)
                .subscribe({
                    showSimpleProgress(false,progressBar)
                    denyTruckModel.postValue(it)
                    log("denyCheckTruck", it.toString())

                }, {
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"denyCheckTruck",context)
                },).let {  }
        }

    }

    fun setCheckTruck(
        baseUrl:String,cookie:String)
    {
        viewModelScope.launch {
            repository.checkTruckList(baseUrl,cookie)
                .subscribe({
                    checkTruckList.postValue(it)
                    log("checkTruck", it.toString())

                }, {
                    showErrorMsg(it,"checkTruck",context)
                },).let {  }
        }

    }




}