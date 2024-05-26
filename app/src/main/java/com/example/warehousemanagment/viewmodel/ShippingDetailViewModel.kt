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
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.shipping.AddShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.LoadingFinishModel
import com.example.warehousemanagment.model.models.shipping.RemoveShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailModel
import com.example.warehousemanagment.model.models.shipping.ShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.customer.CustomerModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailRow
import com.example.warehousemanagment.model.models.transfer_task.DestinyLocationTransfer
import com.example.warehousemanagment.model.models.transfer_task.source_location.SourceLocationRow
import com.example.warehousemanagment.ui.dialog.SheetDenyAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetDenyApproveAlertDialog
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class ShippingDetailViewModel(application: Application,context: Context): AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context

    private var shippingDetailList= MutableLiveData<List<ShippingDetailRow>>()
    private var revokLocation= MutableLiveData<List<DestinyLocationTransfer>>()

    private var shippingSerials=MutableLiveData<List<ShippingSerialModel>>()
    private var removeShippingModel=MutableLiveData<RemoveShippingSerialModel>()
    private var loadinFinish=MutableLiveData<LoadingFinishModel>()
    private var addSerialModel=MutableLiveData<AddShippingSerialModel>()
    private var shippingCount=MutableLiveData<ShippingDetailModel>()
    private var tempList=ArrayList<ShippingDetailRow>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

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


    fun getRevokLocation(): MutableLiveData<List<DestinyLocationTransfer>> {
        return revokLocation
    }



    fun getAddSerialModel(): LiveData<AddShippingSerialModel> {
        return addSerialModel.distinctUntilChanged()
    }
    fun getShippingDetail(): MutableLiveData<List<ShippingDetailRow>> {
        return shippingDetailList
    }
    fun getShippingSerials(): LiveData<List<ShippingSerialModel>> {
        return shippingSerials.distinctUntilChanged()
    }
    fun getRemovingSerialResult(): LiveData<RemoveShippingSerialModel> {
        return removeShippingModel.distinctUntilChanged()
    }
    fun getLoadingFinish(): MutableLiveData<LoadingFinishModel> {
        return loadinFinish
    }


    fun setClearList()
    {
        revokLocation.value= emptyList()
    }
    fun setRevokeLocation(
        baseUrl:String, model: ShippingDetailRow,
        cookie:String, progressBar: ProgressBar, searchLocationDestiny: String)
    {
        showSimpleProgress(true,progressBar)
        val jsonObject= JsonObject()
        jsonObject.addProperty("LocationCode",searchLocationDestiny)
        jsonObject.addProperty("WarehouseID",model.warehouseId)
        viewModelScope.launch()
        {
            repository.revokLocation(baseUrl,jsonObject,cookie)
                .subscribe(
                {
                    showSimpleProgress(false,progressBar)
                    revokLocation.value=(it)
                    log("revokLocation", it.toString())

                },
                {
                    showSimpleProgress(false,progressBar)
                    showErrorMsg(it, "revokLocation", context)
                }
            ).let { }
        }
    }
    fun revoke(
        baseUrl:String, model: ShippingDetailRow,
        cookie:String, progressBar: ProgressBar,quantity:Int,
        loadingCancelId:Int,
        locationDestination:String,
        callBack:()->Unit,
    )
    {
        showSimpleProgress(true,progressBar)
        val jsonObject= JsonObject()
        jsonObject.addProperty("ShippingAddressDetailID",model.shippingAddressDetailID)
        jsonObject.addProperty("ShippingAddressID",model.shippingID)
        jsonObject.addProperty("Quantity",quantity)
        jsonObject.addProperty("LoadingCancelID",loadingCancelId)
        jsonObject.addProperty("DestinationLocationID",locationDestination)
        viewModelScope.launch()
        {
            repository.revoke(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progressBar)
                        log("revoke", it.toString())
                        callBack()

                    },
                    {
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "revoke", context)
                    }
                ).let { }
        }
    }


    fun addSerial2(
        baseUrl:String,shippingAddressDetailID:String,serialNumber:String
                   ,productID:String,progress:ProgressBar,cookie:String,
                    onReceiveError:()->Unit,
                    refreshSerial:(model:AddShippingSerialModel)->Unit,
        )
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailID)
            jsonObject.addProperty("SerialNumber",serialNumber)
            jsonObject.addProperty("ProductID",productID)
            showSimpleProgress(true,progress)
            repository.insertShippingDetail(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
                        log("addShippingSerial", it.toString())

                        refreshSerial(it)


                    },
                    {
                        showSimpleProgress(false,progress)
                        showErrorMsg(it, "addShippingSerial", context)
                        onReceiveError()

                    },{},{ }
                ).let { }
        }
    }


    fun setLoadingFinish(
        baseUrl:String,shippingAddressDetailId:String,cookie:String,progress: ProgressBar,refresh:()->Unit)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            showSimpleProgress(true,progress)
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailId)
            repository.loadingFinish(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
//                        loadinFinish.value=(it)
                        refresh()
                        log("loadingFinish", it.toString())
                    },
                    {
                        showSimpleProgress(false,progress)
                        showErrorMsg(it, "loadingFinish", context)
                    },
                ).let { }
        }
    }
    fun removeShippingSerial(
        baseUrl:String,shippingAddressDetailID:String,serialId:String,cookie:String,progress:ProgressBar)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailID)
            jsonObject.addProperty("SerialNumber",serialId)
            showSimpleProgress(true,progress)
            repository.removeShippingSerial(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
                        removeShippingModel.postValue(it)
                        log("removeShippingSerial", it.toString())
                    },
                    {
                        showSimpleProgress(false,progress)
                        showErrorMsg(it, "removeShippingSerial", context)
                    },
                ).let { }
        }
    }

    fun setShippingSerials(
        baseUrl:String,shippingAddressDetailID:String,
        cookie:String,)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailID)
            repository.getShippingSerials(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        shippingSerials.value=(it)
                        log("shippingSerials", it.toString())
                    },
                    {
                        showErrorMsg(it, "shippingSerials", context)
                    },{},{
                        disposable.add(it)
                    }
                ).let { }
        }
    }
    fun setShippingList(
        baseUrl:String,
        shippingId: String,
        keyword: String,
        customerName:String,
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
            jsonObject.addProperty("ShippingID",shippingId)
            jsonObject.addProperty("CustomerFullName",customerName)
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.getShippingDetail(baseUrl,jsonObject,page, rows, sort, asc, cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.shippingDetailRows.size!=0)
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
                    },{},{disposable.add(it)}
                ).let { }
        }
    }

    fun reasonList(): List<CatalogModel> {
        val myList = ArrayList<CatalogModel>()
        myList.add(CatalogModel(valueField =1 , title = context.getString(R.string.incorrectBar)))
        myList.add(CatalogModel(valueField =2 , title =context.getString(R.string.wrongStimate)))
        myList.add(CatalogModel(valueField =3 , title = context.getString(R.string.wrongNeed)))
        myList.add(CatalogModel(valueField =4, title = context.getString(R.string.cancelBar)))
        return myList
    }


}