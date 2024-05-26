package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternSerialBinding
import com.example.warehousemanagment.model.models.receive.receiving_detail_serials.ReceivingDetailSerialModel
import com.example.warehousemanagment.model.models.tracking.GetSerialInfoModel


class TrackingSerialAdapter(): RecyclerView.Adapter<TrackingSerialAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<GetSerialInfoModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener
    constructor(arrayList:List<GetSerialInfoModel>, context: Context,
                onCallBackListener: OnCallBackListener):this()
    {
        this.context=context
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternSerialBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        holder.b.title.setText(model.serialNumber)

        holder.b.excel.visibility= View.GONE

        holder.b.delete.setOnClickListener {
            onCallBackListener.onDelete(model)
        }
        onCallBackListener.imgVisible(holder.b.excel)


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


    class MyViewHolder(b: PatternSerialBinding): RecyclerView.ViewHolder(b.root){
        var b: PatternSerialBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onDelete(model: GetSerialInfoModel)
        fun imgVisible(img: ImageView)
    }
}
