package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.picking.GetPickingSerialRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class SerialPickingScanViewModel(application: Application) : AndroidViewModel(application) {
    val repository = MyRepository()

    val serialList = MutableLiveData<List<GetPickingSerialRow>>()
    val serialCount = MutableLiveData<Int>()

    private var tempList=ArrayList<GetPickingSerialRow>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearPickingList(){
        if (tempList.size!=0)
        {
            tempList.clear()
            serialList.value=tempList
        }

    }


    fun getSerialList() : LiveData<List<GetPickingSerialRow>> {
        return serialList
    }

    fun getSerialCount() : LiveData<Int> {
        return serialCount
    }

    fun setSerialList(
        url: String,
        keyword: String,
        itemLocationId: String,
        page: Int,
        sort: String,
        order: String,
        cookie: String,
        context: Context,
        progress: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("ItemLocationID",itemLocationId)
        jsonObject.addProperty("Keyword",keyword)
        showSimpleProgress(true,progress)
        viewModelScope.launch {
            repository.getPickingSerials(url,jsonObject,page,sort,order,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
                        swipeLayout.isRefreshing = false
                        if (it.isNotEmpty()){
                            tempList.addAll(it)
                            serialList.value = tempList
                        }
                    },
                    {

                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progress)
                        showErrorMsg(it,"picking serial scan",context)
                    }
                )
        }
    }


    fun scanPickingSerial(
        url: String,
        itemLocationId: String,
        serial: String,
        cookie: String,
        context: Context,
        progress: ProgressBar,
        callback: ()->Unit
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("ItemLocationID",itemLocationId)
            addProperty("Serial",serial)
        }
        showSimpleProgress(true,progress)
        viewModelScope.launch {
            repository.scanPickingSerial(url,jsonObject,cookie).subscribe(
                {
                    showSimpleProgress(false,progress)
                    callback()
                },
                {

                    showSimpleProgress(false,progress)
                    showErrorMsg(it,"picking serial scan",context)
                }
            )
        }
    }
}