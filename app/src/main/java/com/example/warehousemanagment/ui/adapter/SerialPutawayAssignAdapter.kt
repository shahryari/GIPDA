package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternSerialPutawayBinding
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.putaway.serial_putaway.SerialReceiptOnPutawayRow

class SerialPutawayAssignAdapter() : RecyclerView.Adapter<SerialPutawayAssignAdapter.MyViewHolder>() {
    private lateinit var context: Context
    private  var arrayList:List<SerialReceiptOnPutawayRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<SerialReceiptOnPutawayRow>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternSerialPutawayBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model= arrayList[position]
        holder.b.lastName.visibility= View.GONE
        holder.b.recevieNumber.text = model.receiptNumber
        holder.b.driverFullName.text = model.containerNumber
        holder.b.date.text = model.createdOnString

        holder.b.type.text = model.plaqueNumber
        holder.b.containerNumber.text = model.containerNumber
        holder.b.lastName.text = model.currentStatusTitle


        holder.b.plaque.text = getBuiltString(model.plaqueNumber)

        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model,position)
        }
        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }
//
//        if (model!=null)
//            Picasso.get().load(model.driverImageUrl).into(holder.b.img)


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


    class MyViewHolder(var b: PatternSerialPutawayBinding): RecyclerView.ViewHolder(b.root)

    interface OnCallBackListener{
        fun onClick(model: SerialReceiptOnPutawayRow, position: Int)
        fun reachToEnd(position: Int)
    }
}