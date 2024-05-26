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
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.google.gson.JsonObject
import com.test.StockTrackRow
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class StockTakeViewModel(application: Application, context: Context):
    AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var stockList: MutableLiveData<List<StockTrackRow>>
            = MutableLiveData<List<StockTrackRow>>()

    private var tempList=ArrayList<StockTrackRow>()

    private var stockCount= MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearStockList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            stockList.value= emptyList()
        }

    }
    fun getStockCount(): MutableLiveData<Int> {
        return stockCount
    }
    fun getStockTakeList(): MutableLiveData<List<StockTrackRow>> {
        return stockList
    }
    fun setStockTakeList(
        baseUrl:String,
        cookie: String,
        keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject= JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.stockTakingList(
                    url = baseUrl,
                    jsonObject=jsonObject,
                    page=page,
                    rows=rows,
                    sort=sort,
                    order = order,
                    cookie=cookie
                )
                .subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.rows.size>0)
                {
                    tempList.addAll(it.rows)
                    stockList.value=tempList

                }
                stockCount.value=it.total
                log("stockTake",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"stockTake",context)
            },{},{disposable.add(it)}).let {  }
        }
    }

}