package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetTaskTypeBinding
import com.example.warehousemanagment.ui.adapter.ChooseLocationAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetChooseLocationDialog() : BottomSheetDialogFragment() {
    lateinit var onClickListener:OnClickListener
    lateinit var arrayList: List<String>
    var selectedLocations: List<String> = emptyList()

    constructor(arrayList:List<String>, selectedLocations: List<String>,onClickListener:OnClickListener) : this(){
        this.selectedLocations = selectedLocations
        this.onClickListener=onClickListener
        this.arrayList=arrayList
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding= DialogSheetTaskTypeBinding.inflate(inflater,container,false)
        onClickListener.init(binding)



        binding.title.text=getString(R.string.locationCode)

        binding.taskRv.adapter= ChooseLocationAdapter(
            arrayList,
            selectedLocations,
            object : ChooseLocationAdapter.OnCallBackListener {
                override fun onClick(locations: List<String>) {
                    selectedLocations = locations
                }

            }
        )

        binding.relOkCancel.visibility = View.VISIBLE
        binding.okCanselBtn.ok.setOnClickListener {
            onClickListener.onItemClick(selectedLocations)
        }

        binding.okCanselBtn.cansel.setOnClickListener {
            onClickListener.onCloseClick()
        }

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }



        return binding.root
    }




    interface OnClickListener{
        fun onCloseClick()
        fun onItemClick(locations: List<String>)
        fun init(binding: DialogSheetTaskTypeBinding,)
    }

}