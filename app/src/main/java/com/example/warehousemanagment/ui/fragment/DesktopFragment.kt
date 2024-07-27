package com.example.warehousemanagment.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.FragmentDesktopBinding
import com.example.warehousemanagment.model.classes.check401Error
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.hideView
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.setDescAndCopyRight
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.viewmodel.DesktopViewModel
import org.json.JSONObject


class DesktopFragment() : BaseFragment<DesktopViewModel, FragmentDesktopBinding>()
{


    override fun onViewCreated(v: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(v, savedInstanceState)
        if (pref.getTokenGlcTest().length<= Utils.TOKEN_TEST_LENGTH){
            navController?.navigate(R.id.action_desktopFragment_to_loginFragment)
        }



        val permissions=JSONObject(pref.getUserPermissions())
        log("test4",permissions.toString())
         checkPermissionForLayout(permissions)

        b.swipeLayout.setOnRefreshListener()
        {
             setDashboardData()
        }
        setDashboardData()



        activity?.findViewById<TextView>(R.id.profileName)?.setText(pref.getCurrentUser())
        activity?.findViewById<TextView>(R.id.wareHouseName)?.setText(pref.getWarehouseName())

        b.layoutDesktop.relPutaway.setOnClickListener {
            navController?.navigate(R.id.action_desktopFragment_to_putawayFragment)
        }
        b.layoutDesktop.relReceive.setOnClickListener {
            navController?.navigate(R.id.action_desktopFragment_to_receivingFragment)
        }
        b.layoutDesktop.relWaitForLoading.setOnClickListener {
            navController?.navigate(R.id.action_desktopFragment_to_waitForLoadingFragment)
        }
        b.layoutDesktop.relPicking.setOnClickListener {
            navController?.navigate(R.id.action_desktopFragment_to_pickingListFragment)
        }
        b.layoutDesktop.relShipping.setOnClickListener {
            navController?.navigate(R.id.action_desktopFragment_to_shippingFragment)
        }
        b.layoutDesktop.relCargo.setOnClickListener {
            navController?.navigate(R.id.action_desktopFragment_to_cargoFragment)
        }
        b.layoutDesktop.relMyCargo.setOnClickListener {
            navController?.navigate(R.id.action_desktopFragment_to_myCargoFragment)
        }


        
    }

    private fun setDashboardData()
    {
        viewModel.setDashboard(pref.getDomain(),pref.getTokenGlcTest())
            .subscribe({
                b.swipeLayout.isRefreshing=false
                val model = it[0]
                b.layoutDesktop.receivingCount.text = model.receivingCount.toString()
                b.layoutDesktop.pickingCount.text = model.pickingCount.toString()
                b.layoutDesktop.putawayCount.text = model.putawayCount.toString()
                b.layoutDesktop.shippingCount.text = model.shippingCount.toString()
                b.layoutDesktop.transferCount.text = model.transferCount.toString()
                b.layoutDesktop.cargoCount.text=model.cargoCount.toString()
                b.layoutDesktop.myCargoCount.text=model.myCargoCount.toString();

            }, {
                b.swipeLayout.isRefreshing=false
                if (check401Error(it)==Utils.ERROR_401){
                    pref.saveTokenGlcTest("")
                    navController?.navigate(R.id.action_desktopFragment_to_loginFragment)
                }


            }).let { }
    }

    private fun checkPermissionForOptions(optionValue: Boolean, id: Int)
    {
        if (optionValue==false)
            requireActivity().findViewById<View>(id)?.visibility=View.GONE
        else  (context as Activity).findViewById<View>(id)?.visibility=View.VISIBLE
    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),"")
        setDescAndCopyRight(
            b.copyRightTv,
            b.layoutDesktop.appDescriptionTv,
            requireActivity()
        )

    }

    private fun checkPermissionForLayout(model: JSONObject)
    {
        var receivingCat=0
        var shippingCat=0
        var transferCat=0
        var reportCat=0
        var generalCat=0



        if (!model.optBoolean("receiving"))
            receivingCat+=1
        if (!model.optBoolean("putaway"))
            receivingCat+=1


        if (!model.optBoolean("picking"))
            shippingCat+=1
        if (!model.optBoolean("waitForLoading"))
            shippingCat+=1
        if (!model.optBoolean("shipping"))
            shippingCat+=1
        if (!model.optBoolean("shippingCancel"))
            shippingCat+=1



        if (!model.optBoolean("locationTransfer"))
            transferCat+=1
        if (!model.optBoolean("transferTask"))
            transferCat+=1
        if (!model.optBoolean("inventoryTypeModifyTask"))
            transferCat+=1


        if (!model.optBoolean("locationInventory"))
            reportCat+=1
        if (!model.optBoolean("pickPutReport"))
            reportCat+=1
        if (!model.optBoolean("serialReport"))
            reportCat+=1


        if (!model.optBoolean("productWithoutMaster"))
            generalCat+=1
        if (!model.optBoolean("insertSerial"))
            generalCat+=1
        if (!model.optBoolean("offlineSerial"))
            generalCat+=1
        if (!model.optBoolean("trackingSerial"))
            generalCat+=1


        pref.setPermissionBy(Utils.RECEIVING_CAT,receivingCat)
        pref.setPermissionBy(Utils.SHIPPING_CAT,shippingCat)
        pref.setPermissionBy(Utils.TRANSFER_CAT,transferCat)
        pref.setPermissionBy(Utils.REPORT_CAT,reportCat)
        pref.setPermissionBy(Utils.GENERAL_CAT,generalCat)


        checkPermissionForOptions(model.optBoolean("pickPutReport"),R.id.linPutPick)

        checkPermissionForOptions(model.optBoolean("picking"),R.id.relPicking)

        checkPermissionForOptions(model.optBoolean("receiving"),R.id.relReceive)

        checkPermissionForOptions(model.optBoolean("offlineSerial"),R.id.linOfflineScanSerial)

        checkPermissionForOptions(model.optBoolean("putaway"),R.id.relPutaway)

        checkPermissionForOptions(model.optBoolean("waitForLoading"),R.id.linWaitForLoading)

        checkPermissionForOptions(model.optBoolean("transferTask"),R.id.linTransferTask)

        checkPermissionForOptions(model.optBoolean("inventoryTypeModifyTask"),R.id.linInventoryModify)

        checkPermissionForOptions(model.optBoolean("serialReport"),R.id.linSerialInventory)

        checkPermissionForOptions(model.optBoolean("shippingCancel"),R.id.relCanselShipping)

        checkPermissionForOptions(model.optBoolean("trackingSerial"),R.id.linTrackingSerial)

        checkPermissionForOptions(model.optBoolean("locationTransfer"),R.id.linLocationTransfer)

        checkPermissionForOptions(model.optBoolean("productWithoutMaster"),R.id.linProductWithoutMaster)

        checkPermissionForOptions(model.optBoolean("locationInventory"),R.id.linLocationInventory)

        checkPermissionForOptions(model.optBoolean("insertSerial"),R.id.serials)

        checkPermissionForOptions(model.optBoolean("shipping"),R.id.relShipping)

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
         return R.layout.fragment_desktop
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }

    override fun onPause() {
        super.onPause()
        hideView(requireActivity(),R.id.summaryTv,View.VISIBLE)
    }



}