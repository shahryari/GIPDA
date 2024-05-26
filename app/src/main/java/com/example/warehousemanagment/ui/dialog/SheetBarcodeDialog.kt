package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.DialogSheetInvListBinding
import com.example.warehousemanagment.model.models.BarcodeModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.ui.adapter.BarcodeAdapter
import com.example.warehousemanagment.ui.adapter.InvAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetBarcodeDialog() : BottomSheetDialogFragment()
{
    lateinit var onClickListener:OnClickListener
    lateinit var arrayList: List<BarcodeModel>

    constructor(arrayList:List<BarcodeModel>, onClickListener:OnClickListener) : this(){

        this.onClickListener=onClickListener
        this.arrayList=arrayList
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding=DialogSheetInvListBinding.inflate(inflater,container,false)

        binding.rv.adapter=BarcodeAdapter(arrayList,requireActivity(),object :BarcodeAdapter.OnCallBackListener{
            override fun onClick(model: BarcodeModel) {
                onClickListener.onBarcodeClick(model)
            }

        })
        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }



        return binding.root
    }


    interface OnClickListener{
        fun onCloseClick()
        fun onBarcodeClick(model:BarcodeModel)
    }



}