package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.dock_assign.DockListOnShippingRow
import com.example.warehousemanagment.model.models.dock_assign.ShippingListOnDockRow
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class DockAssignViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MyRepository()

    private val shippingList = MutableLiveData<List<ShippingListOnDockRow>>()
    var tempList = ArrayList<ShippingListOnDockRow>()
    private val dockList = MutableLiveData<List<DockListOnShippingRow>>()

    private val shippingCount = MutableLiveData<Int>()

    fun getShippingList() : LiveData<List<ShippingListOnDockRow>> {
        return shippingList
    }

    fun getDockList() : LiveData<List<DockListOnShippingRow>> {
        return dockList
    }

    fun getShippingCount() : LiveData<Int> {
        return shippingCount
    }

    fun clearList(){
        tempList.clear()
        shippingList.value = tempList
    }

    fun clearDockList() {
        dockList.value = emptyList()
    }


    fun setShipping(
        baseUrl: String,
        keyword: String,
        cookie: String,
        context: Context,
        progressBar: ProgressBar
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("Keyword",keyword)
        showSimpleProgress(true,progressBar)

        viewModelScope.launch {
            repository.getShippingListOnDock(
                baseUrl,jsonObject,1,10,"CreatedOn","desc",cookie
            ).subscribe(
                {
                    showSimpleProgress(false,progressBar)
                    if(it.rows!=null){
                        tempList.add(it.rows)
                        shippingList.value = tempList
                    }
                    shippingCount.value = it.total
                },
                {
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"shippingListOnDock",context)
                }
            )
        }
    }

    fun setDockList(
        baseUrl: String,
        keyword: String,
        warehouseId: String,
        cookie: String,
        context: Context,
        progressBar: ProgressBar
    ) {
        showSimpleProgress(true,progressBar)
        val jsonObject = JsonObject()
        jsonObject.addProperty("Keyword",keyword)
        jsonObject.addProperty("WarehouseID",warehouseId)
        viewModelScope.launch {
            repository.getDockListOnShippingAddress(
                baseUrl,jsonObject,1,100,"CreatedOn","desc",cookie
            ).subscribe(
                {
                    showSimpleProgress(false,progressBar)
                    dockList.value = it.rows
                },
                {
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"dockListOnShipping",context)
                }
            )
        }
    }

    fun assignDock(
        baseUrl: String,
        dockId: String,
        shippingAddressId: String,
        cookie: String,
        context: Context,
        onSuccess: ()->Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("DockID",dockId)
        jsonObject.addProperty("ShippingAddressID",shippingAddressId)

        viewModelScope.launch {
            repository.dockAssignShippingAddress(
                baseUrl,jsonObject,cookie
            ).subscribe(
                {
                    onSuccess()
                },
                {
                    showErrorMsg(it,"Dock Assign",context)
                }
            )
        }
    }
}