package com.example.warehousemanagment.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ProgressBar
import com.example.warehousemanagment.databinding.DialogSheetBottomDenyTruckBinding
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.ui.adapter.SimpleSpinnerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetDenyAlertDialog() : BottomSheetDialogFragment()
{
    lateinit var title: String
    lateinit var onClickListener:OnClickListener
    lateinit var reasons: List<CatalogModel>

    constructor(reasons: List<CatalogModel>,title:String, onClickListener:OnClickListener) : this(){
        this.reasons=reasons
        this.title=title
        this.onClickListener=onClickListener
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding=DialogSheetBottomDenyTruckBinding.inflate(inflater,container,false)
        binding.title.setText(title)

        binding.closeImg.setOnClickListener {
            onClickListener.onCloseClick()
        }
        binding.okCanselBtn.cansel.setOnClickListener {
            onClickListener.onCanselClick()
        }
        binding.okCanselBtn.ok.setOnClickListener {
            onClickListener.onOkClick(binding.progress)
        }

            val instituteAdapter = SimpleSpinnerAdapter(reasons, requireActivity())
            binding.rel2.spinner.setAdapter(instituteAdapter)
            binding.rel2.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener
            {
                override fun onItemSelected(parent: AdapterView<*>?,
                                view: View, position: Int, id: Long)
                {
                    onClickListener.selectType(reasons.get(position))
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            })




        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onClickListener.onDismiss()

    }

    interface OnClickListener{
        fun onCanselClick()
        fun onOkClick(progressBar: ProgressBar)
        fun onCloseClick()
        fun selectType(model: CatalogModel)
        fun onDismiss()
    }



}