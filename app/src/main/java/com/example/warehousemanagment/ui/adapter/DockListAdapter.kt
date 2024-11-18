package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.warehousemanagment.databinding.PatternDestinationLocationBinding
import com.example.warehousemanagment.model.models.dock_assign.DockListOnShippingRow

class DockListAdapter(
    private val list: List<DockListOnShippingRow>,
    private val context: Context,
    private val onClick: (DockListOnShippingRow)->Unit
) : Adapter<DockListAdapter.MyViewHolder>(){
    class MyViewHolder(val binding: PatternDestinationLocationBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PatternDestinationLocationBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        holder.binding.title.text = model.dockCode

        holder.binding.root.setOnClickListener {
            onClick(model)
        }
    }
}