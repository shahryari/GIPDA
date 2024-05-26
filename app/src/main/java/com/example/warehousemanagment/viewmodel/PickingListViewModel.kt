package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
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
import com.example.warehousemanagment.model.models.picking.CompletePickingModel
import com.example.warehousemanagment.model.models.picking.picking.PickingRow
import com.example.warehousemanagment.model.models.picking.picking.PickingTruckModel
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class PickingListViewModel(application: Application,context: Context): AndroidViewModel(application)
{
    private val repository= MyRepository( )
    private var context: Context=context
    private var pickingList= MutableLiveData<List<PickingRow>>()

    private var tempList=ArrayList<PickingRow>()
    private var pickCount=MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearPickingList(){
        if (tempList.size!=0)
        {
            tempList.clear()
            pickingList.value=tempList
        }

    }
    fun getPickCount(): MutableLiveData<Int> {
        return pickCount
    }

    fun getPickingList(): MutableLiveData<List<PickingRow>> {
        return pickingList
    }


    fun setPickingList(
        baseUrl:String,
        keyword: String,
        page: Int,
        rows: Int,
        sort: String,
        asc: String, cookie: String, progressBar: ProgressBar, swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.pickTruckList(baseUrl,jsonObject, page, rows, sort, asc,cookie).subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
               if (it.rows.size!=0)
               {
                   tempList.addAll(it.rows)
                   pickingList.value=tempList
               }
                pickCount.value=it.total

                log("pickingList",it.toString())
            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"pickingList",context)
            },{},{disposable.add(it)}).let {  }
        }
    }

}