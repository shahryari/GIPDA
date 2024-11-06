package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.warehousemanagment.databinding.PatternSerialBinding

class SimpleSerialAdapter(
    val list: List<String>,
    val context: Context,
    val onDelete: (model: String) -> Unit,
    val init: (PatternSerialBinding)->Unit
) : Adapter<SimpleSerialAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: PatternSerialBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PatternSerialBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        init(holder.binding)

        holder.binding.excel.visibility = View.GONE
        holder.binding.delete.setOnClickListener {
            onDelete(list[position])
        }
        holder.binding.title.text = list[position]
    }
}