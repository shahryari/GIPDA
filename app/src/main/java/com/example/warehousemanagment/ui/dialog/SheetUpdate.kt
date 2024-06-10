package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehousemanagment.databinding.DialogSheetUpdateBinding
import com.example.warehousemanagment.model.classes.mySheetAlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetUpdate() : BottomSheetDialogFragment() {
    lateinit var onClickListener: OnClickListener
    constructor(onClickListener: OnClickListener) : this() {

        this.onClickListener = onClickListener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogSheetUpdateBinding.inflate(inflater, container, false)

        onClickListener.init(binding)

        binding.update.setOnClickListener {
            onClickListener.onUpdate()
        }
        dialog?.setCancelable(false)
        mySheetAlertDialog?.isCancelable = false

        return binding.root
    }



    interface OnClickListener {
        fun init(binding: DialogSheetUpdateBinding)
        fun onUpdate()
    }
}
