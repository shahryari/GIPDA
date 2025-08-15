package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternStockTakingLocationBinding
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.stock.stock_take_location.StockTackingLocationRow

class StockTakingLocationAdapter():
    RecyclerView.Adapter<StockTakingLocationAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<StockTackingLocationRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    lateinit var countValues1: MutableList<List<Int>>
    lateinit var countValues2: MutableList<List<Int>>
    lateinit var countValues3: MutableList<List<Int>>


    constructor(arrayList: List<StockTackingLocationRow>,
                context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
        countValues1 = arrayList.map {
            listOf<Int>()
        }.toMutableList()
        countValues2 =arrayList.map {
            listOf<Int>()
        }.toMutableList()
        countValues3 = arrayList.map {
            listOf<Int>()
        }.toMutableList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternStockTakingLocationBinding.inflate(LayoutInflater.
            from(context),parent,false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)

        val values1 = countValues1.getOrNull(position)?.toMutableList() ?: mutableListOf()
        val values2 = countValues2.getOrNull(position)?.toMutableList() ?: mutableListOf()
        val values3 = countValues3.getOrNull(position)?.toMutableList() ?: mutableListOf()

        holder.b.ownerCode.text = model.ownerCode
        holder.b.locationCode.text = model.locationCode
        holder.b.goodSystemCode.text = model.productCode
        holder.b.invTypeTitle.text= model.invTypeTitle
        holder.b.productTitle.text= model.productTitle
        holder.b.quantity.text = model.realInventory.toString()
        if (values1.isNotEmpty()){
            holder.b.values1.text = values1.joinToString("+", postfix = "=${values1.sum()}")
            if (values1.sum()!=model.realInventory) {
                holder.b.count1Hint.visibility = View.VISIBLE
                holder.b.count1Hint.text = "ًReal Inventory is ${model.realInventory}"
            } else {
                holder.b.count1Hint.visibility = View.GONE
            }
        }
        if (values2.isNotEmpty())holder.b.values2.text = values2.joinToString("+", postfix = "=${values2.sum()}")
        if (values3.isNotEmpty())holder.b.values3.text = values3.joinToString("+", postfix = "=${values3.sum()}")

        holder.b.change.setOnClickListener {
            onCallBackListener.onChangeClick(model)
        }
        holder.b.save.setOnClickListener {
            onCallBackListener.onSaveClick(
                model,holder.b.count,
                values1,
                holder.b.count2,
                values2,
                holder.b.count3,
                values3,
                holder.b.progressBar,
             )
        }
        holder.b.add1.setOnClickListener {
            val q = textEdi(holder.b.count).toIntOrNull()
            if (q!=null){


                countValues1.add(position,values1 + q)
                holder.b.count.setText("")
                notifyItemChanged(position)
            }
        }
        holder.b.add2.setOnClickListener {
            val q = textEdi(holder.b.count2).toIntOrNull()
            if (q !=null){


                countValues2.add(position,values2 + q)
                holder.b.count2.setText("")
                notifyItemChanged(position)
            }
        }
        holder.b.add3.setOnClickListener {
            val q = textEdi(holder.b.count3).toIntOrNull()
            if (q !=null){


                countValues3.add(position,values3 + q)
                holder.b.count3.setText("")
                notifyItemChanged(position)
            }
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
        fun init(b: PatternStockTakingLocationBinding, model: StockTackingLocationRow)
        fun onChangeClick(model: StockTackingLocationRow,)
        fun reachToEnd(position: Int)
        fun onSaveClick(model: StockTackingLocationRow, count: EditText, values: List<Int>, count2:EditText, values2: List<Int>,
                        count3: EditText,values3: List<Int>,
                        progressBar: ProgressBar)
    }
}