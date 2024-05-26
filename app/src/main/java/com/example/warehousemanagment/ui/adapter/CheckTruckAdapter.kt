package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternCheckTruckBinding
import com.example.warehousemanagment.model.classes.OnSwipeTouchListener
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.check_truck.CheckTruckModel

class CheckTruckAdapter(): RecyclerView.Adapter<CheckTruckAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private var list:List<CheckTruckModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(list:List<CheckTruckModel>,context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.list=list
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternCheckTruckBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=list.get(position)
        holder.b.tv1.text=model.shippingNumber
        holder.b.tv2.text=model.customerFullName
        holder.b.tv3.text=model.bOLNumber
        holder.b.tv4.text=context.getString(R.string.temporary)
        holder.b.tvFullName.text=model.driverFullName
        holder.b.dockCode.text=model.dockCode





        holder.b.denyTv.setOnClickListener {
            onCallBackListener.onDenyClick(model,holder.b.rel0,holder.b.confirmTv,holder.b.denyTv)
        }
        holder.b.confirmTv.setOnClickListener {
            onCallBackListener.onConfirmClick(model.shippingAddressID,
                model.shippingNumber,holder.b.rel0,holder.b.confirmTv,holder.b.denyTv)
        }
        holder.itemView.setOnTouchListener(object: OnSwipeTouchListener(context) {
            override fun onSwipeStart() {
                 onCallBackListener.dragToStart(holder.b.rel0,holder.b.confirmTv,holder.b.denyTv)
            }
            override fun onSwipeEnd() {
                onCallBackListener.dragToEnd(holder.b.rel0,holder.b.confirmTv,holder.b.denyTv)
            }
        })
        holder.setIsRecyclable(false);

    }



    override fun getItemCount(): Int {
       return list.size
    }





    fun <T> setFilter(newList: List<T>)
    {
        list =ArrayList()
        (list as ArrayList<T>).addAll(newList)
        notifyDataSetChanged()
    }















    class MyViewHolder(b:PatternCheckTruckBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternCheckTruckBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onDenyClick(model: CheckTruckModel, itemView: View, confirm: TextView,deny:TextView)
        fun onConfirmClick(shippingAddressId:String,shippingNumber:String,
                           itemView: View, confirm: TextView,deny:TextView)
        fun dragToStart(itemView: View, confirm: TextView,deny:TextView)
        fun dragToEnd(itemView: View, confirm: TextView,deny:TextView)
    }
}
