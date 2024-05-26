package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternReceivingBinding
import com.example.warehousemanagment.databinding.PatternSerialBinding
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.models.insert_serial.InsertSerialModel
import com.example.warehousemanagment.model.models.insert_serial.InsertedSerialModel
import com.example.warehousemanagment.model.models.receive.receiving.ReceivingModel

class InsertedSerialAdapter(): RecyclerView.Adapter<InsertedSerialAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<InsertedSerialModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<InsertedSerialModel>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
            this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternSerialBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.excel.visibility=View.GONE
        holder.b.title.setText(model.serialNumber)

        holder.b.delete.setOnClickListener {
            onCallBackListener.onDeleteSerial(model)
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


    class MyViewHolder(b:PatternSerialBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternSerialBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onDeleteSerial(model: InsertedSerialModel)
    }
}
