package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternCargoDetailBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.cargo_folder.cargo_detail.CargoDetailRow

class CargoDetailAdapter(): RecyclerView.Adapter<CargoDetailAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<CargoDetailRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<CargoDetailRow>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternCargoDetailBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model= arrayList[position]

        holder.b.customerFullName.text=model.customerFullName
        holder.b.ownerCode.text=model.ownerCode
        holder.b.invType.text=model.invTypeTitle
        holder.b.productTitle.text=model.productTitle
        holder.b.productCode.text=model.productCode
        holder.b.count.text=model.quantity.toString()
        holder.b.locationCode?.text=model.locationCode
        holder.b.hasPriority?.text=model.hasLowPriorityTitle

        holder.b.isDone?.visibility = if (model.isDone) View.VISIBLE else View.GONE

        holder.b.doneByLay?.visibility = if(model.isDone) View.VISIBLE else View.GONE
        holder.b.doneBy?.text = model.doneBy


        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model,position)
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


    class MyViewHolder(b:PatternCargoDetailBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternCargoDetailBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: CargoDetailRow,position: Int)
        fun reachToEnd(position: Int)
    }
}
