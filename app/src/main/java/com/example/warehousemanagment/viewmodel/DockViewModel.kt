package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.dock.DockRow
import com.example.warehousemanagment.model.models.dock.SetUseDockModel
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class DockViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MyRepository()
    private val dockList = MutableLiveData<List<DockRow>>()
    private val dockCount = MutableLiveData<Int>()

    private val useDockModel = MutableLiveData<SetUseDockModel>()
    private val tempList = arrayListOf<DockRow>()

    fun clearList(){
        if (tempList.size!=0)
        {
            tempList.clear()
            dockList.value = emptyList()
        }

    }

    fun getDockList() : LiveData<List<DockRow>> {
        return dockList
    }

    fun getDockCount() : LiveData<Int> {
        return dockCount
    }

    fun getUseDock() : LiveData<SetUseDockModel>{
        return useDockModel
    }

    fun setUseDock(
        context: Context,
        baseUrl: String,
        dockId: String,
        useDock: Boolean,
        cookie: String
    ){
        viewModelScope.launch {
            val jsonObject = JsonObject()
            jsonObject.addProperty("DockID",dockId)
            jsonObject.addProperty("UseDock",useDock)
            repository.setUseDock(
                baseUrl,jsonObject,cookie
            ).subscribe(
                {
                    useDockModel.value = it
                },
                {
                    showErrorMsg(it,"docks",context)
                }
            )
        }
    }

    fun setDockList(
        context: Context,
        baseUrl: String,
        keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiUtils.Keyword,keyword)
        viewModelScope.launch {
            showSimpleProgress(true,progressBar)
            repository.getDocks(
                baseUrl,
                jsonObject,
                page,
                rows,
                sort,
                order,
                cookie
            ).subscribe(
                {
                    showSimpleProgress(false,progressBar)
                    swipeLayout.isRefreshing = false
                    if (it.rows.isNotEmpty()){
                        tempList.addAll(it.rows)
                        dockList.value = tempList
                    }
                    dockCount.value = it.total
                    log("dockList",it.toString())
                },
                {

                    swipeLayout.isRefreshing=false
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"cargoList", context )
                },
            ).let {  }
        }
    }
}