package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternPalletBinding
import com.example.warehousemanagment.model.models.transfer_task.PalletModel

class PalletAdapter(): RecyclerView.Adapter<PalletAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<PalletModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(arrayList:List<PalletModel>,context: Context, onCallBackListener: OnCallBackListener ):this()
    {
        this.context=context
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternPalletBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model=arrayList.get(position)
        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }
        holder.b.palletName.text=model.palletName
        holder.b.palletCode.text=model.palletCode





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



    class MyViewHolder(b:PatternPalletBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternPalletBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model:PalletModel)
    }
}
