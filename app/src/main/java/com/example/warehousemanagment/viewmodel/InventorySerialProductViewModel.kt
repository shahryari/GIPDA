package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.report_inventory.serial_inventory.SerialInventoryRows
import com.example.warehousemanagment.model.models.report_inventory.serial_inventory_product.SerialInvProductRows
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class InventorySerialProductViewModel(application: Application, context: Context):
    AndroidViewModel(application)
{
    private var context: Context=context
    private val repository=MyRepository()
    private var reportSerialList=MutableLiveData<List<SerialInvProductRows>>()
    private var reportSerialCount=MutableLiveData<Int>()
    private var tempList=ArrayList<SerialInvProductRows>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun getLocationCount(): MutableLiveData<Int> {
        return reportSerialCount
    }


    fun getReportLocationList(): MutableLiveData<List<SerialInvProductRows>> {
        return reportSerialList
    }

    fun clearReportList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            reportSerialList.value=tempList
        }
    }




    fun reportSerialInventoryProduct(
        baseUrl:String,
        serialNumber:String,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject=JsonObject()
            jsonObject.addProperty("SerialNumber",serialNumber)
            repository.reportSerialInventoryProduct(baseUrl,jsonObject,page,rows,sort,order,cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.serialInvProductRows.size!=0)
                        {
                            tempList.addAll(it.serialInvProductRows)
                            reportSerialList.value=(tempList)
                            log("data",tempList.size.toString()+"->"+reportSerialList.value?.size.toString())
                        }
                        reportSerialCount.value=it.total

                        log("reportLocationInventory", it.toString())
                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "reportLocationInventory", context)
                    },{},{disposable.addAll(it)}
                ).let { }
        }



    }



}