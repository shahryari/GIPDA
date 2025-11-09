package com.example.warehousemanagment.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.warehousemanagment.databinding.DialogSheetBottomBinding
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.textEdi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetAlertDialog() : BottomSheetDialogFragment()
{
    lateinit var msg:String
    lateinit var title: String
    lateinit var onClickListener:OnClickListener

    constructor(title:String,msg:String, onClickListener:OnClickListener) : this(){
        this.msg=msg
        this.title=title
        this.onClickListener=onClickListener
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val binding=DialogSheetBottomBinding.inflate(inflater,container,false)
        binding.msgTv.setText(msg)
        binding.title.setText(title)

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }
        binding.okCanselBtn.cansel.setOnClickListener {
            onClickListener.onCanselClick()
        }
        binding.okCanselBtn.ok.setOnClickListener {
            onClickListener.onOkClick(binding.progress, textEdi(binding.countEdi))
        }
        onClickListener.hideCansel(binding.okCanselBtn.cansel)

        clearEdi(binding.clearImg,binding.countEdi)

        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onClickListener.onDismiss()
    }

    interface OnClickListener{
        fun onCanselClick()
        fun onOkClick(progress: ProgressBar, toInt: String)
        fun onCloseClick()
        fun hideCansel(cansel:TextView)
        fun onDismiss()
    }



}