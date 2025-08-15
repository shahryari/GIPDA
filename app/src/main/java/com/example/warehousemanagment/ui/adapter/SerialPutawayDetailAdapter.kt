package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternSerialPutawayDetailBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.putaway.serial_putaway.MySerialReceiptDetailRow

class SerialPutawayDetailAdapter(
    private val detailList: List<MySerialReceiptDetailRow>,
    val context: Context,
    val onReachToEnd : (Int)->Unit,
    val onItemClick: (MySerialReceiptDetailRow)->Unit,
    val onAssignClick: (MySerialReceiptDetailRow)-> Unit
) : RecyclerView.Adapter<SerialPutawayDetailAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: PatternSerialPutawayDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PatternSerialPutawayDetailBinding.inflate(LayoutInflater.from(context))
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return detailList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = detailList[position]

        holder.binding.productCode.text = model.productCode
        holder.binding.productName.text = model.productName
        holder.binding.total.text = model.quantity.toString()
        holder.binding.scan.text = model.scanCount.toString()

        holder.binding.assignLocation.visibility = if(model.hasItemLocation) View.GONE else View.VISIBLE
        holder.binding.assignLocation.setOnClickListener {
            onAssignClick(model)
        }
        if (position==detailList.size-1 && detailList.size>= Utils.ROWS){
            onReachToEnd(position)
        }
        holder.binding.root.setOnClickListener {
            onItemClick(model)
        }
    }

}