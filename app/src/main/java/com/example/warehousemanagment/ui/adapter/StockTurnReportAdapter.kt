package com.example.warehousemanagment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternStockTurnItemBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.stock.StockTurnItemLocationRow

class StockTurnReportAdapter(
    private val list: List<StockTurnItemLocationRow>,
    private val taskTypeList: List<CatalogModel>,
    private val inventoryList: List<CatalogModel>,
    private val onReachedEnd: () -> Unit
) : RecyclerView.Adapter<StockTurnReportAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            PatternStockTurnItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = list[position]
        holder.binding.locationCode.text = model.locationCode?:""
        holder.binding.taskType.text = taskTypeList.find { it.valueField.toString() == model.taskTypeID }?.title
        holder.binding.inventoryType.text = inventoryList.find { it.valueField.toString() == model.invTypeID }?.title
        holder.binding.owner.text = model.ownerFullName?:""
        holder.binding.goodsNmae.text = model.goodsTitle?:""
        holder.binding.resourceNumber.text = model.entityResourceNumber?:""
        holder.binding.goodsSystemCode.text = model.goodsSystemCode


        if (position==list.size-1 && list.size>= Utils.ROWS){
            onReachedEnd()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolder(val binding: PatternStockTurnItemBinding) : RecyclerView.ViewHolder(binding.root)
}