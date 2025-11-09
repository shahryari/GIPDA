package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternSerialBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.LocationProductSerialRow

class LocationProductSerialAdapter(
    private val context: Context,
    private val serials: List<LocationProductSerialRow>,
    private val onReachEnd: ()->Unit,
    private val init: (binding: PatternSerialBinding,model: LocationProductSerialRow)->Unit,
) : RecyclerView.Adapter<LocationProductSerialAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = PatternSerialBinding.inflate(LayoutInflater.from(context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val serial = serials[position]
        holder.binding.title.text = serial.serialNumber
        init(holder.binding,serial)
        if (position==serials.size-1 && serials.size>= 1000){
            onReachEnd()
        }

    }

    override fun getItemCount(): Int {
        return serials.size
    }


    class MyViewHolder(val binding: PatternSerialBinding) : RecyclerView.ViewHolder(binding.root)
}