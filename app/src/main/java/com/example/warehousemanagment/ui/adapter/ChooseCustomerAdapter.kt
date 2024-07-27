package com.example.warehousemanagment.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternTaskTypeBinding
import com.example.warehousemanagment.model.models.shipping.customer.CustomerModel

class ChooseCustomerAdapter() : RecyclerView.Adapter<ChooseCustomerAdapter.MyViewHolder>() {

    private  var arrayList:List<CustomerModel> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener
    private var selectedCustomers = mutableListOf<CustomerModel>()

    init {

    }

    constructor(customers:List<CustomerModel>, selectedCustomers: List<CustomerModel>,onCallBackListener: OnCallBackListener):this()
    {
        this.arrayList=customers
        this.selectedCustomers = selectedCustomers.toMutableList()
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternTaskTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val customer = arrayList[position]
        holder.b.pickTaskName.text = customer.customerFullName
        holder.b.relItem.setOnClickListener {
            if (selectedCustomers.contains(customer)){
                selectedCustomers.remove(customer)
            } else {
                selectedCustomers.add(customer)
            }
            onCallBackListener.onClick(selectedCustomers)
            notifyItemChanged(position)
        }

        holder.b.pickImg.visibility = if (selectedCustomers.contains(customer)) View.VISIBLE else View.GONE
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
        fun onClick(customers: List<CustomerModel>)
    }
}