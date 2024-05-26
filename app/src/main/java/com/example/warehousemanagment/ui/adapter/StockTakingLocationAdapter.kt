package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternStockTakingLocationBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.stock.stock_take_location.StockLocationRow

class StockTakingLocationAdapter():
    RecyclerView.Adapter<StockTakingLocationAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<StockLocationRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<StockLocationRow>,
                context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternStockTakingLocationBinding.inflate(LayoutInflater.
            from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.ownerCode.text = model.ownerCode
        holder.b.locationCode.text = model.locationCode
        holder.b.goodSystemCode.text = model.productCode
        holder.b.invTypeTitle.text= model.invTypeTitle
        holder.b.productTitle.text= model.productTitle



        holder.b.change.setOnClickListener {
            onCallBackListener.onChangeClick(model)
        }
        holder.b.save.setOnClickListener {
            onCallBackListener.onSaveClick(
                model,holder.b.count,
                holder.b.count2,
                holder.b.progressBar,
             )
        }


        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }

        onCallBackListener.init(
            holder.b,model
        )





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


    class MyViewHolder(b: PatternStockTakingLocationBinding):
        RecyclerView.ViewHolder(b.root){
        var b: PatternStockTakingLocationBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun init(b: PatternStockTakingLocationBinding, model: StockLocationRow)
        fun onChangeClick(model: StockLocationRow,)
        fun reachToEnd(position: Int)
        fun onSaveClick(model: StockLocationRow, count: EditText,count2:EditText,
                        progressBar: ProgressBar)
    }
}