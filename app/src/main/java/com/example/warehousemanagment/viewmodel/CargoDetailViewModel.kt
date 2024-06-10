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
import com.example.warehousemanagment.model.models.cargo_folder.cargo_detail.CargoDetailRow
import com.google.gson.JsonObject
import kotlinx.coroutines.launch


class CargoDetailViewModel(application: Application, context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var cargoDetailList: MutableLiveData<List<CargoDetailRow>>
            = MutableLiveData<List<CargoDetailRow>>()


    private var tempList=ArrayList<CargoDetailRow>()

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
    fun getCargoDetailList(): MutableLiveData<List<CargoDetailRow>>  {
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
        customerName:String,
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            jsonObject.addProperty("CustomerFullName",customerName)
            repository.getCargoDetailList(baseUrl,jsonObject,page, rows, sort, asc,cookie)
                .subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.cargoDetailRows.isNotEmpty())
                {
                    tempList.addAll(it.cargoDetailRows)
                    cargoDetailList.value= tempList

                }
                cargoCount.value=it.total
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