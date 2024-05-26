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
import com.example.warehousemanagment.model.models.my_cargo.my_cargo.MyCargoRow
import com.google.gson.JsonObject
import kotlinx.coroutines.launch


class MyCargoViewModel(application: Application, context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var cargoList: MutableLiveData<List<MyCargoRow>>
    = MutableLiveData<List<MyCargoRow>>()

    private var tempList=ArrayList<MyCargoRow>()

    private var cargoCount=MutableLiveData<Int>()


    fun clearReceiveList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            cargoList.value = emptyList()
        }

    }
    fun getCargoCount(): MutableLiveData<Int> {
        return cargoCount
    }
    fun getCargoList(): MutableLiveData<List<MyCargoRow>>  {
        return cargoList
    }



    fun setCargoList(
        baseUrl:String,
        cookie: String,
        keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.getMyCargoList(baseUrl,jsonObject,page, rows, sort, asc,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.rows.isNotEmpty())
                {
                    tempList.addAll(it.rows)
                    cargoList.value= tempList

                }
                cargoCount.value=it.total
                log("myCargo",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"myCargo", context )
            } ).let {  }
        }
    }


}