package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.warehousemanagment.databinding.PatternLocationTransferBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.serial_transfer.SerialTransferProductRow

class SerialTransferAdapter() : Adapter<SerialTransferAdapter.MyViewHolder>() {
    private lateinit var context: Context
    private  var arrayList:List<SerialTransferProductRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<SerialTransferProductRow>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view= PatternLocationTransferBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.line4.visibility = View.GONE
        holder.b.ownerCode.visibility = View.GONE
        holder.b.warehouse.visibility = View.GONE
        holder.b.locationCode.text=model.locationCode
        holder.b.productTitle.text=model.productName
        holder.b.productCode.text=model.productCode
        holder.b.typeTitle.text = "Warehouse Code"
        holder.b.locationTypeTitle.text = "Warehouse Name"
        holder.b.locationType.text = model.warehouseName
        holder.b.type.text = model.warehouseCode
//        holder.b.locationType.text=model.locationTypeTitle
//        holder.b.ownerCode.text=model.ownerCode

        holder.b.rel1.realinventory.text=model.realInventory.toString()
        holder.b.rel1.availableinventory.text=model.availableInventory.toString()


        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }
        if (position==arrayList.size-1  && arrayList.size>= Utils.ROWS){
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


    class MyViewHolder(b: PatternLocationTransferBinding): RecyclerView.ViewHolder(b.root){
        var b: PatternLocationTransferBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: SerialTransferProductRow)
        fun reachToEnd(position: Int)
    }
}