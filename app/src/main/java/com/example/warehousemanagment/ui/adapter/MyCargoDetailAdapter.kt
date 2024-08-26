package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternCargoDetailBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.my_cargo.my_cargo_detail.MyCargoDetailRow

class MyCargoDetailAdapter(): RecyclerView.Adapter<MyCargoDetailAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<MyCargoDetailRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<MyCargoDetailRow>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternCargoDetailBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.productCodeTitle?.visibility = View.GONE
        holder.b.productCode.visibility = View.GONE
        holder.b.hasPriorityTitle?.visibility = View.GONE
        holder.b.hasPriority?.visibility = View.GONE

        holder.b.customerFullName.text=model.customerFullName
        holder.b.ownerCode.text=model.ownerCode
        holder.b.invType.text=model.invTypeTitle
        holder.b.productTitle.text=model.productTitle
//        holder.b.productCode.text=model.productCode
        holder.b.count.text=model.quantity.toString()
        holder.b.locationCode?.text=model.locationCode
//        holder.b.hasPriority?.text=model.hasLowPriorityTitle


        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model,position)
        }
        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }
//
//        if(!model.isDone)
//        {
//            holder.b.done?.visibility= View.VISIBLE
//            holder.b.remove?.visibility=View.GONE
//        }else{
//            holder.b.done?.visibility= View.GONE
//            holder.b.remove?.visibility=View.VISIBLE
//        }
//
//        holder.b.done?.setOnClickListener {
//            onCallBackListener.onDoneClick(holder.b,model)
//        }
//        holder.b.remove?.setOnClickListener {
//            onCallBackListener.onRemoveClick(holder.b,model)
//        }

        val colorList = ColorStateList.valueOf(ContextCompat.getColor(context,if (holder.b.doneSwitch?.isChecked == true) R.color.green else R.color.red))

        holder.b.doneSwitch?.visibility = View.VISIBLE
        holder.b.doneSwitch?.isChecked = model.isDone

        holder.b.doneSwitch?.trackDrawable = ContextCompat.getDrawable(context, R.drawable.tick)
        holder.b.doneSwitch?.thumbDrawable = ContextCompat.getDrawable(context, R.drawable.circle)

        holder.b.doneByLay?.visibility = if (model.isDone) View.VISIBLE else View.GONE
        holder.b.doneBy?.text = model.doneBy

        holder.b.doneSwitch?.setOnClickListener {
            if (!model.isDone) {
                onCallBackListener.onDoneClick(holder.b,model)
            } else {
                onCallBackListener.onRemoveClick(holder.b,model)
            }
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


    class MyViewHolder(b:PatternCargoDetailBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternCargoDetailBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: MyCargoDetailRow,position: Int)
        fun reachToEnd(position: Int)
        fun onDoneClick(b: PatternCargoDetailBinding, model: MyCargoDetailRow)
        fun onRemoveClick(b: PatternCargoDetailBinding, model: MyCargoDetailRow)
    }
}
