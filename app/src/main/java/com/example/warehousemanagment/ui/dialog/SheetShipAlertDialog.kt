package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.warehousemanagment.databinding.DialogSheetBottomBinding
import com.example.warehousemanagment.databinding.DialogSheetBottomShipBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetShipAlertDialog() : BottomSheetDialogFragment()
{
    lateinit var title: String
    lateinit var onClickListener:OnClickListener

    constructor(title:String , onClickListener:OnClickListener) : this(){

        this.title=title
        this.onClickListener=onClickListener
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding=DialogSheetBottomShipBinding.inflate(inflater,container,false)

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }
        binding.okCanselBtn.cansel.setOnClickListener {
            onClickListener.onCanselClick()
        }
        binding.okCanselBtn.ok.setOnClickListener {
            onClickListener.onOkClick()
        }

        return binding.root
    }


    interface OnClickListener{
        fun onCanselClick()
        fun onOkClick()
        fun onCloseClick()
    }



}