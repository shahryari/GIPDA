package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternDockGroupBinding
import com.example.warehousemanagment.model.models.dock.DockRow
import com.example.warehousemanagment.ui.adapter.DockAdapter.OnCallBackListener

class DockGroupAdapter(
    private val context: Context,
    private val dockGroupList: Map<String,List<DockRow>>,
    private val onCallBackListener: OnCallBackListener
)  : RecyclerView.Adapter<DockGroupAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: PatternDockGroupBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PatternDockGroupBinding.inflate(LayoutInflater.from(context))
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dockGroupList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val group = dockGroupList.entries.toList()[position]
        holder.binding.warehouseCode.text = group.key
        holder.binding.rv.adapter = DockAdapter(context,group.value,object :OnCallBackListener{
            override fun reachToEnd(position: Int) {
                onCallBackListener.reachToEnd(position)
            }

            override fun onUseDock(dockId: String, useDock: Int) {
                onCallBackListener.onUseDock(dockId,useDock)
            }
        })
    }

}