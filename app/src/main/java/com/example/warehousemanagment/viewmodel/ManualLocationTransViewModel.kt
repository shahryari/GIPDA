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
import com.example.warehousemanagment.model.models.transfer_task.DestinyLocationTransfer
import com.example.warehousemanagment.model.models.transfer_task.source_location.SourceLocationRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class ManualLocationTransViewModel(application: Application,context: Context):
    AndroidViewModel(application)
{
    private val repository= MyRepository()
    private var context: Context=context

    private var sourceLocationTransferCount=MutableLiveData<Int>()
    private var sourceLocationTransfer= MutableLiveData<List<SourceLocationRow>>()
    private var destinyLocationTransfer= MutableLiveData<List<DestinyLocationTransfer>>()

    private var tempList=ArrayList<SourceLocationRow>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun getSourceLocationTransferCount(): MutableLiveData<Int> {
        return sourceLocationTransferCount
    }

    fun clearReportList()  {
        if (tempList.size!=0)
        {
            tempList.clear()
            sourceLocationTransfer.value=tempList
        }

    }

    fun getSourceLocationTransfer(): MutableLiveData<List<SourceLocationRow>> {
        return sourceLocationTransfer
    }

    fun getDestinyLocationTransfer(): MutableLiveData<List<DestinyLocationTransfer>> {
        return destinyLocationTransfer
    }


    fun setSubmitLocationTransfer(
        baseUrl:String, cookie:String, model: SourceLocationRow,
        destLocation:String, quantity:Int, locationCode: String,
        progressBar: ProgressBar,onError:()->Unit, refresh:()->Unit)
    {
        val jsonObject= JsonObject()
        jsonObject.addProperty("LocationFromID",model.locationID)
        jsonObject.addProperty("DestinationLocationCode",destLocation)
        jsonObject.addProperty("GoodID",model.goodID)
        jsonObject.addProperty("WarehouseID",model.warehouseID)
        jsonObject.addProperty("TransferTypeID",ApiUtils.TRANSFER_TYPE)
        jsonObject.addProperty("InvTypeID",model.invTypeID)
        jsonObject.addProperty("Quantity",quantity)
        jsonObject.addProperty("LocationProductID",model.locationProductID)

        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            repository.submitLocationTransfer(baseUrl,jsonObject,cookie)
                .subscribe(
                    {

                        refresh()
                        log("submitLocationTransfer", it.toString())
                    },
                    {
                        onError()
                        showErrorMsg(it, "submitLocationTransfer", context)
                        showSimpleProgress(false,progressBar)
                    },{},{disposable.add(it)}
                ).let { }
        }
    }
    fun setClearList()
    {
        destinyLocationTransfer.value= emptyList()
    }
    fun setDestinyLocatoinTransfer(
        baseUrl:String, model: SourceLocationRow,
        cookie:String, progressBar: ProgressBar, searchLocationDestiny: String)
    {
        showSimpleProgress(true,progressBar)
        val jsonObject= JsonObject()
        jsonObject.addProperty("LocationCode",searchLocationDestiny)
        jsonObject.addProperty("WarehouseID",model.warehouseID)
        jsonObject.addProperty("GoodID",model.goodID)
        jsonObject.addProperty("InvTypeID",model.invTypeID)
        viewModelScope.launch()
        {
            repository.destinationLocationTransfer(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progressBar)
                        destinyLocationTransfer.value=(it)
                        log("destinyLocationTransfer", it.toString())


                    },
                    {
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "destinyLocationTransfer", context)
                    },{},{disposable.add(it)}
                ).let { }
        }
    }
    fun setSourceLocationTransfer(
        baseUrl:String,
        locationCode: String,
        cookie: String,
        page:Int,
        rows:Int,
        sort:String,
        asc:String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        showSimpleProgress(true,progressBar)
        val jsonObject= JsonObject()
        jsonObject.addProperty("LocationCode",locationCode)
        viewModelScope.launch()
        {
            repository.sourceLocationTransfer(baseUrl,jsonObject,cookie,page, rows, sort, asc)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)

                        log("sourceLocationTransfer", it.toString())

                        if (it.rows.isNotEmpty()){
                            tempList.addAll(it.rows)
                            sourceLocationTransfer.value=(tempList)
                        }
                        sourceLocationTransferCount.value=it.total

                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "sourceLocationTransfer", context)
                    },{},{disposable.add(it)}
                ).let { }
        }
    }





}