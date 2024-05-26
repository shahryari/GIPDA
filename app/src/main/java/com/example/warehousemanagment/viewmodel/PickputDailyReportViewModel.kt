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
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.report_inventory.pickput.PickAndPutRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class PickputDailyReportViewModel(application: Application,context: Context)
    : AndroidViewModel(application)
{
    private var context: Context=context
    private val repository= MyRepository()
    private var pickAndPutList= MutableLiveData<List<PickAndPutRow>>()
    private var reportList=ArrayList<PickAndPutRow>()
    private var productList=MutableLiveData<List<ProductModel>>()
    private var ownerList= MutableLiveData<List<OwnerModel>>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    private var arrayCount=MutableLiveData<Int>()

    fun getOwners(): MutableLiveData<List<OwnerModel>> {
        return ownerList
    }
    fun getArrayCount(): LiveData<Int> {
        return arrayCount.distinctUntilChanged()
    }
    fun getProductsList(): MutableLiveData<List<ProductModel>> {
        return productList
    }
    fun getReportLocationList(): MutableLiveData<List<PickAndPutRow>> {
        return pickAndPutList
    }

    fun clearReportList()  {
        if (reportList.size!=0)
        {
            reportList.clear()
            pickAndPutList.value=reportList
        }

    }



    fun reportPickAndPutInventory(
        baseUrl:String,
        taskTypeId:Int?,
        locationCode: String,
        date: String,
        productTitle:String,
        ownerCode:String,
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
            val jsonObject= JsonObject()
            jsonObject.addProperty("LocationCode",locationCode)
            jsonObject.addProperty("DateFilter",date)
            jsonObject.addProperty("ProductTitle",productTitle)
            jsonObject.addProperty("OwnerCode",ownerCode)
            jsonObject.addProperty("TaskTypeID",taskTypeId)

            log("jsonObjectOfInventory",jsonObject.toString())
            repository.pickAndPutReport(baseUrl,jsonObject,page,rows,sort,order,cookie)
                .subscribe(
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        if (it.pickAndPutRows.size!=0){
                            reportList.addAll(it.pickAndPutRows)
                            pickAndPutList.value=(reportList)
                        }
                        arrayCount.value=it.total
                        log("pickAndPut", it.toString())
                    },
                    {
                        swipeLayout.isRefreshing=false
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it, "pickAndPut", context)
                    },{},{disposable.add(it)}
                ).let { }
        }

    }
    fun setOwnerList(
        baseUrl:String,cookie:String)
    {
        viewModelScope.launch()
        {
            repository.getOwner(baseUrl,cookie)
                .subscribe({
                    ownerList.value=(it)
                    log("Owner", it.toString())

                }, {
                    showErrorMsg(it,"Owner",context)
                },).let {  }
        }

    }

    fun setProductList(
        baseUrl:String,productTitle:String,cookie: String)
    {
        viewModelScope.launch()
        {
            val jsonObject=JsonObject()
            jsonObject.addProperty("ProductTitle",productTitle)
            repository.getProducts(baseUrl,jsonObject,cookie)
                .subscribe({
                    productList.value=(it)
                    log("products", it.toString())

                }, {
                    showErrorMsg(it,"products",context)
                },).let {  }
        }
    }

    fun getTaskTypes(): List<CatalogModel> {
        val taskTypes = listOf(
            CatalogModel("TaskType", 1, "Receiving"),
            CatalogModel("TaskType", 2, "Putaway"),
            CatalogModel("TaskType", 3, "Picking"),
            CatalogModel("TaskType", 4, "Shipping"),
            CatalogModel("TaskType", 5, "Transfer"),
            CatalogModel("TaskType", 6, "Cancel"),
            CatalogModel("TaskType", 7, "InvoiceCancel"),
            CatalogModel("TaskType", 8, "")
        )
        return taskTypes
    }





}