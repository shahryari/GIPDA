package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternWarehouseBinding
import com.example.warehousemanagment.model.models.insert_serial.WarehouseModel

class WareHouseAdapter(): RecyclerView.Adapter<WareHouseAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<WarehouseModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(arrayList:List<WarehouseModel>,context: Context, onCallBackListener: OnCallBackListener ):this()
    {
        this.context=context
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternWarehouseBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model=arrayList.get(position)
        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }
        holder.b.wareHouseName.text=model.warehouseName
        holder.b.warehouseCode.text=model.warehouseCode





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



    class MyViewHolder(b:PatternWarehouseBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternWarehouseBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model:WarehouseModel)
    }
}
