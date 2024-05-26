package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.insert_serial.InsertedSerialModel
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.tracking.GetSerialInfoModel
import com.example.warehousemanagment.model.models.tracking.LabellingModel
import com.example.warehousemanagment.model.models.tracking.TrackProductModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import kotlinx.coroutines.launch

class TrackingViewModel(application: Application,context: Context):AndroidViewModel(application)
{
    private val repository= MyRepository( )
    private var context: Context=context

    private var productList= MutableLiveData<List<ProductModel>>()
    private var serialsList=MutableLiveData<List<GetSerialInfoModel>>()
    private var tempSerialList:ArrayList<GetSerialInfoModel> = ArrayList()

    fun getProductList(): MutableLiveData<List<ProductModel>> {
        return productList
    }

    fun getSerialsList():MutableLiveData<List<GetSerialInfoModel>>
    {
        return serialsList
    }


    private var labelling= MutableLiveData<LabellingModel>()

    fun getLabellingData(): MutableLiveData<LabellingModel> {
        return labelling
    }

    fun labelling(
        baseUrl:String,progressBar: ProgressBar, cookie: String, callBack:()->Unit)
    {
        if (tempSerialList.size==0)
        {
            toast(context.getString(R.string.serialListEmpty),context)
        }else
        {
            val jsonArray= JsonArray()
            for (model in tempSerialList)
            {
                val jsonObject=JsonObject()
                jsonObject.addProperty("ProductCode",model.goodsSystemCode)
                jsonObject.addProperty("SerialNumber",model.serialNumber)

                jsonArray.add(jsonObject)
            }

            log("insertArray",jsonArray.toString())
            viewModelScope.launch()
            {
                showSimpleProgress(true,progressBar)
                repository.labelling(baseUrl,jsonArray,cookie)
                    .subscribe({
                        showSimpleProgress(false,progressBar)
                        log("labelling", it.toString())

                        clearList()

                        callBack()


                    }, {
                        showSimpleProgress(false,progressBar)
                        showErrorMsg(it,"labelling",context)
                    },).let {  }
            }
        }

    }

    private fun clearList() {
        tempSerialList.clear()
        serialsList.value = tempSerialList
    }


    fun removeSerial(model:GetSerialInfoModel)
    {
        tempSerialList.remove(model)
        serialsList.value=tempSerialList
    }

    fun setSerialInfo(
        baseUrl:String,
        productCode: String,
        serialNumber: String,
        cookie: String,
        progress: ProgressBar
    )
    {
        val jsonObject=JsonObject()
        jsonObject.addProperty("SerialNumber",serialNumber)
        jsonObject.addProperty("ProductCode",productCode)
        showSimpleProgress(true,progress)
        repository.getSerialInfo(baseUrl,jsonObject,cookie)
            .subscribe(
                {
                    showSimpleProgress(false,progress)
                    tempSerialList.add(it)
                    serialsList.value=tempSerialList
                    log("SerialInfo", it.toString())

                }, {
                    showSimpleProgress(false,progress)
                    showErrorMsg(it,"SerialInfo",context)
                }).let {  }

    }

    fun setTrackingProducts(
        baseUrl:String,productTitle:String, cookie: String)
    {
        val jsonObject=JsonObject()
        jsonObject.addProperty(Utils.ProductTitle,productTitle)
        repository.getTrackingProducts(baseUrl,jsonObject,cookie)
            .subscribe(
                {
                    productList.value=it
                    log("TrackingProduct", it.toString())

                }, {
                    showErrorMsg(it,"TrackingProduct",context)
                }).let {  }

    }


}