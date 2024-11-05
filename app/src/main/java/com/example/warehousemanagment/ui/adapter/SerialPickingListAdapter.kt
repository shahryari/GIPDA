package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.warehousemanagment.databinding.PatternPickingDetailHeaderBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.picking.SerialBasePickingRow

class SerialPickingListAdapter(
    val list: List<SerialBasePickingRow>,
    val context: Context,
    val onItemClick: (SerialBasePickingRow)->Unit,
    val onReachToEnd: (Int)->Unit
) : Adapter<SerialPickingListAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: PatternPickingDetailHeaderBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PatternPickingDetailHeaderBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.productTitle.text = model.productTitle
        holder.binding.productCode.text = model.productCode
        holder.binding.invTypeTitle.text = model.invTypeTitle
        holder.binding.locationCode.text = model.locationCode
        holder.binding.shippingArea.text = model.shippingNumber
        holder.binding.quantity.text = model.quantity.toString()
        holder.binding.scan.text = model.sumQuantity.toString()
        holder.binding.shippingLocationCode.text = model.shippingLocationCode

        holder.itemView.setOnClickListener {
            onItemClick(model)
        }

        if (position==list.size-1 && list.size>= Utils.ROWS){
            onReachToEnd(position)
        }
    }
}