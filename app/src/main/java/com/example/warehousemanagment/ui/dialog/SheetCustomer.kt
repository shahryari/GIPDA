package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehousemanagment.databinding.DialogSheetChooseCustomerBinding
import com.example.warehousemanagment.model.models.shipping.customer.CustomerInShipping
import com.example.warehousemanagment.ui.adapter.ChooseCustomerColorAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetCustomer() : BottomSheetDialogFragment() {
    lateinit var onClickListener : OnClickListener
    lateinit var customerList: List<CustomerInShipping>
    constructor(customerList: List<CustomerInShipping>, onClickListener: OnClickListener) : this() {
        this.onClickListener = onClickListener
        this.customerList = customerList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogSheetChooseCustomerBinding.inflate(inflater)
        val adapter = ChooseCustomerColorAdapter(
            customerList,requireContext(),object : ChooseCustomerColorAdapter.OnCallBackListener {
                override fun onClick(model: CustomerInShipping) {
                    onClickListener.onChooseColorClick(model)
                    dismiss()
                }

                override fun onClearColor(model: CustomerInShipping) {
                    onClickListener.onClearColorClick(model)
                    dismiss()
                }

            }
        )

        binding.closeImg.setOnClickListener {
            dismiss()
        }

        binding.rv.adapter = adapter
        onClickListener.init(binding)
        return binding.root
    }

    interface OnClickListener {
        fun onChooseColorClick(customer: CustomerInShipping)

        fun onClearColorClick(customer: CustomerInShipping)

        fun init(binding: DialogSheetChooseCustomerBinding)

    }
}