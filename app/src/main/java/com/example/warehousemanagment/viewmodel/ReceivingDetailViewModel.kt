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
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.data.MySharedPref
import com.example.warehousemanagment.model.models.receive.add_detail_serial.AddReceivingDetailSerialModel
import com.example.warehousemanagment.model.models.receive.confirm.ConfirmReceiveDetailModel
import com.example.warehousemanagment.model.models.receive.count.ReceiveDetailCountModel
import com.example.warehousemanagment.model.models.receive.receiveDetail.ReceiveDetailRow
import com.example.warehousemanagment.model.models.receive.receiving_detail_serials.ReceivingDetailSerialModel
import com.example.warehousemanagment.model.models.receive.remove_serial.RemoveSerialModel
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch


class ReceivingDetailViewModel( application: Application,context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var receiveDetails: MutableLiveData<List<ReceiveDetailRow>> =
        MutableLiveData<List<ReceiveDetailRow>>()
    private var temptReceiveDetail=ArrayList<ReceiveDetailRow>()

    private var confirmReceiveDetail: MutableLiveData<ConfirmReceiveDetailModel> =
        MutableLiveData<ConfirmReceiveDetailModel>()

    private var serials: MutableLiveData<List<ReceivingDetailSerialModel>> =
        MutableLiveData<List<ReceivingDetailSerialModel>>()

    private var addSerialResult : MutableLiveData<AddReceivingDetailSerialModel> =
        MutableLiveData<AddReceivingDetailSerialModel>()

    private var removeSerialModel:MutableLiveData<RemoveSerialModel>
        =MutableLiveData<RemoveSerialModel>()

    private var receiveDetailCount:MutableLiveData<ReceiveDetailCountModel>
            =MutableLiveData<ReceiveDetailCountModel>()
    private var detailCount=MutableLiveData<Int>()

    private var scanQuantityStatus=MutableLiveData<Boolean>();

    private var disposable:CompositeDisposable= CompositeDisposable()
    fun dispose() { disposable.clear() }

    var pref:MySharedPref

    init {
        pref= MySharedPref(context)
    }


    fun clearReceiveDetail(){
        temptReceiveDetail.clear()
        receiveDetails.value=temptReceiveDetail
    }
    fun getDetailCount(): MutableLiveData<Int> {
        return detailCount
    }

    fun getScanCountStatus():MutableLiveData<Boolean>{
        return scanQuantityStatus
    }

    fun getReceiveDetailCount(): MutableLiveData<ReceiveDetailCountModel> {
        return receiveDetailCount
    }
    fun getRemoveSerialModel(): LiveData<RemoveSerialModel> {
        return removeSerialModel.distinctUntilChanged()
    }
    fun getReceiveDetailsList(): MutableLiveData<List<ReceiveDetailRow>> {
        return receiveDetails
    }

    fun getSerialsList(): LiveData<List<ReceivingDetailSerialModel>> {
        return serials.distinctUntilChanged()
    }
    fun getAddingSerialResult(): LiveData<AddReceivingDetailSerialModel> {
        return addSerialResult.distinctUntilChanged()
    }
    fun getConfirmReceiveDetail(): MutableLiveData<ConfirmReceiveDetailModel> {
        return confirmReceiveDetail
    }

    fun confirmReceiveDetailSerial(
        baseUrl:String,receivingDetailId: String,quantity: Int,workerTaskId: String, cookie: String,
            progress: ProgressBar)
    {

        viewModelScope.launch()
        {

            showSimpleProgress(true,progress)
            val jsonObject=JsonObject()
            jsonObject.addProperty("EntityResourceID",receivingDetailId)
            jsonObject.addProperty("Quantity",quantity)
            jsonObject.addProperty("WorkerTaskID",workerTaskId)
            repository.confirmReceiveDetail(baseUrl,jsonObject, cookie)
                .subscribe({
                    showSimpleProgress(false,progress)
                    confirmReceiveDetail.postValue(it)
                    log("ReceivingDetailSerialConfirm",it.toString())
                },{

                    showSimpleProgress(false,progress)
                    showErrorMsg(it,"ReceivingDetailSerialConfirm",context)
                },{

                },{
                    disposable.add(it)
                }).let {  }

        }

    }

    fun countReceiveDetail(
        baseUrl:String,receivingDetailID:String,quantity:Int
                     ,workerTaskId:String,cookie: String,progress:ProgressBar)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("EntityResourceID",receivingDetailID)
            jsonObject.addProperty("Quantity",quantity)
            jsonObject.addProperty("WorkerTaskID",workerTaskId)
            showSimpleProgress(true,progress)
            repository.countReceiveDetail(baseUrl,jsonObject, cookie).subscribe({
                showSimpleProgress(false,progress)
                receiveDetailCount.postValue(it)
                log("receiveDetailCount",it.toString())
                toast(context.getString(R.string.itsDone),context)
            },
                {
                    showSimpleProgress(false,progress)
                    showErrorMsg(it,"receiveDetailCount",context)

                },{

                },{
                    disposable.add(it)
                }).let {  }
        }
    }

    fun removeSerial(
        baseUrl:String,receivingDetailID:String,serialNumber:String
                           ,productId:String,cookie: String,progress:ProgressBar)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("EntityResourceID",receivingDetailID)
            jsonObject.addProperty("ItemSerialID",serialNumber)
            jsonObject.addProperty("GoodID",productId)
            showSimpleProgress(true,progress)
            repository.removeSerial(baseUrl,jsonObject, cookie).subscribe({
                showSimpleProgress(false,progress)
                    removeSerialModel.postValue(it)
                log("removeSerial",it.toString())
            },
                {
                    showSimpleProgress(false,progress)
                    showErrorMsg(it,"removeSerial",context)

                },{

                },{
//                    disposable.add(it)
                }).let {  }
        }
    }

    fun addReceivingSerial(
        baseUrl:String,entityResourceID:String,serialNumber:String
                                 ,goodID:String,cookie: String,progress:ProgressBar,
                                refreshSerials:(model:AddReceivingDetailSerialModel)->Unit)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("EntityResourceID",entityResourceID)
            jsonObject.addProperty("SerialNumber",serialNumber)
            jsonObject.addProperty("GoodID",goodID)
            showSimpleProgress(true,progress)
            repository.addReceiveDetailSerial(baseUrl,jsonObject, cookie).subscribe({
                showSimpleProgress(false,progress)
                refreshSerials(it)
                addSerialResult.value=it

                log("addReceiving",it.toString())
            },
            {
                showSimpleProgress(false,progress)

                showErrorMsg(it,"addReceiving",context)


            }, ).let {  }
        }
    }



    fun setSerialList(
        baseUrl:String,receivingDetailId: String, cookie: String,progress:ProgressBar)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.ReceivingDetailID,receivingDetailId)
            showSimpleProgress(true,progress)
            repository.getReceiveDetailSerialList(baseUrl,jsonObject, cookie)

                .subscribe({
                    showSimpleProgress(false,progress)
                    serials.value=(it)
                    if(it.isEmpty())
                    {
                        scanQuantityStatus.value=false
                    }
                    else if (it.size<=pref.getAtLeastCountForReceivingQuantity() )
                    {
                        scanQuantityStatus.value=true
                        it.forEach {
                            if(it.isExcel==false)
                                scanQuantityStatus.value=false
                        }
                    }else{
                        scanQuantityStatus.value=false
                    }



                    log("receivingSerialDetail",it.toString())
                },{
                    scanQuantityStatus.value=false
                    showSimpleProgress(false,progress)

                    showErrorMsg(it,"receivingSerialDetail",context)
                },{},{
                    disposable.add(it)
                }).let {  }
        }



    }

    fun setReceiveDetailsList(
        baseUrl:String,
        receivingId: String, cookie: String, keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.ReceivingID,receivingId)
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.getReceiveDetailList(baseUrl,jsonObject,page, rows, sort, asc, cookie)
                .subscribe({

                    swipeLayout.isRefreshing=false
                    showSimpleProgress(false,progressBar)
                    if (it.receiveDetailRows.size>0){
                        temptReceiveDetail.addAll(it.receiveDetailRows)
                        receiveDetails.value=temptReceiveDetail
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