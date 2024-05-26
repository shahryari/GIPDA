package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.check_truck.confirm.ConfirmCheckTruckModel
import com.example.warehousemanagment.model.models.check_truck.deny.DenyCheckTruckModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.shipping.shipping_truck.ShippingTruckRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class ShippingViewModel(application: Application,context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository()

    private var shippingList= MutableLiveData<List<ShippingTruckRow>>()
    private var denyModel=MutableLiveData<DenyCheckTruckModel>()
    private var confirmModel=MutableLiveData<ConfirmCheckTruckModel>()

    private var tempList=ArrayList<ShippingTruckRow>()
    private var shippingCount=MutableLiveData<Int>()

    private var context: Context=context
    private var disposable: CompositeDisposable = CompositeDisposable()

    fun getConfirmResult(): MutableLiveData<ConfirmCheckTruckModel> {
        return confirmModel
    }
    fun getDenyModel(): MutableLiveData<DenyCheckTruckModel> {
        return denyModel
    }


    fun dispose() { disposable.clear() }

    fun clearList(){
        if (tempList.size!=0)
        {
            tempList.clear()
            shippingList.value=tempList
        }
    }
    fun getShippingCount(): LiveData<Int> {
        return shippingCount.distinctUntilChanged()
    }


    fun getDenyReason(): List<CatalogModel>
    {
        val reasons:ArrayList<CatalogModel> = ArrayList()
        reasons.add(CatalogModel("",1
            ,context.getString(R.string.notApprovingCar)))
        reasons.add(CatalogModel("",2
            ,context.getString(R.string.notApprovingDriver)))
        reasons.add(CatalogModel("",3
            ,context.getString(R.string.resign)))


        return reasons
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
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.shippingTruckList(baseUrl,jsonObject, page, rows, sort, asc, cookie)
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
                        log("shippingList", it.toString())
                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "shippingList", context)
                    },{},{disposable.add(it)}
                ).let { }
        }
    }

    fun setConfirmTruck(
        baseUrl:String,shippingAddressId:String,cookie:String,progressBar: ProgressBar)
    {
        viewModelScope.launch()
        {
            val jsonObject= JsonObject()
            showSimpleProgress(true,progressBar)
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            repository.confirmCheckTruck(baseUrl,jsonObject,cookie)
                .subscribe({
                    log("confirmCheckTruck", it.toString())
                    showSimpleProgress(false,progressBar)
                    confirmModel.value=it

                }, {
                    showErrorMsg(it,"confirmCheckTruck",context)
                    showSimpleProgress(false,progressBar)
                },{},{disposable.add(it)}).let {  }
        }

    }
    fun setDenyTruck(
        baseUrl:String,shippingAddressId:String,
                     shippingId:String,reason:Int,cookie:String,progressBar: ProgressBar)
    {
        viewModelScope.launch {
            val jsonObject= JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            jsonObject.addProperty("ShippingID",shippingId)
            jsonObject.addProperty("Reason",reason)
            showSimpleProgress(true,progressBar)
            log("denyJson",jsonObject.toString())
            repository.denyCheckTruck(baseUrl,jsonObject,cookie)
                .subscribe({
                    denyModel.value=it
                    showSimpleProgress(false,progressBar)
                    log("denyCheckTruck", it.toString())

                }, {
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"denyCheckTruck",context)
                },{},{disposable.add(it)}).let {  }
        }

    }

    fun leftDock(
        url: String,
        shippingAddressId: String,
        cookie: String,
        progressBar: ProgressBar,
        onLeftDockSuccess:()->Unit,
        onLeftDockError:()->Unit
    )
    {

        viewModelScope.launch()
        {
            val jsonObject= JsonObject()
            jsonObject.addProperty("ShippingAddressID",shippingAddressId)
            showSimpleProgress(true,progressBar)
            repository.leftDock(url,jsonObject,cookie)
                .subscribe({
                    showSimpleProgress(false,progressBar)
                    log("leftDock", it.toString())
                    onLeftDockSuccess()

                }, {
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it,"leftDockError",context)
                    onLeftDockError()
                }
                ).let {  }
        }
    }



}