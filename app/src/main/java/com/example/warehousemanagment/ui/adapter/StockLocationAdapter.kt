package com.example.warehousemanagment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.warehousemanagment.databinding.PatternTaskTypeBinding
import com.example.warehousemanagment.model.models.stock.StockLocationRow

class StockLocationAdapter() : Adapter<StockLocationAdapter.MyViewHolder>() {
    private  var arrayList:List<StockLocationRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    init {

    }

    constructor(locations: List<StockLocationRow>, onCallBackListener: OnCallBackListener):this()
    {
        this.arrayList=locations
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternTaskTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val location = arrayList[position]
        holder.b.pickTaskName.text = location.locationCode
        holder.b.relItem.setOnClickListener {
            onCallBackListener.onClick(location)
        }

//        holder.b.pickImg.visibility = if (selectedLocations.contains(location)) View.VISIBLE else View.GONE
//        holder.b.pickImg.imageTintList = ColorStateList.valueOf(Color.Black.toArgb())
    }



    override fun getItemCount(): Int {
        return arrayList.size
    }




    class MyViewHolder(b: PatternTaskTypeBinding): RecyclerView.ViewHolder(b.root){
        var b: PatternTaskTypeBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(location: StockLocationRow)
    }
}