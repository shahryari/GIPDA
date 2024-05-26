package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.my_cargo.my_cargo_detail.MyCargoDetailRow
import com.google.gson.JsonObject
import kotlinx.coroutines.launch


class MyCargoDetailViewModel(application: Application, context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var cargoDetailList: MutableLiveData<List<MyCargoDetailRow>>
    = MutableLiveData<List<MyCargoDetailRow>>()

    private var tempList=ArrayList<MyCargoDetailRow>()

    private var cargoCount=MutableLiveData<Int>()


    fun clearReceiveList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            cargoDetailList.value = emptyList()
        }

    }
    fun getCargoCount(): MutableLiveData<Int> {
        return cargoCount
    }
    fun getCargoDetailList(): MutableLiveData<List<MyCargoDetailRow>>  {
        return cargoDetailList
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
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            repository.getMyCargoDetailList(baseUrl,jsonObject,page, rows, sort, asc,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.myCargoDetailRow.size>0)
                {
                    tempList.addAll(it.myCargoDetailRow)
                    cargoDetailList.value= tempList

                }
                cargoCount.value=it.total
                log("receiving",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"mycargoDetail", context )
            } ).let {  }
        }
    }

    fun changeDriverTaskDone(
        url: String,
        shippingAddressId:String,
        progressBar: ProgressBar,
        cookie: String ,
        onErrorCallback:()->Unit,
        callBack:()->Unit
    ){
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            repository.changeDriverTaskDone(url,jsonObject,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                log("changeDriverTaskDone",it.toString())
                callBack()

            },{
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"changeDriverTaskDone", context )
                onErrorCallback()
            } ).let {  }
        }
    }


    fun driverTaskRemove(
        url: String,
        shippingAddressId:String,
        progressBar: ProgressBar,
        cookie: String ,
        onErrorCallback:()->Unit,
        callBack:()->Unit
    ){
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            repository.driverTaskRemove(url,jsonObject,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                log("DriverTaskRemove",it.toString())
                callBack()

            },{
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"DriverTaskRemove", context )
                onErrorCallback()
            } ).let {  }
        }
    }


    fun cargoDetailWorkerSubmit(
        url: String,
        shippingAddressDetailId:String,
        progressBar: ProgressBar,
        cookie: String ,
        callBack:()->Unit
    ){
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailId)
            repository.cargoDetailWorkerSubmit(url,jsonObject,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                log("cargoDetailWorkerSubmit",it.toString())
                callBack()

            },{
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"cargoDetailWorkerSubmit", context )
            } ).let {  }
        }
    }


    fun cargoDetailWorkerRemove(
        url: String,
        shippingAddressDetailId:String,
        progressBar: ProgressBar,
        cookie: String ,
        callBack:()->Unit
    ){
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailId)
            repository.cargoDetailWorkerRemove(url,jsonObject,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                log("CargoDetailWorkerRemove",it.toString())
                callBack()

            },{
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"CargoDetailWorkerRemove", context )
            } ).let {  }
        }
    }

}