package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.LocationModel
import com.example.warehousemanagment.model.models.cargo_folder.cargo_detail.CargoDetailRow
import com.google.gson.JsonObject
import kotlinx.coroutines.launch


class CargoDetailViewModel(application: Application, context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var cargoDetailList: MutableLiveData<List<CargoDetailRow>>
            = MutableLiveData<List<CargoDetailRow>>()

    private val cargoDetailLocations: MutableLiveData<List<LocationModel>> = MutableLiveData<List<LocationModel>>()


    private var tempList=ArrayList<CargoDetailRow>()

    private var cargoCount=MutableLiveData<Pair<Int,Int>>()


    fun clearReceiveList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            cargoDetailList.value = emptyList()
        }

    }
    fun getCargoCount(): MutableLiveData<Pair<Int,Int>> {
        return cargoCount
    }
    fun getCargoDetailList(): MutableLiveData<List<CargoDetailRow>>  {
        return cargoDetailList
    }

    fun getLocations() : LiveData<List<LocationModel>> {
        return cargoDetailLocations.distinctUntilChanged()
    }

    fun setLocations(baseUrl: String,shippingAddressId: String,cookie: String) {
        viewModelScope.launch {
            val jsonObject = JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            repository.getCargoDetailLocation(baseUrl,jsonObject,cookie).subscribe(
                {
                    cargoDetailLocations.value = it
                },
                {
                    showErrorMsg(it,"get locations",context)
                }
            )
        }
    }


    fun setCargoDetailList(
        baseUrl:String,
        cookie: String,
        keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        shippingAddressId:String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout,
        location:String,
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            jsonObject.addProperty("LocationCode",location)
            repository.getCargoDetailList(baseUrl,jsonObject,page, rows, sort, asc,cookie)
                .subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.cargoDetailRows.isNotEmpty())
                {
                    tempList.addAll(it.cargoDetailRows)
                    cargoDetailList.value= tempList

                }
                cargoCount.value= Pair(it.total,it.sumQuantity)
                log("receiving",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"cargoDetail", context )
            } ).let {  }
        }
    }


    fun driverTaskSubmit(
        url: String,
        shippingAddressId:String,
        progressBar: ProgressBar,
        cookie: String,
        onErrorCallback:()->Unit,
        callBack:()->Unit
    ){
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            jsonObject.addProperty("ForceUpdate","true")
            repository.driverTaskSubmit(url,jsonObject,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                log("driverTaskSubmit",it.toString())
                callBack()

            },{
                onErrorCallback()
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"driverTaskSubmit", context )
            } ).let {  }
        }
    }



}