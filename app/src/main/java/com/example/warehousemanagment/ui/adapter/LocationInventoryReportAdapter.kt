package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternPickputReportBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.report_inventory.report_location.LocationInventoryRows
import com.example.warehousemanagment.model.models.report_inventory.report_location.ReportLocationInventory

class LocationInventoryReportAdapter(): RecyclerView.Adapter<LocationInventoryReportAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<LocationInventoryRows> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<LocationInventoryRows>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
            this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternPickputReportBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.title1.visibility=View.INVISIBLE
        holder.b.tv1.visibility=View.INVISIBLE
        holder.b.productCode.text=model.productCode
        holder.b.productTitle.text=model.productTitle
        holder.b.locationCode.text=model.locationCode
        holder.b.ownerCode.text=model.owner
        holder.b.invType.text=model.invTypeTitle
        holder.b.uomTv.text=model.uomName

        holder.b.ownerTitle.text=context.getString(R.string.owner)

        holder.b.relCount.availableinventory.text=model.availableInventory.toString()
        holder.b.relCount.realinventory.text=model.realInventory.toString()


        holder.itemView.setOnClickListener {
            onCallBackListener.onClick()
        }

        if (position==arrayList.size-1 && arrayList.size>=Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }

    }



    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun <T> setFilter(newList: List<T>)
    {
         arrayList =ArrayList()
        (arrayList as ArrayList<T>).addAll(newList)
        notifyDataSetChanged()
    }


    class MyViewHolder(b:PatternPickputReportBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternPickputReportBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick()
        fun reachToEnd(position: Int)
    }
}
