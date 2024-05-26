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
import com.example.warehousemanagment.model.models.inventory.inventory.InventoryModifedModel
import com.example.warehousemanagment.model.models.inventory.inventory.InventoryModifyRow
import com.example.warehousemanagment.model.models.receive.receiving.RowReceivingModel
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import org.json.JSONObject

class InventoryModifiedTaskViewModel(application: Application,context: Context):AndroidViewModel(application)
{
    private val repository= MyRepository()
    private var context: Context=context

    private var inventoryTaskList=MutableLiveData<List<InventoryModifyRow>>()
    private var tempList=ArrayList<InventoryModifyRow>()
    private var completeModifierTaskModel=MutableLiveData<CompleteInventoryModifyModel>()

    private var inventoryCount=MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun getCompleteModifierResult(): MutableLiveData<CompleteInventoryModifyModel> {
        return completeModifierTaskModel
    }

    fun clearReceiveList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            inventoryTaskList.value= tempList
        }
    }
    fun getInventoryCount(): MutableLiveData<Int> {
        return inventoryCount
    }

    fun getInventoryModifeidTask(): MutableLiveData<List<InventoryModifyRow>> {
        return inventoryTaskList
    }

    fun completeInventoryModifiedTask(
        baseUrl:String,locationTransferId:String,
                                      cookie:String,progressBar: ProgressBar,refresh:()->Unit)
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty("LocationTransferID",locationTransferId)
            repository.completeInventoryModify(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        refresh()
                        showSimpleProgress(false,progressBar)
                        log("completeInventoryModifiedTask", it.toString())
                    },
                    {
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "completeInventoryModifiedTask", context)
                    },
                ).let { }
        }


    }


    fun setInventoryModifiedTask(
        baseUrl:String,
        keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        cookie: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {

            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.inventoryModifiedTask(baseUrl,jsonObject, page, rows, sort, asc, cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.inventoryModifyRows.size>0)
                        {
                            tempList.addAll(it.inventoryModifyRows)
                            inventoryTaskList.value=tempList
                        }
                        inventoryCount.value=it.total
                        log("InventoryModifiedTask", it.toString())
                    },{
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it,"InventoryModifiedTask",context)
                    },{},{disposable.add(it)}).let {  }
        }
    }


}