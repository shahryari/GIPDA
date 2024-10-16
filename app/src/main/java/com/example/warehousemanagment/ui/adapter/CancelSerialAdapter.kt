package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternSerialCancelBinding
import com.example.warehousemanagment.model.models.shipping.SerialBaseShippingSerialRow

class CancelSerialAdapter(
    val list: List<SerialBaseShippingSerialRow>,
    val context: Context,
) : RecyclerView.Adapter<CancelSerialAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: PatternSerialCancelBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PatternSerialCancelBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.title.text = model.serialNumber
        holder.binding.tick.backgroundTintList = ContextCompat.getColorStateList(
            context,
            if (model.isScanInShip) R.color.red else R.color.white
        )
    }
}