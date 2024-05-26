package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.login.DashboardInfoModel
import io.reactivex.Observable
import kotlinx.coroutines.launch

class DesktopViewModel(application: Application,context: Context)
    : AndroidViewModel(application)
{

    private val repository= MyRepository( )
    private var dashBoardInfo= MutableLiveData<List<DashboardInfoModel>>()
    private var context: Context=context

//    var billDao: PermissionDao
//    init {
//        billDao= BillDatabase.getDatabase(application).billDao()
//    }


//    fun getPermissions():Permissions {
//        return billDao.readAllPermission()
//    }

    fun getDashboardInfo(): MutableLiveData<List<DashboardInfoModel>> {
        return dashBoardInfo
    }
    fun setDashboardInfo(
        baseUrl:String,cookie:String,checkErro:()->Unit)
    {
        viewModelScope.launch()
        {
            repository.dashboardInfo(baseUrl,cookie)
                .subscribe(
                    {
                        dashBoardInfo.value=(it)
                        log("dashBoardInfo", it.toString())
                    },
                    {
                        showErrorMsg(it, "dashBoardInfo", context)
                    },
                ).let { }
        }
    }
    fun setDashboard(
        baseUrl:String,cookie: String): Observable<List<DashboardInfoModel>> {
        return repository.dashboardInfo(baseUrl,cookie)
    }



}