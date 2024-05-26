package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.BarcodeModel
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.login.login.LoginModel
import com.example.warehousemanagment.model.models.notif.NotificationModel
import com.example.warehousemanagment.model.models.rework.ReworkModel
import com.google.gson.JsonObject
import com.google.zxing.BarcodeFormat
import io.reactivex.Single
import kotlinx.coroutines.launch

class ReworkViewModel(application: Application,context: Context): AndroidViewModel(application)
{
    private var context: Context=context
    private val repository=MyRepository()


    fun invList(context: Context): List<CatalogModel> {
        val invList = ArrayList<CatalogModel>()
        invList.add(CatalogModel(valueField =1 , title = context.getString(R.string.tina)))
        invList.add(CatalogModel(valueField =2 , title = context.getString(R.string.otherFirms)))
        return invList
    }


    fun rework(
        baseUrl:String,serialNumber:String,ReworkSerialOwnerID:Int,token:String ,
        printName:String,
        tinaPaperSize:String,
        otherPaperSize:String,
        progressBar: ProgressBar, )
    {
        viewModelScope.launch()
        {
            val jsonObject= JsonObject()
            jsonObject.addProperty("ReworkSerialOwnerID",ReworkSerialOwnerID)
            jsonObject.addProperty("SerialNumber",serialNumber)
            jsonObject.addProperty("PrinterName",printName)
            jsonObject.addProperty("TinaPaperSize",tinaPaperSize)
            jsonObject.addProperty("OtherPaperSize",otherPaperSize)
            showSimpleProgress(true, progressBar)
            repository.rework(baseUrl,jsonObject,token)
                .subscribe(
                    {
                        showSimpleProgress(false, progressBar)

                        log("rework", it.toString())

                    },
                    {
                        showSimpleProgress(false, progressBar)
                        showErrorMsg(it, "rework", context)
//                        toast(it.toString(),context)
                    },
                ).let {}
        }

    }

    fun getBarcodeList(): List<BarcodeModel>
    {
        val list = ArrayList<BarcodeModel>()
        list.add(BarcodeModel(name ="CODABAR" , barcodeValue =BarcodeFormat.CODABAR, barcodeNumber = 1 ))
        list.add(BarcodeModel(name ="AZTEC" , barcodeValue =BarcodeFormat.AZTEC, barcodeNumber = 2 ))
        list.add(BarcodeModel(name ="CODE_128" , barcodeValue =BarcodeFormat.CODE_128, barcodeNumber = 3 ))
        list.add(BarcodeModel(name ="CODE_39" , barcodeValue =BarcodeFormat.CODE_39,barcodeNumber=4 ))
        list.add(BarcodeModel(name ="CODE_93" , barcodeValue =BarcodeFormat.CODE_93,barcodeNumber=5 ))
        list.add(BarcodeModel(name ="DATA_MATRIX" , barcodeValue =BarcodeFormat.DATA_MATRIX, barcodeNumber = 6 ))
        list.add(BarcodeModel(name ="EAN_13" , barcodeValue =BarcodeFormat.EAN_13, barcodeNumber = 7 ))
        list.add(BarcodeModel(name ="ITF" , barcodeValue =BarcodeFormat.ITF, barcodeNumber = 8 ))
        list.add(BarcodeModel(name ="PDF_417" , barcodeValue =BarcodeFormat.PDF_417, barcodeNumber = 9 ))
        list.add(BarcodeModel(name ="QR_CODE" , barcodeValue =BarcodeFormat.QR_CODE, barcodeNumber = 10 ))
        list.add(BarcodeModel(name ="MAXICODE" , barcodeValue =BarcodeFormat.MAXICODE, barcodeNumber = 11 ))
        list.add(BarcodeModel(name ="RSS_14" , barcodeValue = BarcodeFormat.RSS_14, barcodeNumber = 12))
        list.add(BarcodeModel(name ="RSS_EXPANDED" , barcodeValue =BarcodeFormat.RSS_EXPANDED, barcodeNumber = 13 ))
        list.add(BarcodeModel(name ="UPC_A" , barcodeValue =BarcodeFormat.UPC_A, barcodeNumber = 14 ))
        list.add(BarcodeModel(name ="UPC_E" , barcodeValue =BarcodeFormat.UPC_E, barcodeNumber = 15 ))
        list.add(BarcodeModel(name ="UPC_EAN_EXTENSION" ,
            barcodeValue =BarcodeFormat.UPC_EAN_EXTENSION, barcodeNumber = 16 ))
        return list

    }


}