package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.putaway.serial_putaway.ReceiptDetailLocationRow
import com.example.warehousemanagment.model.models.putaway.serial_putaway.ReceiptSerialRow
import com.example.warehousemanagment.model.models.putaway.serial_putaway.SerialPutawayAssignModel
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class SerialPutawayDetailLocationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository= MyRepository( )
    private var locations: MutableLiveData<List<ReceiptDetailLocationRow>> =
        MutableLiveData<List<ReceiptDetailLocationRow>>()
    private var tempLocations=ArrayList<ReceiptDetailLocationRow>()

    private var serials: MutableLiveData<List<ReceiptSerialRow>> =
        MutableLiveData<List<ReceiptSerialRow>>()

    private var serialCount = MutableLiveData<Int>()

    private var addSerialResult : MutableLiveData<SerialPutawayAssignModel> =
        MutableLiveData<SerialPutawayAssignModel>()

    private var removeSerialModel: MutableLiveData<SerialPutawayAssignModel>
            = MutableLiveData<SerialPutawayAssignModel>()

    private var detailCount= MutableLiveData<Int>()


    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }



    fun clearLocations(){
        tempLocations.clear()
        locations.value=tempLocations
    }
    fun getDetailCount(): MutableLiveData<Int> {
        return detailCount
    }

    fun getRemoveSerialModel(): LiveData<SerialPutawayAssignModel> {
        return removeSerialModel.distinctUntilChanged()
    }
    fun getLocations(): MutableLiveData<List<ReceiptDetailLocationRow>> {
        return locations
    }

    fun getSerialsList(): LiveData<List<ReceiptSerialRow>> {
        return serials.distinctUntilChanged()
    }
    fun getSerialCount(): MutableLiveData<Int> {
        return serialCount
    }


    fun removeSerial(
        baseUrl:String,
        itemSerialId: String,
        cookie: String,
        context: Context,
        progress: ProgressBar
    )
    {
        viewModelScope.launch()
        {
            val jsonObject= JsonObject()
            jsonObject.addProperty("ItemSerialIDs",itemSerialId)
            showSimpleProgress(true,progress)
            repository.receiptDetailSerialRemove(baseUrl,jsonObject, cookie).subscribe(
                {
                    showSimpleProgress(false,progress)
                    removeSerialModel.postValue(it)
                    log("removeSerial",it.toString())
                },
                {
                    showSimpleProgress(false,progress)
                    showErrorMsg(it,"removeSerial",context)

                }
            )
        }
    }

    fun scanSerial(
        baseUrl:String,
        locationCode:String,
        serialNumber:String,
        receiptDetailId:String,
        cookie: String,
        context: Context,
        progress: ProgressBar,
        refreshSerials:()->Unit)
    {
        viewModelScope.launch()
        {
            val jsonObject= JsonObject()
            jsonObject.addProperty("LocationCode",locationCode)
            jsonObject.addProperty("Serial",serialNumber)
            jsonObject.addProperty("ReceiptDetailID",receiptDetailId)
            showSimpleProgress(true,progress)
            repository.receiptDetailScanSerial(baseUrl,jsonObject, cookie).subscribe({
                showSimpleProgress(false,progress)
                refreshSerials()
                addSerialResult.value=it

                log("scanSerial",it.toString())
            },
                {
                    showSimpleProgress(false,progress)

                    showErrorMsg(it,"scanSerial",context)


                }, ).let {  }
        }
    }



    fun setSerialList(
        baseUrl:String,
        receiptDetailId: String,
        locationCode: String,
        cookie: String,
        context: Context,
        progress: ProgressBar
    )
    {
        viewModelScope.launch()
        {
            val jsonObject= JsonObject()
            jsonObject.addProperty("ReceiptDetailID",receiptDetailId)
            if(locationCode.isNotEmpty())jsonObject.addProperty("LocationCode",locationCode)
            showSimpleProgress(true,progress)
            repository.receiptDetailSerial(
                baseUrl,
                jsonObject,
                1,
                1000,
                "CreatedOn",
                "desc",
                cookie
            )

                .subscribe({
                    showSimpleProgress(false,progress)
                    serials.postValue(it.rows)
//                    if(it.size==0)
//                    {
//                        scanQuantityStatus.value=false
//                    }
//                    else if (it.size<=pref.getAtLeastCountForReceivingQuantity() )
//                    {
//                        scanQuantityStatus.value=true
//                        it.forEach {
//                            if(it.isExcel==false)
//                                scanQuantityStatus.value=false
//                        }
//                    }else{
//                        scanQuantityStatus.value=false
//                    }
                    serialCount.value = it.total



                    log("receivingSerialDetail",it.toString())
                },{
//                    scanQuantityStatus.value=false
                    showSimpleProgress(false,progress)

                    showErrorMsg(it,"receivingSerialDetail",context)
                },{},{
                    disposable.add(it)
                }).let {  }
        }



    }

    fun setLocations(
        baseUrl:String,
        receivingId: String, cookie: String, keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        context: Context,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject= JsonObject()
            jsonObject.addProperty("ReceiptDetailID",receivingId)
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.receiptDetailLocation(baseUrl,jsonObject,page, rows, sort, asc, cookie)
                .subscribe({

                    swipeLayout.isRefreshing=false
                    showSimpleProgress(false,progressBar)
                    if (it.rows.isNotEmpty()){
                        tempLocations.addAll(it.rows)
                        locations.value=tempLocations
                    }
                    detailCount.value=it.total

                    log("receivingDetail",it.toString())
                },{
                    swipeLayout.isRefreshing=false
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"receivingDetail",context)
                },{

                },{
                    disposable.add(it)
                }).let {  }

        }

    }

}