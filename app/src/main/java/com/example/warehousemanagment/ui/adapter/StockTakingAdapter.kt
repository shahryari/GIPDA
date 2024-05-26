package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternStockTakingBinding
import com.example.warehousemanagment.model.constants.Utils
import com.test.StockTrackRow

class StockTakingAdapter(): RecyclerView.Adapter<StockTakingAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<StockTrackRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<StockTrackRow>,
                context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternStockTakingBinding.inflate(LayoutInflater.
            from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.title.setText(model.title)
        holder.b.warhouseName.setText(model.warehouseCode)



        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model)
        }
        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }



    }



    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun <T> setFilter(newList: List<T>)
    {
        arrayList =ArrayList()
        (arrayList as ArrayList<T>).addAll(newList)
        notifyDataSetChanged()
    }


    class MyViewHolder(b: PatternStockTakingBinding): RecyclerView.ViewHolder(b.root){
        var b: PatternStockTakingBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: StockTrackRow,)
        fun reachToEnd(position: Int)
    }
}