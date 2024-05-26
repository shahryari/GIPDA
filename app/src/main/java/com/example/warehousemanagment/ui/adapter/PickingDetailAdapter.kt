package com.example.warehousemanagment.ui.adapter

import PickingDetailRow
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternPickingDetailBinding
import com.example.warehousemanagment.databinding.PatternReceivingDetailInfoBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.receive.receiveDetail.ReceiveDetailRow

class PickingDetailAdapter(): RecyclerView.Adapter<PickingDetailAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private var serialList: List<PickingDetailRow> = ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(serialList: List<PickingDetailRow>,context: Context,
                onCallBackListener: OnCallBackListener):this() {
        this.serialList=serialList
        this.context=context
        this.onCallBackListener=onCallBackListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternPickingDetailBinding.inflate(LayoutInflater.from(context)
            ,parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=serialList.get(position)

        holder.b.productTitle.text=model.productTitle
        holder.b.productCode.text=model.productCode
        holder.b.invTypeTitle.text=model.invTypeTitle
        holder.b.ownerCode.text=model.ownerCode
        holder.b.quantity.text=model.sumQuantity.toString()

        holder.itemView.setOnClickListener{
            onCallBackListener.onItemClick(model)
        }

        if (position==serialList.size-1  && serialList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }
    }



    override fun getItemCount(): Int {
        return serialList.size
    }

    fun <T> setFilter(newList: List<T>)
    {
        serialList =ArrayList()
        (serialList as ArrayList<T>).addAll(newList)
        notifyDataSetChanged()
    }



    class MyViewHolder(b:PatternPickingDetailBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternPickingDetailBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onItemClick(
            model:PickingDetailRow
        )


        fun reachToEnd(position: Int)
    }
}
