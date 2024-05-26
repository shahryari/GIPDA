package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternSerialInventoryBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.report_inventory.serial_inventory_product.SerialInvProductRows


class SerialInventoryReportProductAdapter():
    RecyclerView.Adapter<SerialInventoryReportProductAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<SerialInvProductRows> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<SerialInvProductRows>, context: Context,
                onCallBackListener: OnCallBackListener):this()
    {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternSerialInventoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.linGen3.visibility= View.VISIBLE
        holder.b.productCode.text=model.productCode
        holder.b.productTitle.text=model.productTitle
        holder.b.invType.text=model.invTypeTitle
        holder.b.ownerCode.text=model.ownerCode
        holder.b.availableinventory.text=model.availableInventory.toString()
        holder.b.realinventory.text=model.realInventory.toString()


        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model.productID)
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


    class MyViewHolder(b: PatternSerialInventoryBinding): RecyclerView.ViewHolder(b.root){
        var b: PatternSerialInventoryBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(productId: String)
        fun reachToEnd(position: Int)
    }
}
