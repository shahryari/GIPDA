package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetTaskTypeBinding
import com.example.warehousemanagment.model.models.shipping.customer.CustomerModel
import com.example.warehousemanagment.ui.adapter.ChooseCustomerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetChooseCustomerDialog() : BottomSheetDialogFragment() {
    lateinit var onClickListener:OnClickListener
    lateinit var arrayList: List<CustomerModel>
    var selectedCustomers: List<CustomerModel> = emptyList()

    constructor(arrayList:List<CustomerModel>, selectedCustomers: List<CustomerModel>,onClickListener:OnClickListener) : this(){
        this.selectedCustomers = selectedCustomers
        this.onClickListener=onClickListener
        this.arrayList=arrayList
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding= DialogSheetTaskTypeBinding.inflate(inflater,container,false)
        onClickListener.init(binding)



        binding.title.text=getString(R.string.customerCode)

        binding.taskRv.adapter= ChooseCustomerAdapter(
            arrayList,
            selectedCustomers,
            object : ChooseCustomerAdapter.OnCallBackListener {
                override fun onClick(customers: List<CustomerModel>) {
                    selectedCustomers = customers
                }

            }
        )

        binding.relOkCancel.visibility = View.VISIBLE
        binding.okCanselBtn.ok.setOnClickListener {
            onClickListener.onItemClick(selectedCustomers)
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
        fun onItemClick(customers: List<CustomerModel>)
        fun init(binding: DialogSheetTaskTypeBinding,)
    }

}