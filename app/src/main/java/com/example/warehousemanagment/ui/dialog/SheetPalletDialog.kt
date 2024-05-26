package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetPalletDialog() : BottomSheetDialogFragment()
{
    lateinit var title: String
    lateinit var onClickListener:OnClickListener

    constructor(title:String , onClickListener:OnClickListener) : this(){

        this.title=title
        this.onClickListener=onClickListener
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding=DialogSheetDestinyLocationBinding.inflate(inflater,container,false)

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }
        binding.title.text=title
        onClickListener.initData(binding)

        binding.searchEdi.doAfterTextChanged {

        }


        return binding.root
    }


    interface OnClickListener{
        fun onCloseClick()
        fun initData(rv: DialogSheetDestinyLocationBinding)
    }



}