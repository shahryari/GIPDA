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
import com.example.warehousemanagment.model.models.LocationProductSerialRow
import com.example.warehousemanagment.model.models.serial_transfer.SerialTransferProductRow
import com.example.warehousemanagment.model.models.transfer_task.DestinyLocationTransfer
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class
SerialTransferViewModel(application: Application) : AndroidViewModel(application) {

    val repository = MyRepository()

    private val transferProducts = MutableLiveData<List<SerialTransferProductRow>>()
    val tempList = ArrayList<SerialTransferProductRow>()
    private val serials = MutableLiveData<List<LocationProductSerialRow>>()
    private val normalSerials = MutableLiveData<List<String>>()
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

    fun getSerials() : LiveData<List<LocationProductSerialRow>>{
        return serials
    }

    fun getNormalSerials() : LiveData<List<String>> {
        return normalSerials
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

    fun clearSerials() {
        tempSerials.clear()
        normalSerials.value = tempSerials
        serials.value = emptyList()
    }

    fun getSerialTransferProducts(
        baseUrl: String,
        locationCode: String,
        productName: String,
        page: Int,
        sort: String,
        order: String,
        cookie: String,
        context: Context,
        progressBar: ProgressBar,
        swipeRefresh: SwipeRefreshLayout
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("LocationCode",locationCode)
        jsonObject.addProperty("ProductName",productName)
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

    fun deleteSerial(serial: LocationProductSerialRow) {
        serials.value = serials.value?.map {
            if (it.serialID.isNotEmpty()){
                if (it.serialID == serial.serialID) it.copy(isScanned = false)
                else it
            } else {
                if (it.serialNumber == serial.serialNumber) it.copy(isScanned = false)
                else it
            }
        }
        tempSerials.remove(serial.serialNumber)
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
                { res ->
                    if(res.isSucceed){
                        if (serials.value?.any { it.serialNumber == serialNumber.trim() } == true){

                            serials.value = serials.value?.map {
                                if (it.serialNumber == serialNumber.trim()) it.copy(isScanned = true)
                                else it
                            }
                        } else {
                            serials.value = (serials.value?:emptyList()) + LocationProductSerialRow("",serialNumber, isScanned = true)
                        }
                        tempSerials.add(serialNumber)
                        onSuccess()
                    } else {
                        onError()
                        toast(res.messages.first(),context)
                    }
                },
                {
                    onError()
                    showErrorMsg(it,"location transfer",context)
                }
            )
        }
    }

    fun addSerialNormal(
        serial: String,
        context: Context,
        onSuccess: ()-> Unit
    ) {
        if (serial.isEmpty()) return
        if (tempSerials.contains(serial)){
            toast("Serial already exist", context = context)
            return
        }
        tempSerials.add(serial)
        normalSerials.value = tempSerials
        onSuccess()
    }

    fun deleteSerialNormal(
        serial: String
    ) {
        tempSerials.remove(serial)
        normalSerials.value = tempSerials
    }

    fun getSerials(
        baseUrl: String,
        locationProductId: String,
        cookie: String,
        context: Context,
        onSuccess: ()->Unit,
        onError: ()->Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("LocationProductID",locationProductId)
        viewModelScope.launch {
            repository.getLocationProductSerials(
                baseUrl,jsonObject,cookie
            ).subscribe(
                {
                    if (it.rows?.isNotEmpty() == true){

                        serials.value = it.rows!!
                    }
                    onSuccess()
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
        quantity: Double?,
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
        jsonObject.addProperty("Quantity",quantity?.toInt())
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