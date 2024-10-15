package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.PatternSerialBinding
import com.example.warehousemanagment.model.models.shipping.SerialBaseShippingSerialRow
import com.example.warehousemanagment.model.models.shipping.ShippingSerialModel

class SerialBaseShippingSerialAdapter(
    var list: List<SerialBaseShippingSerialRow>,
    val context: Context,
    val onCallBackListener: OnCallBackListener
): RecyclerView.Adapter<SerialBaseShippingSerialAdapter.MyViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= PatternSerialBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model= list[position]
        holder.b.title.text = model.serialNumber

        onCallBackListener.imgVisible(holder.b.excel)

        if (model.isScanInShip)holder.b.title.setTextColor(Color.Black.toArgb())
        if (model.isScanInShip)holder.b.layout.backgroundTintList = ContextCompat.getColorStateList(context, R.color.green)

        holder.b.delete.visibility = View.GONE


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


    class MyViewHolder(var b: PatternSerialBinding): RecyclerView.ViewHolder(b.root)

    interface OnCallBackListener{
        fun onDelete(model: ShippingSerialModel)
        fun imgVisible(img: ImageView)
    }
}
