package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.*
import com.example.warehousemanagment.model.models.insert_serial.ProductModel

class ProductAdapter(): RecyclerView.Adapter<ProductAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<ProductModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(arrayList:List<ProductModel>,context: Context, onCallBackListener: OnCallBackListener ):this()
    {
        this.context=context
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternProductBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }
        holder.b.ownerCode.text=model.ownerCode
        holder.b.productCode.text=model.productCode
        holder.b.productTitle.text=model.productTitle




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



    class MyViewHolder(b:PatternProductBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternProductBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model:ProductModel)
    }
}
