package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.ErrorModel
import com.example.warehousemanagment.model.models.putaway.complete.CompletePutawayModel
import com.example.warehousemanagment.model.models.putaway.truck_detail.PutawayTruckDetailModel
import com.example.warehousemanagment.model.models.putaway.truck_detail.PutawayDetailRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class PutAwayDetailViewModel(application: Application,context: Context): AndroidViewModel(application)
{
    private val repository= MyRepository( )
    private var context: Context=context
    private var putawayDetail: MutableLiveData<List<PutawayDetailRow>> =
        MutableLiveData<List<PutawayDetailRow>>()

    private var completePutawayModel: MutableLiveData<CompletePutawayModel> =
        MutableLiveData<CompletePutawayModel>()

    private var completePutawayModelError: MutableLiveData<ErrorModel> =
        MutableLiveData<ErrorModel>()

    private var tempList=ArrayList<PutawayDetailRow>()
    private var putawayCount=MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearPutawayDetail()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            putawayDetail.value=tempList
        }

    }
    fun getPutawayCount(): MutableLiveData<Int> {
        return putawayCount
    }



    fun getCompletePutawayError(): LiveData<ErrorModel> {
        return completePutawayModelError.distinctUntilChanged()
    }

    fun getCompletePutaway(): MutableLiveData<CompletePutawayModel> {
        return completePutawayModel
    }
    fun getPutawayDetail(): MutableLiveData<List<PutawayDetailRow>> {
        return putawayDetail
    }
    fun completePutaway(
        baseUrl:String,itemLocationId:String,receiptDetailId:String,cookie: String,progress: ProgressBar)
    {
        viewModelScope.launch()
        {
            val jsonObject= JsonObject()
            jsonObject.addProperty("ItemLocationID",itemLocationId)
            jsonObject.addProperty("ReceiptDetailID",receiptDetailId)
            showSimpleProgress(true,progress)
            repository.completePutawayModel(baseUrl,jsonObject, cookie).subscribe({
                completePutawayModel.value=(it)
                log(" completePutaway",it.toString())
                showSimpleProgress(false,progress)
            },
                {
                    showSimpleProgress(false,progress)
                    logErr("putawayDetail",it.toString())
                    completePutawayModelError.postValue(ErrorModel("",false,"", Utils.WRONG_NUM))
                }).let {  }
        }
    }

    fun setPutawayDetial(
        baseUrl:String,
        receivingDetailID: String, keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        asc: String, cookie: String, progressBar: ProgressBar, swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject= JsonObject()
            jsonObject.addProperty("ReceivingID",receivingDetailID)
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.putawayTruckDetail(baseUrl,jsonObject,page, rows, sort, asc, cookie).subscribe({
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                log("putawayCount",putawayDetail.value?.size.toString())
                if (it.putawayDetailRows.size!=0)
                {
                    tempList.addAll(it.putawayDetailRows)
                    putawayDetail.value=tempList
                }
                putawayCount.value=it.total
                log("putawayDetail",it.toString())
             },
                {
                    swipeLayout.isRefreshing=false
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"putawayDetail",context)

                },{},{disposable.add(it)}).let {  }
        }
    }

}

