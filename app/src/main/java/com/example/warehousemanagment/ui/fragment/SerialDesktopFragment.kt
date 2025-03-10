package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.LayoutSerialDesktopBinding
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.hideView
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.viewmodel.DesktopViewModel


class SerialDesktopFragment() : BaseFragment<DesktopViewModel, LayoutSerialDesktopBinding>()
{


    override fun onViewCreated(v: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(v, savedInstanceState)




        b.appDescriptionTv.text = getString(R.string.serial_base_description)

        b.relSerialPutaway.setOnClickListener {
            navController?.navigate(R.id.action_serialDesktopFragment_to_serialPutawayFragment)
        }

        b.relSerialPutawayAssign.setOnClickListener {
            navController?.navigate(R.id.action_serialDesktopFragment_to_serialPutawayAssignFragment)
        }
        b.relSerialTransfer.setOnClickListener {
            navController?.navigate(R.id.action_serialDesktopFragment_to_serialLocationTransferFragment)
        }

        b.relSerialPicking.setOnClickListener {
            navController?.navigate(R.id.action_serialDesktopFragment_to_serialPickingListFragment)
        }

        b.relSerialShipping.setOnClickListener {
            navController?.navigate(R.id.action_serialDesktopFragment_to_serialShippingFragment)
        }



        
    }



    override fun init()
    {
        setToolbarTitle(requireActivity(),"")

    }





    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        hideView(requireActivity(),R.id.summaryTv,View.GONE)
        hideView(requireActivity(),R.id.rel1, View.VISIBLE)
        hideShortCut(requireActivity())
    }
    override fun getLayout(): Int {
         return R.layout.layout_serial_desktop
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }

    override fun onPause() {
        super.onPause()
        hideView(requireActivity(),R.id.summaryTv,View.VISIBLE)
    }



}