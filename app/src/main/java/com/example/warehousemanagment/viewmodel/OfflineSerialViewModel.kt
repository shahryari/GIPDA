package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.warehousemanagment.model.models.receive.receiving_detail_serials.ReceivingDetailSerialModel

class OfflineSerialViewModel(application: Application,context: Context):AndroidViewModel(application)
{
    private var liveSerials=MutableLiveData<List<ReceivingDetailSerialModel>>()
    private var serialList=ArrayList<ReceivingDetailSerialModel>()

    fun getSerials(): MutableLiveData<List<ReceivingDetailSerialModel>> {
        return liveSerials
    }
    fun clearSerials(){
        serialList.clear()
        liveSerials.postValue(serialList)
    }
    fun removeSerial(serialModel: ReceivingDetailSerialModel){
        serialList.remove(serialModel)
        liveSerials.postValue(serialList)
    }
    fun addSerial(
        serialModel: ReceivingDetailSerialModel,
        disallowRepeat: Boolean,
        repetitive:()->Unit
    ){
        if (disallowRepeat){
            if (!serialList.contains(serialModel))
            {
                serialList.add(serialModel)
                liveSerials.postValue(serialList)
            }else{
                repetitive()
            }
        } else {
            serialList.add(serialModel)
            liveSerials.postValue(serialList)
        }

    }

    fun sortAsc() {
        serialList.sortBy { it.serialNumber } // replace "serialNumber" with the property you want to sort by
        liveSerials.postValue(serialList)
    }
    fun sortDesc() {
        serialList.sortByDescending { it.serialNumber } // replace "serialNumber" with the property you want to sort by
        liveSerials.postValue(serialList)
    }








}