package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.LocationProductSerialRow
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.report_inventory.report_location.LocationInventoryRows
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class LocatoinInventoryReportViewModel(application: Application,context: Context):
    AndroidViewModel(application)
{
    private var context: Context=context
    private val repository=MyRepository()
    private var reportLocationList=MutableLiveData<List<LocationInventoryRows>>()
    private var reportLocationCount=MutableLiveData<Int>()
    private var tempList=ArrayList<LocationInventoryRows>()
    private var productList=MutableLiveData<List<ProductModel>>()
    private var ownerList= MutableLiveData<List<OwnerModel>>()

    private var serialList = MutableLiveData<List<LocationProductSerialRow>>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }


    fun getLocationCount(): MutableLiveData<Int> {
        return reportLocationCount
    }

    fun getLocationSerials() : LiveData<List<LocationProductSerialRow>> {
        return serialList
    }
    fun getOwners(): MutableLiveData<List<OwnerModel>> {
        return ownerList
    }

    fun getProductsList(): MutableLiveData<List<ProductModel>> {
        return productList
    }

    fun getReportLocationList(): MutableLiveData<List<LocationInventoryRows>> {
        return reportLocationList
    }

    fun clearReportList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            reportLocationList.value=tempList
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

    fun setSerialList(
        baseUrl: String,
        locationProductID: String,
        cookie: String,
        progressBar: ProgressBar
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("LocationProductID",locationProductID)

        showSimpleProgress(true,progressBar)
        viewModelScope.launch {
            repository.getLocationProductSerials(baseUrl,jsonObject,cookie)
                .subscribe(
                    {
                        if (it.rows?.isNotEmpty() == true) {

                            serialList.value = it.rows!!
                        }
                        showSimpleProgress(false,progressBar)
                    }  ,
                    {
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it,"product serials",context)
                    }
                )
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


    fun reportLocationInventory(
        baseUrl:String,
        invTypeId:Int ?,
        productCode:String,
        locationCode: String,
        productTitle: String,
        ownerCode:String,
        page: Int,
        rows: Int ,
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

           jsonObject.addProperty("LocationCode",locationCode)
           jsonObject.addProperty("ProductTitle",productTitle)
           jsonObject.addProperty("OwnerInfoID",ownerCode)
           jsonObject.addProperty("ProductCode",productCode)
           jsonObject.addProperty("InvTypeID",invTypeId)

           log("inventoryJson",jsonObject.toString())

           repository.reportLocationInventory(baseUrl,jsonObject,page,rows,sort,order,cookie)
               .subscribe(
                   {
                       swipeLayout.isRefreshing=false
                       showSimpleProgress(false,progressBar)
                       if (it.rows.size!=0)
                       {
                           tempList.addAll(it.rows)
                           reportLocationList.value=(tempList)
                           log("data",tempList.size.toString()
                                   +"->"
                                   +reportLocationList.value?.size.toString())
                       }
                       reportLocationCount.value=it.total

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

    fun invList(context: Context): List<CatalogModel> {
        val invList = ArrayList<CatalogModel>()
        invList.add(CatalogModel(valueField =1 , title = context.getString(R.string.healty)))
        invList.add(CatalogModel(valueField =2 , title = context.getString(R.string.defection)))
        invList.add(CatalogModel(valueField =3 , title = context.getString(R.string.toredPaper)))
        invList.add(CatalogModel(valueField =4, title = context.getString(R.string.return_value)))
        invList.add(CatalogModel(valueField =5 , title = context.getString(R.string.openPack)))
        return invList
    }



}