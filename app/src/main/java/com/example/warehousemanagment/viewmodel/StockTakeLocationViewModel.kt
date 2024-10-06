package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.isStockMessageEqualMinusOne
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.stock.StockLocationRow
import com.example.warehousemanagment.model.models.stock.stock_take_location.StockTackingLocationRow
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class StockTakeLocationViewModel(application: Application, context: Context):
    AndroidViewModel(application)
{
    private val repository=MyRepository( )
    private var context: Context=context
    private var stockList: MutableLiveData<List<StockTackingLocationRow>>
            = MutableLiveData<List<StockTackingLocationRow>>()

    private var locationList: MutableLiveData<List<StockLocationRow>> = MutableLiveData<List<StockLocationRow>>()

    private var ownerList= MutableLiveData<List<OwnerModel>>()

    private var productList=MutableLiveData<List<ProductModel>>()

    private var tempList=ArrayList<StockTackingLocationRow>()

    private var stockCount= MutableLiveData<Int>()

    private var disposable: CompositeDisposable = CompositeDisposable()
    fun dispose() { disposable.clear() }

    fun clearStockList()
    {
        if (tempList.size!=0)
        {
            tempList.clear()
            stockList.value= emptyList()
        }

    }
    fun getOwners(): MutableLiveData<List<OwnerModel>> {
        return ownerList
    }

    fun getLocations() : MutableLiveData<List<StockLocationRow>> {
        return locationList
    }

    fun getProductsList(): MutableLiveData<List<ProductModel>> {
        return productList
    }
    fun getStockCount(): MutableLiveData<Int> {
        return stockCount
    }
    fun getStockTakeLocationList(): MutableLiveData<List<StockTackingLocationRow>> {
        return stockList
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

    fun setLocationList(
        baseUrl:String,
        stockTurnId: String,
        keyword: String = "",
        cookie: String
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("StockTurnID", stockTurnId)
        jsonObject.addProperty("Keyword",keyword)
        viewModelScope.launch() {
            repository.stockLocation(
                url = baseUrl,
                jsonObject = jsonObject,
                page = 1,
                rows = 1000,
                sort = "LocationCode",
                order = "asc",
                cookie = cookie
            ).subscribe({
                locationList.value = it.rows
                log("LocationList", it.toString())
            },{
                showErrorMsg(it,"LocationList",context)
            }).let {  }
        }
    }

    fun setStockTakeLocationList(
        baseUrl:String,
        cookie: String,
        keyword: String,
        stockTurnId:String,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progressBar)
            val jsonObject= JsonObject()
            jsonObject.addProperty(ApiUtils.Keyword,keyword)
            jsonObject.addProperty("StockTurnId",stockTurnId)
            repository.stockTakingLocationList(
                    url = baseUrl,
                    jsonObject=jsonObject,
                    page=page,
                    rows=rows,
                    sort=sort,
                    order = order,
                    cookie=cookie
                )
                .subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.rows.size>0)
                {
                    tempList.addAll(it.rows)
                    stockList.value=tempList

                }
                stockCount.value=it.total
//                log("stockTakeLocation",it.toString())

            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
//                showErrorMsg(it,"stockTakeLocation",context)
            },{},{disposable.add(it)}).let {  }
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
        }//        dialogBinding.locationCode.requestFocus()

    }



    fun stockTakingLocationInsert(
        baseUrl: String,
        cookie: String,
        locationCode:String?,
        countQuantity:Int,
        goodSystemCode:String,
        invTypeId:Int,
        ownerCode:String,
        stockTurnId: String,
        progressBar: ProgressBar,
        onCallBack:()->Unit
    ){
        viewModelScope.launch()
        {
            val jsonObject = JsonObject()
            jsonObject.addProperty("LocationCode", locationCode)
            jsonObject.addProperty("CountQuantity", countQuantity)
            jsonObject.addProperty("GoodsSystemCode", goodSystemCode)
            jsonObject.addProperty("InvTypeID", invTypeId)
            jsonObject.addProperty("OwnerInfoID", ownerCode)
            jsonObject.addProperty("StockTurnID",stockTurnId)

            log("jsonStock:",jsonObject.toString())

            showSimpleProgress(true, progressBar)
            repository.stockTakingLocationInsert(baseUrl, jsonObject, cookie)
                .subscribe(
                    {
                        showSimpleProgress(false, progressBar)
                        log("stockTakingLocationInsert", it.toString())
                        onCallBack()

                    },
                    {

                        showSimpleProgress(false, progressBar)
                        showErrorMsg(it, "stockTakingLocationInsert", context)
                    },
                ).let { }
        }

    }

    fun stockTakingCount(
        baseUrl: String,
        cookie: String,
        stockTurnTeamLocationID:String,
        countQuantity:Int,
        progressBar: ProgressBar,
        onCallBack:(Boolean)->Unit,
        onError:(Boolean)->Unit,
    ){
        viewModelScope.launch()
        {
            val jsonObject = JsonObject()
            jsonObject.addProperty("StockTurnTeamLocationID", stockTurnTeamLocationID)
            jsonObject.addProperty("CountQuantity", countQuantity)

            showSimpleProgress(true, progressBar)
            repository.stockTakingCount(baseUrl, jsonObject, cookie)
                .subscribe(
                    {
                        showSimpleProgress(false, progressBar)
                        log("stockTakingCount", it.toString())
                        if(it.messageCode== Utils.MINUS_ONE){
                            onCallBack(true)
                        }else onCallBack(false)

                    },
                    {
                        showSimpleProgress(false, progressBar)
                        showErrorMsg(it, "stockTakingCount", context)
                        onError(isStockMessageEqualMinusOne(it,"stockMessageCode"))
                    },
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