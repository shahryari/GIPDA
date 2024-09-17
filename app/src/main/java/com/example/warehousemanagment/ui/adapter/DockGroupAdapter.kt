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

        holder.binding.rv.removeAllViews()
        for (dock in group.value){
            val dockBinding=  PatternDockBinding.inflate(LayoutInflater.from(context))
            dockBinding.dockCode.text = dock.dockCode
            dockBinding.inActive.setOnClickListener {
                onCallBackListener.onUseDock(dock.dockId,1)
            }
            dockBinding.receiving.setOnClickListener {
                onCallBackListener.onUseDock(dock.dockId,2)
            }
            dockBinding.shipping.setOnClickListener {
                onCallBackListener.onUseDock(dock.dockId,3)
            }
            dockBinding.inActive.backgroundTintList = if (dock.dockTypeID == 1) ContextCompat.getColorStateList(context,R.color.mainYellow) else ContextCompat.getColorStateList(context,R.color.white)
            dockBinding.inActiveText.setTypeface(dockBinding.inActiveText.typeface, if (dock.dockTypeID == 1) Typeface.BOLD else Typeface.NORMAL)

            dockBinding.receiving.backgroundTintList = ColorStateList.valueOf(if (dock.dockTypeID == 2) ContextCompat.getColor(context,R.color.mainYellow) else ContextCompat.getColor(context,R.color.white))
            dockBinding.receivingText.setTypeface(dockBinding.receivingText.typeface, if (dock.dockTypeID == 2) Typeface.BOLD else Typeface.NORMAL)

            dockBinding.shipping.backgroundTintList = ColorStateList.valueOf(if (dock.dockTypeID == 3) ContextCompat.getColor(context,R.color.mainYellow) else ContextCompat.getColor(context,R.color.white))
            dockBinding.shippingText.setTypeface(dockBinding.shippingText.typeface, if (dock.dockTypeID == 3) Typeface.BOLD else Typeface.NORMAL)

            holder.binding.rv.addView(dockBinding.root)
        }
//        holder.binding.rv.adapter = DockAdapter(context,group.value,object :OnCallBackListener{
//            override fun reachToEnd(position: Int) {
//            }
//
//            override fun onUseDock(dockId: String, useDock: Int) {
//                onCallBackListener.onUseDock(dockId,useDock)
//            }
//
//            override fun enableRefresh(boolean: Boolean) {
//            }
//        })
//        holder.binding.rv.isNestedScrollingEnabled = true
    }

}