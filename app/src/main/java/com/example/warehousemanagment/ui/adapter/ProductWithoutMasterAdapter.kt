package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternLocationTransferBinding
import com.example.warehousemanagment.databinding.PatternProductWithoutTransferBinding
import com.example.warehousemanagment.databinding.PatternReceivingBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.receive.receiving.ReceivingModel
import com.example.warehousemanagment.model.models.without_master.ProductWithoutMasterModel

class ProductWithoutMasterAdapter(): RecyclerView.Adapter<ProductWithoutMasterAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<ProductWithoutMasterModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<ProductWithoutMasterModel>,
                context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
            this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternProductWithoutTransferBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model=arrayList.get(position)
        holder.b.productCode.text=model.productCode
        holder.b.productGroup.text=model.productGroup
        holder.b.productTitle.text=model.productTitle
        holder.b.owner.text=model?.ownerCode?.toString()
        holder.b.date.text=model.createdOnString

        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }

        if (position==arrayList.size-1){
            onCallBackListener.reachToEnd(position)
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


    class MyViewHolder(b:PatternProductWithoutTransferBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternProductWithoutTransferBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: ProductWithoutMasterModel)
        fun reachToEnd(position: Int)
    }
}
