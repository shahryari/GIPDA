package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetTaskTypeBinding
import com.example.warehousemanagment.databinding.PatternTaskTypeBinding
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.ui.adapter.TaskTypeAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetTaskDialog() : BottomSheetDialogFragment()
{
    lateinit var onClickListener:OnClickListener
    lateinit var arrayList: List<CatalogModel>

    constructor(arrayList:List<CatalogModel> ,onClickListener:OnClickListener) : this(){

        this.onClickListener=onClickListener
        this.arrayList=arrayList
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding=DialogSheetTaskTypeBinding.inflate(inflater,container,false)
        onClickListener.init(binding)



        binding.title.text=getString(R.string.taskType)

        binding.taskRv.adapter=TaskTypeAdapter(
            arrayList, object :TaskTypeAdapter.OnCallBackListener{
                override fun onClick(model: CatalogModel) {
                     onClickListener.onItemClick(
                         binding,model
                     )
                }


            }
        )

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }



        return binding.root
    }




    interface OnClickListener{
        fun onCloseClick()
        fun onItemClick(binding:DialogSheetTaskTypeBinding,model: CatalogModel)
        fun init(binding: DialogSheetTaskTypeBinding,)
    }



}