package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternReceivingBinding
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.shipping.shipping_truck.ShippingTruckRow
import com.squareup.picasso.Picasso

class ShippingAdapter(): RecyclerView.Adapter<ShippingAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<ShippingTruckRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<ShippingTruckRow>,
                context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternReceivingBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model= arrayList[position]
        holder.b.driverFullName.text = model.driverFullName
        holder.b.date.text = model.dockAssignTimeString
        holder.b.recevieNumber.text=model.shippingNumber
        holder.b.containerNumber.text=model.bOLNumber



        holder.b.title2.text=context.getString(R.string.bolNumber)

        holder.b.title1.text=context.getString(R.string.shippingNumber2)

        holder.b.dockCode.text=model.dockCode
        holder.b.type.text=model.carTypeTitle
        holder.b.plaque.text = getBuiltString(
            model.plaqueNumberThird,
            model.plaqueNumberSecond, model.plaqueNumberFirst
        )
        holder.b.dateTitle.text=context.getString(R.string.dockAssignTime)

        holder.b.customerCount.text = model.customerCount.toString()

        holder.b.CurrentStatusCode.visibility=View.VISIBLE
        holder.b.CurrentStatusCodeTitle.visibility=View.VISIBLE
        holder.b.CurrentStatusCode.text=model.currentStatusCode

        holder.b.qtyLay.visibility = View.VISIBLE
        holder.b.totalTv.text = model.total.toString()
        holder.b.doneTv.text = model.doneCount.toString()
        holder.b.qtyTv.text = model.sumQuantity.toString()
        holder.b.doneQtyTv.text = model.sumDonQuantity.toString()

        holder.b.plaqueYear.text=model.plaqueNumberFourth

        onCallBackListener.init(holder.b, model.currentStatusCode?:"")

        holder.b.leftDock.setOnClickListener {
            onCallBackListener.onLeftDockClick(
                model=model
            )
        }


        holder.itemView.setOnClickListener()
        {
            onCallBackListener.onClick(model,position)
        }
        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }

        if (model.driverImageUrl!=null)
            Picasso.get().load(model.driverImageUrl).into(holder.b.img)


        if (model.hasCheckTruck) {
            holder.b.imgBackground.visibility= View.VISIBLE
        } else {
            holder.b.imgBackground.visibility = View.GONE
        }

        holder.b.remove.visibility = if (model.currentStatusCode == "CarControl") View.VISIBLE else View.GONE
        holder.b.remove.setOnClickListener {
            onCallBackListener.onTruckLoadingRemove(model.shippingAddressID)
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


    class MyViewHolder(b:PatternReceivingBinding): RecyclerView.ViewHolder(b.root)
    {
        var b:PatternReceivingBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun init(binding: PatternReceivingBinding, currentStatusCode: String)
        fun onClick(model: ShippingTruckRow, position: Int)
        fun reachToEnd(position: Int)

        fun onLeftDockClick(model: ShippingTruckRow)

        fun onTruckLoadingRemove(shippingAddressId: String)
    }
}
