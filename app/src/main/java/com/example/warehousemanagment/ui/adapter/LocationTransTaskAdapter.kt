package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternLocationTransferTaskBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.transfer_task.location_transfer.LocationTransferRow
import com.example.warehousemanagment.model.models.transfer_task.location_transfer.LocationTransferTaskModel

class LocationTransTaskAdapter(): RecyclerView.Adapter<LocationTransTaskAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<LocationTransferRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<LocationTransferRow>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
            this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternLocationTransferTaskBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.uomTitle.setText(context.getString(R.string.uom))

        holder.b.locationTo.text=model.destinationLcationCode
        holder.b.locationFrom.text=model.sourceLocationCode
        holder.b.productCode.text=model.productCode
        holder.b.productTitle.text=model.productTitle
        holder.b.ownerCode.text=model.ownerCode

        holder.b.quantity.text=model.quantity.toString()
        holder.b.invType.text=model.invTypeTitle


        holder.b.rel5.availableinventory.text=model.availableInventory.toString()
        holder.b.rel5.realinventory.text=model.realInventory.toString()
        holder.b.uomTv.text=model.uOMName
        holder.b.date.text=model.taskTimeString


        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }
        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
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


    class MyViewHolder(b:PatternLocationTransferTaskBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternLocationTransferTaskBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: LocationTransferRow)
        fun reachToEnd(position: Int)
    }
}