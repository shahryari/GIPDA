package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.shipping.shipping_truck.ShippingTruckModel
import com.example.warehousemanagment.model.models.shipping.shipping_truck.ShippingTruckRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class CanselShippingViewModel(application: Application,context: Context)
    : AndroidViewModel(application)
{
    private val repository=MyRepository( )

    private var shippingList= MutableLiveData<List<ShippingTruckRow>>()
    private var context: Context=context

    private var tempList=ArrayList<ShippingTruckRow>()
    private var shippingCount = MutableLiveData<Int>()
    private var disposable=CompositeDisposable()

    fun dispose(){
        disposable.clear()
    }
    fun clearList(){
        if (tempList.size!=0)
        {
            tempList.clear()
            shippingList.value=tempList
        }
    }
    fun getShippingCount(): MutableLiveData<Int> {
        return shippingCount
    }

    fun getShippingList( ): MutableLiveData<List<ShippingTruckRow>> {
        return shippingList
    }


    fun setShippingList(
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
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            showSimpleProgress(true,progressBar)
            repository.shippingCanselTruckList(baseUrl,jsonObject, page, rows, sort, asc, cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.shippingTruckRows.size!=0)
                        {
                            tempList.addAll(it.shippingTruckRows)
                            shippingList.value=tempList
                        }
                        shippingCount.value=it.total
                        log("canselShippingList", it.toString())
                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "canselShippingList", context)
                    },{},{disposable.add(it)}
                ).let { }
        }
    }

}