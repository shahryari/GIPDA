package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.logErr
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.notif.NotificationModel
import io.reactivex.Single

class MainViewModel(application: Application,): AndroidViewModel(application)
{
    private val repository=MyRepository()

    fun notif(
        baseUrl:String,cookie: String,callBack:(resp:NotificationModel)->Unit) {
        repository.notif(baseUrl,cookie).subscribe(
            {
                log("notif",it.toString())
                if (it.size!=0)
                callBack(it.get(0))
            },{

                logErr("notif",it.toString())
         }).let {  }
    }



}