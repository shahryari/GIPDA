package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternShippingDetailBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailRow

class ShipingDetailAdapter(): RecyclerView.Adapter<ShipingDetailAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<ShippingDetailRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList:List<ShippingDetailRow>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList=arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternShippingDetailBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model= arrayList[position]

        onCallBackListener.init(holder.b)
        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }
        holder.b.count.text=model.quantity.toString()
        holder.b.shippingCount.text=model.serialScanCount.toString()
        holder.b.customerFullName.text=model.customerFullName
        holder.b.productTitle.text=model.productTitle
        holder.b.productCode.text=model.productCode
        holder.b.ownerCode.text=model.ownerCode
        holder.b.date.text=model.taskTimeString

        holder.b.colorLay.visibility = View.VISIBLE
        holder.b.customerColor.backgroundTintList = ColorStateList.valueOf(if (model.customerColorCode!=null )
            Color.parseColor(model.customerColorCode)
        else
            Color.TRANSPARENT)
        holder.b.customerName.text = model.customerFullName

        holder.b.lin1.visibility = View.GONE

        holder.b.relClose.setOnClickListener {
            onCallBackListener.onCloseClick(model)
        }

        if (!model.serializable)
        {
            holder.b.imgBackground.visibility=View.INVISIBLE
        } else{
            holder.b.imgBackground.visibility=View.VISIBLE
        }

        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }

        if (model.cancelCount==null || model.cancelCount==0){
            holder.b.relCancelCount.visibility=View.INVISIBLE
        }else holder.b.cancelCount.setText(model.cancelCount.toString())



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



    class MyViewHolder(b:PatternShippingDetailBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternShippingDetailBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model:ShippingDetailRow)
        fun reachToEnd(position: Int)
        fun onCloseClick(model: ShippingDetailRow)
        fun init(binding:PatternShippingDetailBinding)
    }
}
