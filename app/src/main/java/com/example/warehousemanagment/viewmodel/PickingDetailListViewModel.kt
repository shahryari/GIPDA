package com.example.warehousemanagment.viewmodel

import PickingDetailRow
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
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class PickingDetailListViewModel(application: Application, context: Context)
    : AndroidViewModel(application)
{
    private val repository= MyRepository( )
    private var context: Context=context
    private var pickginDetailList= MutableLiveData<List<PickingDetailRow>>()
    private var completePickingModel= MutableLiveData<CompletePickingModel>()

    private var tempList=ArrayList<PickingDetailRow>()
    private var pickCount=MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearPickingList(){
        if (tempList.size!=0)
        {
            tempList.clear()
            pickginDetailList.value=tempList
        }

    }
    fun getPickCount(): MutableLiveData<Int> {
        return pickCount
    }

    fun getPickingList(): MutableLiveData<List<PickingDetailRow>> {
        return pickginDetailList
    }

    fun getCompletePicking(): MutableLiveData<CompletePickingModel> {
        return completePickingModel
    }
    fun completePicking(
        baseUrl:String,
        productCode:String,
        locations:String,
        sumQuantity:Int,
        gtinCode:String ?,
        cookie:String,progress:ProgressBar,callBack:()->Unit)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ProductCode",productCode)
            jsonObject.addProperty("Locations",locations)
            jsonObject.addProperty("SumQuantity",sumQuantity)
            jsonObject.addProperty("GTINCode",gtinCode)
            showSimpleProgress(true,progress)
            repository.completePicking(baseUrl,jsonObject,cookie).subscribe({
                showSimpleProgress(false,progress)
                completePickingModel.postValue(it)
                log("completePicking",it.toString())
                callBack()
            },{
                showSimpleProgress(false,progress)
                showErrorMsg(it,"completePicking",context)
            }).let {  }
        }
    }


    fun setPickingDetailList(
        locationCode:String,
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
            jsonObject.addProperty("LocationCode",locationCode)
            repository.pickTruckDetailList(baseUrl,jsonObject, page, rows, sort, asc,cookie)
                .subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
               if (it.rows.size!=0)
               {
                   tempList.addAll(it.rows)
                   pickginDetailList.value=tempList
               }
                pickCount.value=it.total

                log("pickingDetailList",it.toString())
            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"pickingDetailList",context)
            },{},{disposable.add(it)}).let {  }
        }
    }

}