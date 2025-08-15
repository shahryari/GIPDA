package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.showErrorMsg
import com.example.warehousemanagment.model.classes.showSimpleProgress
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.stock.StockTurnItemLocationRow
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class StockTurnReportViewModel(application: Application):
    AndroidViewModel(application)
{
    private val repository=MyRepository()

    private val stockTurnItemList = MutableLiveData<List<StockTurnItemLocationRow>>()
    private val tempList = arrayListOf<StockTurnItemLocationRow>()
    private val count = MutableLiveData<Int>()


    fun getStockTurnItemList() : MutableLiveData<List<StockTurnItemLocationRow>> {
        return stockTurnItemList
    }

    fun clearList() {
        tempList.clear()
        stockTurnItemList.value = tempList
    }

    fun getCount() = count

    fun setStockTurnItemList(
        baseUrl: String,
        keyword: String,
        stockTurnTeamLocationID: String,
        page: Int,
        order: String,
        cookie: String,
        progressBar: ProgressBar,
        swipeRefreshLayout: SwipeRefreshLayout,
        context: Context
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("Keyword",keyword)
        jsonObject.addProperty("StockTurnTeamLocationID", stockTurnTeamLocationID)
        showSimpleProgress(true,progressBar)
        viewModelScope.launch {
            repository.stockTurnItemLocation(
                baseUrl,jsonObject,page,10,"CreatedOn",order,cookie
            ).subscribe(
                {
                    showSimpleProgress(false,progressBar)
                    swipeRefreshLayout.isRefreshing = false
                    if (it.rows?.isNotEmpty() == true){
                        tempList.addAll(it.rows)
                        stockTurnItemList.value = tempList
                    }
                    count.value = it.total
                },
                {
                    showSimpleProgress(false,progressBar)
                    swipeRefreshLayout.isRefreshing = false
                    showErrorMsg(it,"stock turn location",context,)
                }
            )
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