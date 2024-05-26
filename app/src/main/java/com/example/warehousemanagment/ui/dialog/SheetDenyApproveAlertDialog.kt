package com.example.warehousemanagment.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetBottomBinding
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.ui.adapter.SimpleSpinnerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetDenyApproveAlertDialog() : BottomSheetDialogFragment()
{
    lateinit var msg:String
    lateinit var title: String
    lateinit var onClickListener:OnClickListener

    constructor( title:String, msg:String, onClickListener:OnClickListener) : this(){
        this.msg=msg
        this.title=title
        this.onClickListener=onClickListener
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding=DialogSheetBottomBinding.inflate(inflater,container,false)

        binding.msgTv.setText(msg)
        binding.title.setText(title)
        binding.okCanselBtn.ok.text=getString(R.string.approve)
        binding.okCanselBtn.cansel.text=getString(R.string.deny)
        onClickListener.init(binding)

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }
        binding.okCanselBtn.cansel.setOnClickListener {
            onClickListener.onDenyClick()
        }
        binding.okCanselBtn.ok.setOnClickListener {
            onClickListener.onOkClick(binding.progress)
        }




        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onClickListener.onDismiss()
    }


    interface OnClickListener{
        fun init(binding:DialogSheetBottomBinding)
        fun onDenyClick()
        fun onOkClick(progress:ProgressBar)
        fun onCloseClick()
        fun onDismiss()
    }



}