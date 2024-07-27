package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternCustomerBinding
import com.example.warehousemanagment.model.models.shipping.customer.CustomerInShipping

class ChooseCustomerColorAdapter() : RecyclerView.Adapter<ChooseCustomerColorAdapter.MyViewHolder>() {
    private lateinit var context: Context
    private  var arrayList:List<CustomerInShipping> =ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(arrayList:List<CustomerInShipping>, context: Context, onCallBackListener: OnCallBackListener ):this()
    {
        this.context=context
        this.arrayList=arrayList
        this.onCallBackListener=onCallBackListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternCustomerBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model= arrayList[position]


        holder.b.customerFullName.text = model.customerName

        holder.b.clearBtn.visibility = if (model.customerColorCode != null) View.VISIBLE else View.GONE

        holder.b.clearBtn.setOnClickListener {
            onCallBackListener.onClearColor(model)
        }

        holder.b.chooseColorBtn.backgroundTintList = ColorStateList.valueOf(
            if (model.customerColorCode!=null)Color.parseColor(model.customerColorCode)
            else ContextCompat.getColor(context, R.color.grayCB)
        )
        holder.b.chooseColorBtn.setOnClickListener {
            onCallBackListener.onClick(model)
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



    class MyViewHolder(b: PatternCustomerBinding): RecyclerView.ViewHolder(b.root){
        var b: PatternCustomerBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onClick(model: CustomerInShipping)
        fun onClearColor(model: CustomerInShipping)
    }
}