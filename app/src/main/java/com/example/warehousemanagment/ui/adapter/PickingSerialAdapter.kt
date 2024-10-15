package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternSerialPickingDetailScanBinding
import com.example.warehousemanagment.model.models.picking.GetPickingSerialRow

class PickingSerialAdapter(
    val list: List<GetPickingSerialRow>,
    val context: Context,
    val reachToEnd: (Int)->Unit
) : Adapter<PickingSerialAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: PatternSerialPickingDetailScanBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PatternSerialPickingDetailScanBinding.inflate(LayoutInflater.from(context))
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.title.text = model.serialNumber
        holder.binding.tick.backgroundTintList = ContextCompat.getColorStateList(
            context,
            if (model.scanSerial) R.color.green else R.color.white
        )
    }
}