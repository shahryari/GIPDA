package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternDestinationLocationBinding
import com.example.warehousemanagment.databinding.PatternSerialBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.receive.receiving_detail_serials.ReceivingDetailSerialModel
import com.example.warehousemanagment.model.models.shipping.ShippingSerialModel
import com.example.warehousemanagment.model.models.transfer_task.DestinyLocationTransfer

class DestinyLocationAdapter(): RecyclerView.Adapter<DestinyLocationAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<DestinyLocationTransfer> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener
    constructor(arrayList:List<DestinyLocationTransfer>, context: Context,
                onCallBackListener: OnCallBackListener):this()
    {
        this.context=context
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternDestinationLocationBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model=arrayList.get(position)
        holder.b.title.text=model.locationCode

        holder.itemView.setOnClickListener{
            onCallBackListener.onItemClick(model)
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


    class MyViewHolder(b:PatternDestinationLocationBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternDestinationLocationBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onItemClick(model:DestinyLocationTransfer)
    }
}
