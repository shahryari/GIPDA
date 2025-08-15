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
import com.example.warehousemanagment.model.models.putaway.truck.PutawayTruckRow
import com.squareup.picasso.Picasso

class PutAwayAdapter(): RecyclerView.Adapter<PutAwayAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<PutawayTruckRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<PutawayTruckRow>, context: Context, onCallBackListener: OnCallBackListener):this() {
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
        holder.b.recevieNumber.text = model.receivingNumber
        holder.b.driverFullName.text = model.driverFullName
        holder.b.dockCode.text = model.dockCode
        holder.b.date.text = model.taskTimeString
        holder.b.dateTitle.text = context.getString(R.string.dockAssignTime)
        holder.b.type.text = model.carTypeTitle
        holder.b.containerNumber.text = model.containerNumber

        holder.b.plaque.text = getBuiltString(model.plaqueNumberThird,
            model.plaqueNumberSecond,model.plaqueNumberFirst)
        holder.b.plaqueYear.text=model.plaqueNumberFourth


        holder.b.ownerNameTitle.visibility = View.VISIBLE
        holder.b.ownerName.visibility = View.VISIBLE
        holder.b.ownerName.text = "${model.ownerName?:""} ${model?.ownerCode?.let { "($it)" } ?: ""}"

        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model,position)
        }
        if (position==arrayList.size-1 && arrayList.size>=Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }


        if (model.driverImageUrl!=null)
            Picasso.get().load(model.driverImageUrl).into(holder.b.img)


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


    class MyViewHolder(b:PatternReceivingBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternReceivingBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: PutawayTruckRow, position: Int)
        fun reachToEnd(position: Int)
    }
}
