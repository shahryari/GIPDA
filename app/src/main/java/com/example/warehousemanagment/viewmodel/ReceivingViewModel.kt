package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.receive.receiving.RowReceivingModel
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import java.util.stream.Collectors


class ReceivingViewModel(application: Application,context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var receiveList: MutableLiveData<List<RowReceivingModel>>
    = MutableLiveData<List<RowReceivingModel>>()

    private var tempList=ArrayList<RowReceivingModel>()

    private var receiveCount=MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearReceiveList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            receiveList.value= emptyList()
        }

    }
    fun getReceiveCount(): MutableLiveData<Int> {
        return receiveCount
    }
    fun getReceivingList(): MutableLiveData<List<RowReceivingModel>>  {
        return receiveList
    }
    fun setReceivingList(
        baseUrl:String,
        cookie: String,
        keyword: String,
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
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.getReceivingList(baseUrl,cookie,jsonObject,page, rows, sort, asc).subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.rows.size>0)
                {
                    tempList.addAll(it.rows)
                    receiveList.value=tempList

                }
                receiveCount.value=it.total
                log("receiving",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"receiving",context)
            },{},{disposable.add(it)}).let {  }
        }
    }


}