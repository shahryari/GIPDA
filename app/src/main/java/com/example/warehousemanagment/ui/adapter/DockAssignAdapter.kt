package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.warehousemanagment.databinding.PatternDockAssignBinding
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.models.dock_assign.ShippingListOnDockRow

class DockAssignAdapter(
    private val list: List<ShippingListOnDockRow>,
    private val context: Context,
    private val onReachEnd: (Int)->Unit,
    private val onClick: (ShippingListOnDockRow)->Unit
) : Adapter<DockAssignAdapter.MyViewHolder>(){
    class MyViewHolder(val binding: PatternDockAssignBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PatternDockAssignBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        holder.binding.bolNumber.text = model.bOLNumber
        holder.binding.shippingNumber.text = model.shippingNumber
        holder.binding.driverFullName.text = model.firstDriverFullName
        val plques = model.carPlaqueNumber.split('-')
        if (plques.size==4){
            holder.binding.plaque.text = getBuiltString(
                plques[3], plques[2], plques[1]
            )
            holder.binding.plaqueYear.text = plques[0]
        }
        holder.binding.type.text = model.carTypeTitle

        holder.binding.root.setOnClickListener {
            onClick(model)
        }
    }
}