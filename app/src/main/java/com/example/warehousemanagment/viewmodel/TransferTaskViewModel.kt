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
import com.example.warehousemanagment.model.models.inventory.inventory.InventoryModifyRow
import com.example.warehousemanagment.model.models.transfer_task.*
import com.example.warehousemanagment.model.models.transfer_task.location_transfer.LocationTransferRow
import com.example.warehousemanagment.model.models.transfer_task.location_transfer.LocationTransferTaskModel
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class TransferTaskViewModel(application: Application, context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository()
    private var context: Context=context
    private var locationTransferTask=MutableLiveData<List<LocationTransferRow>>()

    private var tempList=ArrayList<LocationTransferRow>()

    private var locationTransferCount=MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearReceiveList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            locationTransferTask.value= tempList
        }
    }
    fun getLocationTransferCount(): MutableLiveData<Int> {
        return locationTransferCount
    }

    fun getLocationTransferList(): MutableLiveData<List<LocationTransferRow>> {
        return locationTransferTask
    }
    fun completeLocationTransfer(
        baseUrl:String,cookie:String, model: LocationTransferRow, progress:ProgressBar,
        refresh:()->Unit)
    {
        val jsonObject=JsonObject()
        jsonObject.addProperty("LocationTransferID",model.locationTransferID)

        viewModelScope.launch()
        {
            showSimpleProgress(true,progress)
            repository.completeLocationTransfer(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
//                        completeLocationTransfer.value=it
                        refresh()
                        log("completeLocationTransfer", it.toString())
                    },
                    {
                        showSimpleProgress(false,progress)
                        showErrorMsg(it, "completeLocationTransfer", context)
                    },
                ).let { }
        }
    }


    fun setLocationTransferList(
        baseUrl:String,
        keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        cookie: String, progressBar: ProgressBar, swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.locationTransferTaskList(baseUrl,jsonObject, page, rows, sort, asc, cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.rows.size>0)
                        {
                            tempList.addAll(it.rows)
                            locationTransferTask.value=tempList
                        }
                        locationTransferCount.value=it.total


                        log("locationSourceTransfer", it.toString())

                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(true,progressBar)
                        showErrorMsg(it, "locationSourceTransfer", context)
                    },
                ).let { }
        }
    }






}