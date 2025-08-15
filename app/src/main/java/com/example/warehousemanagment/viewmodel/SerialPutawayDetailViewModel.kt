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
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.putaway.serial_putaway.MySerialReceiptDetailRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class SerialPutawayDetailViewModel(application: Application) : AndroidViewModel(application){
    val repository = MyRepository()

    private var detailList: MutableLiveData<List<MySerialReceiptDetailRow>>
            = MutableLiveData<List<MySerialReceiptDetailRow>>()

    private var tempList=ArrayList<MySerialReceiptDetailRow>()

    private var detailCount= MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearDetailList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            detailList.value= emptyList()
        }

    }
    fun getDetailCount(): MutableLiveData<Int> {
        return detailCount
    }
    fun getDetailList(): MutableLiveData<List<MySerialReceiptDetailRow>> {
        return detailList
    }
    fun setDetailList(
        baseUrl:String,
        cookie: String,
        keyword: String,
        receiptId: String,
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
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            jsonObject.addProperty("ReceiptID",receiptId)
            repository.mySerialReceiptDetailOnPutaway(baseUrl,jsonObject,page, rows, sort, asc,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.rows.isNotEmpty())
                {
                    tempList.addAll(it.rows)
                    detailList.value=tempList

                }
                detailCount.value=it.total
                log("serialPutawayDetail",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"serialPutawayDetail",context)
            },{},{disposable.add(it)}).let {  }
        }
    }

    fun receiptDetailAutoScanSerial(
        baseUrl: String,
        receiptDetailID: String,
        cookie: String,
        context: Context,
        progressBar: ProgressBar,
        onComplete: ()-> Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("ReceiptDetailID",receiptDetailID)
        showSimpleProgress(true,progressBar)
        repository.receiptDetailAutoScanSerial(
            baseUrl,jsonObject, cookie
        ).subscribe(
            {
                showSimpleProgress(false,progressBar)
                toast("Assigned Successfully",context)
                onComplete()
            },
            {
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"autoScanReceiptSerial",context)
                onComplete()
            }
        ).let {  }
    }
}