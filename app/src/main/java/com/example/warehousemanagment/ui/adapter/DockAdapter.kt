package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.binding.useSwitch.isChecked = model.useDock
        holder.binding.warehouseCode.text = model.warehouseCode
        holder.binding.useSwitch.setOnClickListener {
            onCallBackListener.onUseDock(model.dockId,!model.useDock)
        }

        if (position==dockList.size-1 && dockList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }
    }

    interface OnCallBackListener{
        fun reachToEnd(position: Int)

        fun onUseDock(dockId: String,useDock: Boolean)
    }
}