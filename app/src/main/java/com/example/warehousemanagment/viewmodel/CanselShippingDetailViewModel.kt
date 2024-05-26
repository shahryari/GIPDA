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
import com.example.warehousemanagment.model.models.shipping.AddShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.LoadingFinishModel
import com.example.warehousemanagment.model.models.shipping.RemoveShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailModel
import com.example.warehousemanagment.model.models.shipping.ShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.customer.CustomerModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class CanselShippingDetailViewModel(application: Application,context: Context)
    : AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var disposable= CompositeDisposable()
    private var customers= MutableLiveData<List<CustomerModel>>()

    private var shippingDetailList= MutableLiveData<List<ShippingDetailRow>>()
    private var shippingSerials=MutableLiveData<List<ShippingSerialModel>>()
    private var removeShippingModel=MutableLiveData<RemoveShippingSerialModel>()
    private var addSerialModel=MutableLiveData<AddShippingSerialModel>()
    private var tempList=ArrayList<ShippingDetailRow>()
    private var shipCount=MutableLiveData<ShippingDetailModel>()

    fun getShippingDetailCount(): MutableLiveData<ShippingDetailModel> {
        return shipCount
    }

    fun dispose(){
        disposable.clear()
    }
    fun clearList(){
        if (tempList.size!=0)
        {
            tempList.clear()
            shippingDetailList.value=tempList
        }
    }

    fun getCustomers(): MutableLiveData<List<CustomerModel>> {
        return customers
    }

    fun getAddSerialModel(): LiveData<AddShippingSerialModel> {
        return addSerialModel.distinctUntilChanged()
    }
    fun getShippingDetail(): MutableLiveData<List<ShippingDetailRow>> {
        return shippingDetailList
    }
    fun getShippingSerials(): MutableLiveData<List<ShippingSerialModel>> {
        return shippingSerials
    }
    fun getRemovingSerialResult(): LiveData<RemoveShippingSerialModel> {
        return removeShippingModel.distinctUntilChanged()
    }

    fun addSerial(
        baseUrl:String,shippingAddressDetailID:String,serialNumber:String
                  ,progress:ProgressBar,cookie:String,
                  onReceiveError:()->Unit,
                  refreshSerial:(model:AddShippingSerialModel)->Unit)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailID)
            jsonObject.addProperty("SerialNumber",serialNumber)
            showSimpleProgress(true,progress)
            repository.insertCanselShippingDetail(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
                        refreshSerial(it)
                        log("CanseladdShippingSerial", it.toString())
                    },
                    {
                        showSimpleProgress(false,progress)
                        showErrorMsg(it, "CanseladdShippingSerial", context)
                        onReceiveError()
                    },
                ).let { }
        }
    }
    fun setLoadingFinish(
        baseUrl:String,shippingAddressDetailId:String,cookie:String,progress: ProgressBar,
    callback:(LoadingFinishModel)->Unit)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            showSimpleProgress(true,progress)
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailId)
            repository.loadingCanselFinish(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
                        callback(it)
                        log("canselloadingFinish", it.toString())
                    },
                    {
                        showSimpleProgress(false,progress)
                        showErrorMsg(it, "canselloadingFinish", context)
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
            repository.removeCanselShippingSerial(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        showSimpleProgress(false,progress)
                        removeShippingModel.postValue(it)
                        log("CanselremoveShippingSerial", it.toString())
                    },
                    {
                        showSimpleProgress(false,progress)
                        showErrorMsg(it, "CanselremoveShippingSerial", context)
                    },
                ).let { }
        }
    }

    fun setShippingSerials(
        baseUrl:String,shippingAddressDetailID:String,cookie:String)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingAddressDetailID",shippingAddressDetailID)
            repository.getCanselShippingSerials(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        shippingSerials.postValue(it)
                        log("CanselshippingSerials", it.toString())
                    },
                    {
                        showErrorMsg(it, "CanselshippingSerials", context)
                    },{},{disposable.add(it)}
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
        asc: String,
        cookie: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty("ShippingID",shippingId)
            jsonObject.addProperty("CustomerFullName",customerName)
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            repository.getCanselShippingDetail(baseUrl,jsonObject,page, rows, sort, asc, cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.shippingDetailRows.size!=0)
                        {
                            tempList.addAll(it.shippingDetailRows)
                            shippingDetailList.value=tempList
                        }
                        shipCount.value=it

                        log("CanselshippingDetailList", it.toString())
                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "CanselshippingDetailList", context)
                    },{},{disposable.add(it)}
                ).let { }
        }
    }



}