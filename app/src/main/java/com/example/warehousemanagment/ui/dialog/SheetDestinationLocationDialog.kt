package com.example.warehousemanagment.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetDestinationLocationDialog() : BottomSheetDialogFragment()
{
    lateinit var onClickListener:OnClickListener
    lateinit var ctx:Context

    constructor(ctx: Context,  onClickListener:OnClickListener) : this(){
        this.onClickListener=onClickListener
        this.ctx=ctx
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding=DialogSheetDestinyLocationBinding.inflate(inflater,container,false)

        onClickListener.init(
            binding
        )

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }
        binding.clearImg.setOnClickListener {
            binding.searchEdi.setText("")
        }


        onClickListener.setRvData(binding.rv,binding.progressBar,binding.serialsCount,binding.searchEdi)



        return binding.root
    }


    interface OnClickListener
    {
        fun onCloseClick()
        fun setRvData(rv: RecyclerView, progressBar: ProgressBar,countTv:TextView,searchEdi:EditText)
        fun init(
            binding: DialogSheetDestinyLocationBinding
        )
    }



}