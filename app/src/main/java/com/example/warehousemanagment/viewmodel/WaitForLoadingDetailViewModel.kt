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
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailRow
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class WaitForLoadingDetailViewModel(application: Application,context: Context) : AndroidViewModel(application) {
    val repository = MyRepository()
    private var context: Context=context

    private var shippingDetailList= MutableLiveData<List<ShippingDetailRow>>()
    private var tempList=ArrayList<ShippingDetailRow>()
    private var shippingCount=MutableLiveData<ShippingDetailModel>()

    fun clearList(){
        if (tempList.size!=0)
        {
            tempList.clear()
            shippingDetailList.value=tempList
        }
    }

    fun getShippingCount(): LiveData<ShippingDetailModel> {
        return shippingCount.distinctUntilChanged()
    }

    fun getShippingDetail(): MutableLiveData<List<ShippingDetailRow>> {
        return shippingDetailList
    }

    fun setShippingList(
        baseUrl:String,
        shippingId: String,
        keyword: String,
        customers:String,
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
            jsonObject.addProperty("ShippingID",shippingId)
            jsonObject.addProperty("CustomerIDs",customers)
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.getShippingDetail(baseUrl,jsonObject,page, rows, sort, asc, cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.shippingDetailRows.isNotEmpty())
                        {
                            tempList.addAll(it.shippingDetailRows)
                            shippingDetailList.value=(tempList)
                        }
                        shippingCount.value=it

                        log("shippingDetailList", it.toString())
                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "shippingDetailList", context)
                    },{},{}
                ).let { }
        }
    }
}