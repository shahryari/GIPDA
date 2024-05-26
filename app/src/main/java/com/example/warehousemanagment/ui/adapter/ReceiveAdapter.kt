package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternReceivingBinding
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.receive.receiving.ReceivingModel
import com.example.warehousemanagment.model.models.receive.receiving.RowReceivingModel
import com.squareup.picasso.Picasso

class ReceiveAdapter(): RecyclerView.Adapter<ReceiveAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<RowReceivingModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<RowReceivingModel>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
            this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternReceivingBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.lastName.visibility= View.VISIBLE
        holder.b.recevieNumber.setText(model.receivingNumber)
        holder.b.driverFullName.setText(model.driverFirstName)
        holder.b.dockCode.setText(model.dockCode)
        holder.b.date.setText(model.taskTimeString)

        holder.b.type.setText(model?.carTypeTitle?.toString())
        holder.b.containerNumber.setText(model.containerNumber)
        holder.b.lastName.setText(model.driverLastName)


        holder.b.plaque.setText(getBuiltString(model.plaqueNumberThird,
            model.plaqueNumberSecond,model.plaqueNumberFirst))
        holder.b.plaqueYear.text=model.plaqueNumberFourth

        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model,position)
        }
        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }

        if (model.driverImageUrl!=null)
            Picasso.get().load(model.driverImageUrl).into(holder.b.img)


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


    class MyViewHolder(b:PatternReceivingBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternReceivingBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: RowReceivingModel,position: Int)
        fun reachToEnd(position: Int)
    }
}
