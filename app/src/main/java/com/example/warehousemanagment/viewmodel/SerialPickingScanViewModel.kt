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
import com.example.warehousemanagment.model.models.picking.SerialBasePickingRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class SerialPickingScanViewModel(application: Application) : AndroidViewModel(application) {
    val repository = MyRepository()

    val serialList = MutableLiveData<List<GetPickingSerialRow>>()
    val serialCount = MutableLiveData<Int>()

    val shippingDetail = MutableLiveData<SerialBasePickingRow?>()

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

    fun getDetail() : LiveData<SerialBasePickingRow?>{
        return shippingDetail
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
        shippingAddressDetailId: String,
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
        jsonObject.addProperty("Keyword",keyword)
        jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailId)
        jsonObject.addProperty("ItemLocationID",itemLocationId)
        showSimpleProgress(true,progress)
        viewModelScope.launch {
            repository.getSerialBasePickingDetailSerial(url,jsonObject,page,10,sort,order,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
                        swipeLayout.isRefreshing = false
                        if (it.data.isNotEmpty()){
                            tempList.addAll(it.data)
                            serialList.value = tempList
                        }
                        if (it.shippingDetail!=null){
                            shippingDetail.value = it.shippingDetail
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
        locationCode: String,
        shippingAddressDetailId: String,
        shippingLocationId: String,
        itemLocationId: String,
        serial: String,
        cookie: String,
        context: Context,
        progress: ProgressBar,
        callback: ()->Unit
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("LocationCode",locationCode)
            addProperty("ShippingAddressDetailID",shippingAddressDetailId)
            addProperty("ShippingLocationID",shippingLocationId)
            addProperty("ItemLocationID",itemLocationId)
            addProperty("Serial",serial)
        }
        showSimpleProgress(true,progress)
        viewModelScope.launch {
            repository.scanSerialBasePickingDetailSerial(url,jsonObject,cookie).subscribe(
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