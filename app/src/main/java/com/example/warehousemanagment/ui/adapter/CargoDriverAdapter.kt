package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternCargoBinding
import com.example.warehousemanagment.databinding.PatternCargoDriverBinding
import com.example.warehousemanagment.model.models.cargo_folder.cargo.DriverTaskObject
import com.squareup.picasso.Picasso


class CargoDriverAdapter(): RecyclerView.Adapter<CargoDriverAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<DriverTaskObject> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<DriverTaskObject>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternCargoDriverBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=arrayList.get(position)
        if (model.driverImageUrl.equals("") || model.driverImageUrl== null)
        {
            holder.b.driverImg.setImageResource(R.drawable.driver_default)
        }else onCallBackListener.setImage(model.driverImageUrl,holder.b.driverImg)

        holder.b.driverTv.setText(model.personFullName)


        if(model.isDone==true){
            holder.b.checkImg.visibility=View.VISIBLE
        }else holder.b.checkImg.visibility=View.INVISIBLE






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


    class MyViewHolder(b: PatternCargoDriverBinding): RecyclerView.ViewHolder(b.root){
        var b: PatternCargoDriverBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun setImage(driverImageUrl: String, driverImg: ImageView)

    }
}