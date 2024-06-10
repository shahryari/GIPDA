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
import com.example.warehousemanagment.model.models.putaway.truck.PutawayTruckRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class PutAwayViewModel(application: Application,context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context

    private var putawayTruckList=MutableLiveData<List<PutawayTruckRow>>()
    private var tempList=ArrayList<PutawayTruckRow>()
    private var countPut=MutableLiveData<Int>()

    private var disposable:CompositeDisposable= CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun getCountPut(): LiveData<Int> {
        return countPut.distinctUntilChanged()
    }

    fun getPutawayTruckList(): MutableLiveData<List<PutawayTruckRow>> {
        return putawayTruckList
    }

    fun clearPutaway(){
        tempList.clear()
        putawayTruckList.value= tempList
    }

    fun setPutawayTruck(
        baseUrl:String,
        cookie: String, keyword: String,
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
            jsonObject.addProperty(ApiUtils.Keyword, keyword)

            repository.putawayTruckList(baseUrl,cookie,jsonObject,page, rows, sort, asc).subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.rows.isNotEmpty())
                {

                    tempList.addAll(it.rows)
                    putawayTruckList.value=tempList
                }
                countPut.value=it.total
                log("putawayList",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"putawayList",context)
            },{},{
                disposable.add(it)
            }).let {  }


        }

    }

}