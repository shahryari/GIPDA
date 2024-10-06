package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetTaskTypeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetStockLocationDialog() : BottomSheetDialogFragment() {
    lateinit var onClickListener:OnClickListener
//    lateinit var arrayList: List<StockLocationRow>

    constructor(onClickListener: OnClickListener) : this(){
        this.onClickListener=onClickListener
//        this.arrayList=arrayList
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding= DialogSheetTaskTypeBinding.inflate(inflater,container,false)
        onClickListener.init(binding)



        binding.title.text=getString(R.string.locationCode)


//        binding.relOkCancel.visibility = View.VISIBLE
//        binding.okCanselBtn.ok.setOnClickListener {
//            onClickListener.onItemClick(selectedLocations)
//        }
//
//        binding.okCanselBtn.cansel.setOnClickListener {
//            onClickListener.onCloseClick()
//        }

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }



        return binding.root
    }




    interface OnClickListener{
        fun onCloseClick()
//        fun onItemClick(location: StockLocationRow)
        fun init(binding: DialogSheetTaskTypeBinding,)
    }

}