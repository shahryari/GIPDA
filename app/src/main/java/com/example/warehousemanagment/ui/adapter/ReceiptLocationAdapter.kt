package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternSerialPutawayLocationBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.putaway.serial_putaway.ReceiptDetailLocationRow

class ReceiptLocationAdapter(
    val list: List<ReceiptDetailLocationRow>,
    val context: Context,
    val onReachToEnd: (Int)->Unit
) : RecyclerView.Adapter<ReceiptLocationAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: PatternSerialPutawayLocationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PatternSerialPutawayLocationBinding.inflate(LayoutInflater.from(context))
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.receiptNumber.text = model.locationCode
        holder.binding.total.text = model.quantity.toString()
        if (position==list.size-1 && list.size>= Utils.ROWS){
            onReachToEnd(position)
        }
    }
}