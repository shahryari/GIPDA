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
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.my_cargo.my_cargo.MyCargoRow

class MyCargoAdapter(): RecyclerView.Adapter<MyCargoAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private  var arrayList:List<MyCargoRow> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener

    constructor(arrayList: List<MyCargoRow>, context: Context, onCallBackListener: OnCallBackListener):this() {
        this.context=context
        this.onCallBackListener=onCallBackListener
        this.arrayList= arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternCargoBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model= arrayList[position]
        holder.b.lastName.visibility= View.VISIBLE
        holder.b.workerFullName.text = model.workerFullNameShip
        holder.b.recevieNumber.text = model.shippingNumber
        holder.b.dockCode.text = model.dockCode
        holder.b.date.text = model.dockAssignTimeString

        holder.b.type.text = model.carTypeTitle
        holder.b.containerNumber.text = model.bOLNumber
        holder.b.driverFullName.text = model.driverFullName.toString()
        holder.b.title1.text = context.getString(R.string.shippingNumber2)
        holder.b.title2.text = context.getString(R.string.bolNumber)
        holder.b.qtyLay.visibility = View.VISIBLE
        holder.b.plaque.text = getBuiltString(
            model.plaqueNumberThird,
            model.plaqueNumberSecond, model.plaqueNumberFirst
        )
        holder.b.plaqueYear.text=model.plaqueNumberFourth

        holder.b.done.visibility = if (model.isDone) View.VISIBLE else View.GONE

        holder.b.totalTv.text = model.total.toString()
        holder.b.doneTv.text = model.doneCount.toString()
        holder.b.qtyTv.text = model.sumQuantity.toString()
        holder.b.doneQtyTv.text = model.sumDonQuantity.toString()

        holder.itemView.setOnClickListener {
            onCallBackListener.onClick(model,position,holder.b.progressBar)
        }
        if (position==arrayList.size-1 && arrayList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }

        if (model.driverImageUrl != ""){
            onCallBackListener.setImage(model.driverImageUrl,holder.b.img)
        }else holder.b.img.setImageResource(R.drawable.driver_default)

        val driversList=model.driverTaskObject;
        holder.b.rvDrivers.adapter=CargoDriverAdapter(
            driversList,context,object :CargoDriverAdapter.OnCallBackListener{
                override fun setImage(driverImageUrl: String, driverImg: ImageView) {
                    onCallBackListener.setImage(driverImageUrl,driverImg)
                }

            }
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


    class MyViewHolder(b:PatternCargoBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternCargoBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: MyCargoRow, position: Int, progressBar: ProgressBar)
        fun reachToEnd(position: Int)
        fun setImage(img:String,imageView: ImageView)
    }
}
