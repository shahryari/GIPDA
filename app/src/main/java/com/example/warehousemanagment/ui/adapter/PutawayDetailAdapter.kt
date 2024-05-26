package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternPutawayDetailBinding
import com.example.warehousemanagment.databinding.PatternReceivingDetailInfoBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.putaway.truck_detail.PutawayDetailRow
import com.example.warehousemanagment.model.models.putaway.truck_detail.PutawayTruckDetailModel

class PutawayDetailAdapter(): RecyclerView.Adapter<PutawayDetailAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<PutawayDetailRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList:List<PutawayDetailRow>,
                context: Context, onCallBackListener: OnCallBackListener):this() {
        this.arrayList=arrayList
        this.context=context
        this.onCallBackListener=onCallBackListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternPutawayDetailBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model=arrayList.get(position)
        holder.b.locationCode.text=model.locationCode
        holder.b.productTitle.text=model.productTitle
        holder.b.productCode.text=model.productCode
        holder.b.quantity.text=model.quantity.toString()
        holder.b.dockCode.text=model.dockCode


        //goodSet and receiveingId have remainded






        holder.itemView.setOnClickListener { onCallBackListener.onClick(model) }

        if (position==arrayList.size-1  && arrayList.size>=Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }
    }



    override fun getItemCount(): Int {
        return arrayList.size
    }




    class MyViewHolder(b:PatternPutawayDetailBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternPutawayDetailBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model:PutawayDetailRow)
        fun reachToEnd(position: Int)
    }
}
