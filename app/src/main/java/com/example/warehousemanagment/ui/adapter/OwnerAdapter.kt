package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternWarehouseBinding
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel

class OwnerAdapter(): RecyclerView.Adapter<OwnerAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<OwnerModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(arrayList:List<OwnerModel>,context: Context, onCallBackListener: OnCallBackListener ):this()
    {
        this.context=context
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternWarehouseBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }
        holder.b.tv1.text=context.getString(R.string.ownerCode)
        holder.b.tv2.text=context.getString(R.string.OwnerInfoFullName)

        holder.b.wareHouseName.text=model.ownerCode
        holder.b.warehouseCode.text=model.ownerInfoFullName
//        holder.b.lin2.visibility= View.GONE


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
        fun onClick(model:OwnerModel)
        fun init(b: PatternWarehouseBinding)
    }
}
