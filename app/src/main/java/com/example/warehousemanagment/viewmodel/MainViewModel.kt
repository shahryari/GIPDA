package com.example.warehousemanagment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.logErr
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.VersionInfoModel
import com.example.warehousemanagment.model.models.notif.NotificationModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application,): AndroidViewModel(application)
{
    private val repository=MyRepository()
    private val updateInfo : MutableLiveData<VersionInfoModel> = MutableLiveData()

    fun notif(
        baseUrl:String,cookie: String,callBack:(resp:NotificationModel)->Unit) {
        repository.notif(baseUrl,cookie).subscribe(
            {
                log("notif",it.toString())
                if (it.isNotEmpty())
                    callBack(it[0])
            },{

                logErr("notif",it.toString())
         }).let {  }
    }

    fun getUpdateInfo(
        baseUrl: String,
        cookie: String
    ) {
        viewModelScope.launch {
            repository.getCurrentVersionInfo(baseUrl,cookie).subscribe(
                {
                    updateInfo.value = it
                },{
                    log("version_info","get version info")
                }
            )
        }
    }

    fun getUpdateInfo() : LiveData<VersionInfoModel> {
        return updateInfo
    }



}