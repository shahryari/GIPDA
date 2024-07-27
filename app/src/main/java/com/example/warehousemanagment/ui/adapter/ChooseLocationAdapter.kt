package com.example.warehousemanagment.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternTaskTypeBinding

class ChooseLocationAdapter() : RecyclerView.Adapter<ChooseLocationAdapter.MyViewHolder>() {

    private  var arrayList:List<String> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener
    private var selectedLocations = mutableListOf<String>()

    init {

    }

    constructor(locations:List<String>, selectedLocations: List<String>,onCallBackListener: OnCallBackListener):this()
    {
        this.arrayList=locations
        this.selectedLocations = selectedLocations.toMutableList()
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternTaskTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val location = arrayList[position]
        holder.b.pickTaskName.text = location
        holder.b.relItem.setOnClickListener {
            if (selectedLocations.contains(location)){
                selectedLocations.remove(location)
            } else {
                selectedLocations.add(location)
            }
            onCallBackListener.onClick(selectedLocations)
            notifyItemChanged(position)
        }

        holder.b.pickImg.visibility = if (selectedLocations.contains(location)) View.VISIBLE else View.GONE
        holder.b.pickImg.imageTintList = ColorStateList.valueOf(Color.Black.toArgb())
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
        fun onClick(locations: List<String>)
    }
}