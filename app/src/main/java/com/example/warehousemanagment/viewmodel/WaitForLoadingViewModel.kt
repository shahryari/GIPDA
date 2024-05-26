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
import com.example.warehousemanagment.model.models.inventory.CompleteInventoryModifyModel
import com.example.warehousemanagment.model.models.inventory.inventory.InventoryModifyRow
import com.example.warehousemanagment.model.models.wait_to_load.TruckLoadingAssignModel
import com.example.warehousemanagment.model.models.wait_to_load.wait_truck.WaitTruckLoadingModel
import com.example.warehousemanagment.model.models.wait_to_load.wait_truck.WaitTruckLoadingRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class WaitForLoadingViewModel(application: Application,context:Context): AndroidViewModel(application)
{
    private val repository= MyRepository()
    private var context: Context=context
    private var loadingAssignModel=MutableLiveData<TruckLoadingAssignModel>()

    private var waitForLoadingList= MutableLiveData<List<WaitTruckLoadingRow>>()

    private var tempList=ArrayList<WaitTruckLoadingRow>()
    private var waitForTruckCount=MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }


    fun getLoadingAssignModel(): MutableLiveData<TruckLoadingAssignModel> {
        return loadingAssignModel
    }
    fun getWaitForLoadingList(): MutableLiveData<List<WaitTruckLoadingRow>>
    {
        return waitForLoadingList
    }

    fun clearReceiveList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            waitForLoadingList.value= tempList
        }
    }
    fun getWaifForLoadingCount(): MutableLiveData<Int> {
        return waitForTruckCount
    }

    fun setWaitForLoadingList(
        baseUrl:String,
        keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject= JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.waitTruckLoading(baseUrl,jsonObject, page, rows, sort, order, cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.waitTruckRows.size>0)
                        {
                            tempList.addAll(it.waitTruckRows)
                             waitForLoadingList.value=tempList
                        }
                        waitForTruckCount.value=it.total
                        log("WaitForLoadingList", it.toString())
                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false, progressBar)
                        showErrorMsg(it, "WaitForLoadingList", context)
                    },{},{disposable.add(it)}).let {  }
        }
    }
    fun setLoadingAssign(
        baseUrl:String,shippingAddressId:String, cookie: String, progress: ProgressBar)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)

            showSimpleProgress(true,progress)
            repository.truckLoadingAssign(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
                        loadingAssignModel.value=it
                        log("WaitForLoadingList", it.toString())
                    },
                    {
                        showSimpleProgress(false,progress)
                        showErrorMsg(it, "WaitForLoadingList", context)
                    },
                ).let { }
        }
    }

}