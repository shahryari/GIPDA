package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.transfer_task.PalletModel
import com.example.warehousemanagment.model.models.without_master.ProductWithoutMasterModel
import com.example.warehousemanagment.model.models.without_master.UnitOfMeasureSubmitModel
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class ProductWithoutMasterViewModel(application: Application,context: Context)
    :AndroidViewModel(application)
{
    private val repository=MyRepository()
    private var context: Context=context
    private var productWithoutMaster=MutableLiveData<List<ProductWithoutMasterModel>>()
    private var palletList=MutableLiveData<List<PalletModel>>()
    private var tempList=ArrayList<ProductWithoutMasterModel>()
    private var unitOfMeasure=MutableLiveData<UnitOfMeasureSubmitModel>()

    fun clearProductWithoutMaster(){
        if (tempList.size!=0)
        {
            tempList.clear()
            productWithoutMaster.value=tempList
        }

    }
    fun getUnitOfMeasure(): MutableLiveData<UnitOfMeasureSubmitModel> {
        return unitOfMeasure
    }

    fun getProductWithoutMaster(): MutableLiveData<List<ProductWithoutMasterModel>>
    {
        return productWithoutMaster
    }

    fun getPallet(): MutableLiveData<List<PalletModel>>
    {
        return palletList
    }
    fun setProductWithoutMaster(
        baseUrl:String,
        searchKeyboard: String,
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
            jsonObject.addProperty("SearchKeyword",searchKeyboard)
            repository.
            getProductWithoutMaster(baseUrl,jsonObject, page, rows, sort, order, cookie).subscribe({
                showSimpleProgress(false,progressBar)
                swipeLayout.isRefreshing=false
                if (it.size!=0)
                {
                    tempList.addAll(it)
                    productWithoutMaster.value=(tempList)
                }

                log("ProductWithoutMaster",tempList.size.toString())
            },{
                swipeLayout.isRefreshing=false
                showSimpleProgress(false,progressBar)
                showErrorMsg(it,"ProductWithoutMaster",context)
            }).let {  }
        }
    }

    fun setPalletList(
        baseUrl:String,cookie: String)
    {
        viewModelScope.launch()
        {
            repository.palletList(baseUrl,cookie).subscribe(
            {
                palletList.value=(it)
                log("PalletList",it.toString())
            },{
                showErrorMsg(it,"PalletList",context)
            }).let {  }
        }
    }
    fun setUnitOfMeasure(
        baseUrl:String,
        goodId: String, width: Int, height: Int, length: Int,weight: Float, palletId: String,
        palletLayer: Int, quantityPerLayer: Int, cookie: String, progress: ProgressBar
    )
    {
        viewModelScope.launch()
        {
            showSimpleProgress(true,progress)
            val jsonObject=JsonObject()
            jsonObject.addProperty("GoodID",goodId)
            jsonObject.addProperty("Width",width)
            jsonObject.addProperty("Height",height)
            jsonObject.addProperty("Length",length)
            jsonObject.addProperty("TotalWeight",weight)
            jsonObject.addProperty("PalletID",palletId)
            jsonObject.addProperty("PalletLayer",palletLayer)
            jsonObject.addProperty("QuantityPerLayer",quantityPerLayer)

            log("unitOfMeasure",jsonObject.toString())
            repository.unitOfMeasureSubmtit(baseUrl,jsonObject, cookie).subscribe(
                {
                    showSimpleProgress(false,progress)
                    unitOfMeasure.value=(it)
                    log("unitOfMeasure",it.toString())
                },{
                    showSimpleProgress(false,progress)
                    showErrorMsg(it,"unitOfMeasure",context)
                }).let {  }
        }
    }



}