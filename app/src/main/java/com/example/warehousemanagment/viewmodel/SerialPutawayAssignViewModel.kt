package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.putaway.serial_putaway.SerialReceiptOnPutawayRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class SerialPutawayAssignViewModel(application: Application) : AndroidViewModel(application) {

    val repository = MyRepository()

    private var serialList: MutableLiveData<List<SerialReceiptOnPutawayRow>>
            = MutableLiveData<List<SerialReceiptOnPutawayRow>>()

    private var tempList=ArrayList<SerialReceiptOnPutawayRow>()

    private var serialsCount= MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearSerialList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            serialList.value= emptyList()
        }

    }
    fun getSerialCount(): MutableLiveData<Int> {
        return serialsCount
    }
    fun getSerialList(): MutableLiveData<List<SerialReceiptOnPutawayRow>> {
        return serialList
    }
    fun setSerialList(
        baseUrl:String,
        cookie: String,
        keyword: String,
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
            repository.serialReceiptOnPutaway(baseUrl,jsonObject,page, rows, sort, asc,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.rows.isNotEmpty())
                {
                    tempList.addAll(it.rows)
                    serialList.value=tempList

                }
                serialsCount.value=it.total
                log("serialPutawayAssign",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"serialPutawayAssign",context)
            },{},{disposable.add(it)}).let {  }
        }
    }


    fun assignSerialPutaway(
        url: String,
        receiptId: String,
        progressBar: ProgressBar,
        context: Context,
        cookie: String,
        onErrorCallback: ()-> Unit,
        callback: () -> Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("ReceiptID", receiptId)

        viewModelScope.launch {
            progressBar.visibility = View.VISIBLE
            repository.assignSerialPutaway(
                url,jsonObject,cookie
            ).subscribe(
                {
                    progressBar.visibility = View.GONE
                    toast(context.getString(R.string.itsDone),context)
                    callback()
                },
                {
                    onErrorCallback()
                    progressBar.visibility = View.GONE
                    showErrorMsg(it,"",context)
                }
            )
        }
    }

}