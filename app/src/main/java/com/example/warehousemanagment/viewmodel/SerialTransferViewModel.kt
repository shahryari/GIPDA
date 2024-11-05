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
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.serial_transfer.SerialTransferProductRow
import com.example.warehousemanagment.model.models.transfer_task.DestinyLocationTransfer
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class SerialTransferViewModel(application: Application) : AndroidViewModel(application) {

    val repository = MyRepository()

    private val transferProducts = MutableLiveData<List<SerialTransferProductRow>>()
    val tempList = ArrayList<SerialTransferProductRow>()
    private val serials = MutableLiveData<List<String>>()
    val tempSerials = ArrayList<String>()
    private var destinyLocationTransfer= MutableLiveData<List<DestinyLocationTransfer>>()


    val productSize = MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }


    fun getTransferProducts() : LiveData<List<SerialTransferProductRow>> {
        return transferProducts
    }

    fun getProductSize() : LiveData<Int> {
        return productSize
    }

    fun getSerials() : LiveData<List<String>>{
        return serials
    }

    fun getDestinyLocationTransfer(): MutableLiveData<List<DestinyLocationTransfer>> {
        return destinyLocationTransfer
    }


    fun clearList() {
        if (tempList.size!=0)
        {
            tempList.clear()
            transferProducts.value= tempList
        }
    }

    fun getSerialTransferProducts(
        baseUrl: String,
        keyword: String,
        page: Int,
        sort: String,
        order: String,
        cookie: String,
        context: Context,
        progressBar: ProgressBar,
        swipeRefresh: SwipeRefreshLayout
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("Keyword",keyword)
        viewModelScope.launch {
            showSimpleProgress(true,progressBar)
            repository.getSerialBaseLocationProduct(
                baseUrl,jsonObject,page,Utils.ROWS,sort,order,cookie
            ).subscribe(
                {
                    swipeRefresh.isRefreshing = false
                    showSimpleProgress(false,progressBar)
                    if (it.rows.isNotEmpty()){
                        tempList.addAll(it.rows)
                        transferProducts.value = tempList
                    }
                    productSize.value = it.total
                },
                {
                    swipeRefresh.isRefreshing = false
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"transfer location",context)
                }
            )
        }
    }

    fun setClearList()
    {
        destinyLocationTransfer.value= emptyList()
    }
    fun setDestinyLocatoinTransfer(
        baseUrl:String,
        model: SerialTransferProductRow,
        cookie:String,
        context: Context,
        progressBar: ProgressBar,
        searchLocationDestiny: String)
    {
        showSimpleProgress(true,progressBar)
        val jsonObject= JsonObject()
        jsonObject.addProperty("LocationCode",searchLocationDestiny)
        jsonObject.addProperty("WarehouseID",model.warehouseCode)
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

    fun checkSerial(
        baseUrl: String,
        locationProductId: String,
        serialNumber: String,
        cookie: String,
        context: Context,
        onSuccess: ()->Unit,
        onError: ()->Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("LocationProductID",locationProductId)
        jsonObject.addProperty("SerialNumber",serialNumber)
        viewModelScope.launch {
            repository.checkLocationTransferSerial(
                baseUrl,jsonObject,cookie
            ).subscribe(
                {
                    if(it.isSucceed){
                        tempSerials.add(serialNumber)
                        serials.value = tempSerials
                        onSuccess()
                    } else {
                        onError()
                        toast(it.messages.first(),context)
                    }
                },
                {
                    onError()
                    showErrorMsg(it,"location transfer",context)
                }
            )
        }
    }

    fun transferSerials(
        baseUrl: String,
        locationProductId: String,
        serials: ArrayList<String>,
        destinationLocation: String,
        cookie: String,
        context: Context,
        onSuccess: () -> Unit,
        onError: ()->Unit
    ) {

        val jsonArray = JsonArray()
        for (serial in serials){
            jsonArray.add(serial)
        }
        val jsonObject = JsonObject()
        jsonObject.addProperty("LocationProductID",locationProductId)
        jsonObject.add("Serials",jsonArray)
        jsonObject.addProperty("DestinationLocation",destinationLocation)

        viewModelScope.launch {
            repository.serialBaseLocationTransfer(
                baseUrl,jsonObject,cookie
            ).subscribe(
                {
                    if (it.isSucceed){
                        onSuccess()
                    } else {
                        onError()
                        toast(it.messages.first(),context)
                    }
                },
                {
                    onError()
                    showErrorMsg(it,"location transfer",context)
                }
            )
        }
    }
}