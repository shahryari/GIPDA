package com.example.warehousemanagment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternTaskTypeBinding
import com.example.warehousemanagment.model.models.login.CatalogModel

class TaskTypeAdapter(): RecyclerView.Adapter<TaskTypeAdapter.MyViewHolder>()
{
    private  var arrayList:List<CatalogModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener
    private var selectedPosition = RecyclerView.NO_POSITION

    init {

    }

    constructor(arrayList:List<CatalogModel> ,onCallBackListener: OnCallBackListener):this()
    {
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternTaskTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model= arrayList[position]
        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
            onCallBackListener.onClick(model)
        }
        holder.b.pickTaskName.text = model.title

        if (position == selectedPosition) {
            holder.b.pickImg.visibility = View.VISIBLE

        } else {
            holder.b.pickImg.visibility = View.INVISIBLE
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



    class MyViewHolder(b:PatternTaskTypeBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternTaskTypeBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model:CatalogModel)
    }
}
