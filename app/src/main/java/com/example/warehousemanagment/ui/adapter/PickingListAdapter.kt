package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternPickingBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.picking.picking.PickingRow
import com.squareup.picasso.Picasso

class PickingListAdapter():
    RecyclerView.Adapter<PickingListAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<PickingRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(arrayList:List<PickingRow>, context: Context,
                onCallBackListener: OnCallBackListener ):this()
    {
        this.context=context
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternPickingBinding.
        inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model=arrayList.get(position)
        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }

        holder.b.quantity.text=model.sumQuantity.toString()
        holder.b.locationCode.text=model.locationCode


        if (position==arrayList.size-1 && arrayList.size>=Utils.ROWS){
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



    class MyViewHolder(b:PatternPickingBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternPickingBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: PickingRow)
        fun reachToEnd(position: Int)
    }
}
