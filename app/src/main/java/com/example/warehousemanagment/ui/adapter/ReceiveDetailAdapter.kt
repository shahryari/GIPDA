package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.PatternReceivingDetailInfoBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.receive.receiveDetail.ReceiveDetailRow

class ReceiveDetailAdapter(): RecyclerView.Adapter<ReceiveDetailAdapter.MyViewHolder>()
{
    private lateinit var context: Context
    private var serialList: List<ReceiveDetailRow> = ArrayList()
    lateinit var onCallBackListener:OnCallBackListener


    constructor(serialList: List<ReceiveDetailRow>,context: Context, onCallBackListener: OnCallBackListener):this() {
        this.serialList=serialList
        this.context=context
        this.onCallBackListener=onCallBackListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=PatternReceivingDetailInfoBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val model=serialList.get(position)
        holder.b.iconBarcode.setOnClickListener {
            onCallBackListener.onScanClick(model.receivingDetailID,model.productID,holder.b.progress,
                model.productTitle,model.productCode,model.workerTaskID,
                model.ownerCode,model.invTypeTitle)
        }
        holder.b.iconHand.setOnClickListener {
            onCallBackListener.onHandClick(model.receivingDetailID,model.workerTaskID,
                model.productTitle,model.productCode,model.ownerCode,model.invTypeTitle)
        }
        holder.b.productTitle.setText(model.productTitle)
        holder.b.productCode.setText(model.productCode)
        holder.b.row.text=model.palletLayer.toString()
        holder.b.qri.text=model.quantityPerLayer.toString()
        holder.b.invTypeTitle.text=model.invTypeTitle


        if (!model.serializable)
        {
            holder.b.iconBarcode.visibility=View.INVISIBLE
            holder.b.imgBackground.visibility=View.INVISIBLE
        } else{
            holder.b.iconBarcode.visibility=View.VISIBLE
            holder.b.imgBackground.visibility=View.VISIBLE
        }
        if (position==serialList.size-1  && serialList.size>= Utils.ROWS){
            onCallBackListener.reachToEnd(position)
        }
    }



    override fun getItemCount(): Int {
        return serialList.size
    }

    fun <T> setFilter(newList: List<T>)
    {
        serialList =ArrayList()
        (serialList as ArrayList<T>).addAll(newList)
        notifyDataSetChanged()
    }



    class MyViewHolder(b:PatternReceivingDetailInfoBinding): RecyclerView.ViewHolder(b.root){
        var b:PatternReceivingDetailInfoBinding
        init {
            this.b=b
        }

    }

    interface OnCallBackListener{
        fun onScanClick(
            receivingDetailId: String, productId: String, progress: ProgressBar
            ,
            productTitle: String, productCode: String, workerTaskID: String,
            ownerCode: String,
            invTypeTitle: String
        )
        fun onHandClick(receivingDetailID: String, workerTaskID: String,
                        productTitle: String,productCode: String,ownerCode: String,invTypeTitle:String)

        fun reachToEnd(position: Int)
    }
}
