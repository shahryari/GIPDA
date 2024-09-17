package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternDockBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.dock.DockRow

class DockAdapter(
    private val context: Context,
    private val dockList: List<DockRow>,
    private val onCallBackListener: OnCallBackListener
) : RecyclerView.Adapter<DockAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: PatternDockBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PatternDockBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return dockList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = dockList[position]
        holder.binding.dockCode.text = model.dockCode
        holder.binding.inActive.setOnClickListener {
            onCallBackListener.onUseDock(model.dockId,1)
        }
        holder.binding.receiving.setOnClickListener {
            onCallBackListener.onUseDock(model.dockId,2)
        }
        holder.binding.shipping.setOnClickListener {
            onCallBackListener.onUseDock(model.dockId,3)
        }
        holder.binding.inActive.backgroundTintList = if (model.dockTypeID == 1) ContextCompat.getColorStateList(context,R.color.mainYellow) else ContextCompat.getColorStateList(context,R.color.white)
        holder.binding.inActiveText.setTypeface(holder.binding.inActiveText.typeface, if (model.dockTypeID == 1)Typeface.BOLD else Typeface.NORMAL)

        holder.binding.receiving.backgroundTintList = ColorStateList.valueOf(if (model.dockTypeID == 2) ContextCompat.getColor(context,R.color.mainYellow) else ContextCompat.getColor(context,R.color.white))
        holder.binding.receivingText.setTypeface(holder.binding.receivingText.typeface, if (model.dockTypeID == 2)Typeface.BOLD else Typeface.NORMAL)

        holder.binding.shipping.backgroundTintList = ColorStateList.valueOf(if (model.dockTypeID == 3) ContextCompat.getColor(context,R.color.mainYellow) else ContextCompat.getColor(context,R.color.white))
        holder.binding.shippingText.setTypeface(holder.binding.shippingText.typeface, if (model.dockTypeID == 3)Typeface.BOLD else Typeface.NORMAL)

        if (position==dockList.size-1 && dockList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }
    }

    interface OnCallBackListener{
        fun reachToEnd(position: Int)

        fun onUseDock(dockId: String,useDock: Int)

        fun enableRefresh(boolean: Boolean)
    }
}