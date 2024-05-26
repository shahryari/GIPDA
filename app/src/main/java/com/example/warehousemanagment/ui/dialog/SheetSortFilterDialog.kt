package com.example.warehousemanagment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetSortFilterDialog() : BottomSheetDialogFragment()
{
    lateinit var onClickListener:OnClickListener

    constructor( onClickListener:OnClickListener) : this(){

        this.onClickListener=onClickListener
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val binding=DialogSheetSortFilterBinding.inflate(inflater,container,false)

        onClickListener.initView(binding)

        onClickListener.initTickedSort(binding)
        onClickListener.initAscDesc(binding.asc,binding.desc)

        binding.closeImg.setOnClickListener { onClickListener.onCloseClick() }
        binding.asc.setOnClickListener()
        {
            binding.asc.backgroundTintList=ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
            binding.desc.backgroundTintList=null
            onClickListener.onAscClick()
        }
        binding.desc.setOnClickListener()
        {
            binding.desc.backgroundTintList=ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
            binding.asc.backgroundTintList=null
            onClickListener.onDescClick()
        }


        binding.relLocationCode.setOnClickListener()
        {
            checkTick(binding.locationCodeImg,binding)
            onClickListener.onLocationCodeClick()
        }
        binding.relProdcutCode.setOnClickListener()
        {
            checkTick(binding.productCodeImg,binding)
            onClickListener.onProductCodeClick()
        }
        binding.relProductTitle.setOnClickListener()
        {
            checkTick(binding.productTitleImg,binding)
            onClickListener.onProductTitleClick()
        }
        binding.relOwnerCode.setOnClickListener()
        {
            checkTick(binding.ownerCOdeImg,binding)
            onClickListener.onOwnerClick()
        }
        binding.rel5.setOnClickListener {
            checkTick(binding.img5,binding)
            onClickListener.onRel5Click()
        }
        binding.rel6.setOnClickListener {
            checkTick(binding.img6,binding)
            onClickListener.onRel6Click()
        }



        return binding.root
    }



    interface OnClickListener{
        fun initView(binding: DialogSheetSortFilterBinding)
        fun initTickedSort(binding: DialogSheetSortFilterBinding, )
        fun initAscDesc(asc: TextView, desc: TextView)
        fun onCloseClick()
        fun onAscClick()
        fun onDescClick()
        fun onLocationCodeClick()
        fun onProductCodeClick()
        fun onProductTitleClick()
        fun onOwnerClick()
        fun onRel5Click()
        fun onRel6Click(){}

    }



}