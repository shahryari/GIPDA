package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.warehousemanagment.databinding.PatternColorBinding
import com.example.warehousemanagment.model.models.shipping.customer.ColorModel

class ColorAdapter(
    val context: Context,
    val arrayList: List<ColorModel>,
) : RecyclerView.Adapter<ColorAdapter.MyViewHolder>(){

    var selected : ColorModel? = null


    class MyViewHolder(val b: PatternColorBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PatternColorBinding.inflate(LayoutInflater.from(context))
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val color = arrayList[position]


        holder.b.color.setBackgroundColor(Color.parseColor(color.customerColorCode))

//        holder.b.color.setBackgroundColor(Color.RED)
        if (selected?.customerColorId == color.customerColorId) {
            holder.b.checked.visibility = View.VISIBLE
        } else {
            holder.b.checked.visibility = View.GONE
        }

        holder.b.color.setOnClickListener {
            selected = color
            notifyDataSetChanged()
        }
    }
}