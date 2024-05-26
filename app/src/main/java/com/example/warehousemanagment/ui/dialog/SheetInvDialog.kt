package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.DialogSheetInvListBinding
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.ui.adapter.InvAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetInvDialog() : BottomSheetDialogFragment()
{
    lateinit var onClickListener:OnClickListener
    lateinit var arrayList: List<CatalogModel>

    constructor(arrayList:List<CatalogModel>,onClickListener:OnClickListener) : this(){

        this.onClickListener=onClickListener
        this.arrayList=arrayList
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding=DialogSheetInvListBinding.inflate(inflater,container,false)

        onClickListener.init(binding)
        binding.rv.adapter=InvAdapter(arrayList,requireActivity(),object :InvAdapter.OnCallBackListener{
            override fun onClick(model: CatalogModel) {
                onClickListener.onInvClick(model)
            }

        })
        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }



        return binding.root
    }


    interface OnClickListener{
        fun onCloseClick()
        fun onInvClick(model:CatalogModel)
        fun init(binding:DialogSheetInvListBinding)
    }



}