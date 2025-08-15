package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternSerialPickingDetailScanBinding
import com.example.warehousemanagment.model.models.shipping.SerialBaseShippingSerialRow

class SerialBaseShippingSerialAdapter(
    var list: List<SerialBaseShippingSerialRow>,
    val context: Context,
    val onReachEnd: ()->Unit
): RecyclerView.Adapter<SerialBaseShippingSerialAdapter.MyViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternSerialPickingDetailScanBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model= list[position]
        holder.b.title.text = model.serialNumber


        holder.b.tick.backgroundTintList =
            if (model.isScanInShip)ContextCompat.getColorStateList(context,R.color.green)
            else ContextCompat.getColorStateList(context,R.color.white)

        if (position==list.size-1 && list.size>= 100){
            onReachEnd()
        }

    }



    override fun getItemCount(): Int {
        return list.size
    }


    fun <T> setFilter(newList: List<T>)
    {
        list =ArrayList()
        (list as ArrayList<T>).addAll(newList)
        notifyDataSetChanged()
    }


    class MyViewHolder(var b: PatternSerialPickingDetailScanBinding): RecyclerView.ViewHolder(b.root)
}
