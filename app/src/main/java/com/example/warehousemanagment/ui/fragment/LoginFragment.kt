package com.example.warehousemanagment.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DiaologChangeBaseUrlBinding
import com.example.warehousemanagment.databinding.FragmentLoginBinding
import com.example.warehousemanagment.model.classes.NavigationUtils
import com.example.warehousemanagment.model.classes.changeBackgroundTint
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.createAlertDialog
import com.example.warehousemanagment.model.classes.hideKeyboard
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.hideView
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.setDescAndCopyRight
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.login.login.LoginModel
import com.example.warehousemanagment.model.models.login.login.Permissions
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.viewmodel.LoginViewModel


class LoginFragment() :
    BaseFragment<LoginViewModel, FragmentLoginBinding>()
{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        if (pref.getTokenGlcTest().length>= Utils.TOKEN_TEST_LENGTH){
            navController?.navigate(R.id.action_loginFragment_to_desktopFragment)
        }else pref.saveTokenGlcTest("")

        checkFilledEdi(b.username,b.password,b.login.rel,b.login.submit)


        b.login.submit.setOnClickListener()
        {
            login(textEdi(b.username),textEdi(b.password),
                b.login.submit,b.login.progressBar)
        }

        b.username.doAfterTextChanged()
        {
            if (textEdi(b.username).equals("."))
            {
                showDomainDialog()
            }
        }

    }

    private fun showDomainDialog()
    {
        val dialogBinding = DiaologChangeBaseUrlBinding.
        inflate(LayoutInflater.from(requireActivity()), null)

        val domainDialog = createAlertDialog(dialogBinding,
            R.drawable.shape_background_rect_border_gray_solid_white, requireActivity())

        dialogBinding.closeImg.setOnClickListener {
            domainDialog.dismiss()
        }
        dialogBinding.rel4.cansel.setOnClickListener {
            domainDialog.dismiss()
        }

        dialogBinding.domainEdi.setHint(pref.getDomain().replace(Utils.CONSTANT_PART_DOMAIN,""))

        clearEdi(dialogBinding.clearImg,dialogBinding.domainEdi)

        dialogBinding.rel4.ok.setOnClickListener()
        {
            if (lenEdi(dialogBinding.domainEdi)!=0
                && textEdi(dialogBinding.domainEdi).startsWith("http"))
            {
                pref.saveDomain(textEdi(dialogBinding.domainEdi))
                log("newDomain",pref.getDomain())
                domainDialog.dismiss()
            }else if (textEdi(dialogBinding.domainEdi).endsWith("/")) {
                toast(getString(R.string.domainEnd),requireActivity())
            } else if (!textEdi(dialogBinding.domainEdi).startsWith("http")){
                toast(getString(R.string.startDomain),requireActivity())
            }
            else {
                toast(getString(R.string.enterDomain),requireActivity())
            }

        }

        dialogBinding.clearImg.setOnClickListener {
            clearEdi(dialogBinding.clearImg,dialogBinding.domainEdi)
        }

    }


    private fun login(username:String,password:String,submit:TextView,progressBar:ProgressBar)
    {
        log("myDomain:",pref.getDomain())
        viewModel.login(
            pref.getDomain()
            ,username,password, submit, progressBar)
        hideKeyboard(requireActivity())
        viewModel.getLoginResult().observe(viewLifecycleOwner,object :Observer<LoginModel>
        {
            override fun onChanged(it: LoginModel)
            {
                pref.saveTokenGlcTest(it.tokenID)
                pref.saveCurrentUser(it.fullName)
                pref.saveWareHouseName(it.warehouse)

                pref.saveAtLeastCountForReceivingQuantity(it.randomCheckCount)

                checkPermissionForLayout(it.permissions)


                NavigationUtils.navigateSafe(
                    navController!!,
                    R.id.action_loginFragment_to_desktopFragment,
                    null
                )

            }
        })

    }

    private fun checkPermissionForLayout(model: Permissions,)
    {
        pref.setUserPermissiosns(model.toString())
        log("test",model.toString())
        log("test2",pref.getUserPermissions().toString())

        var receivingCat=0
        var shippingCat=0
        var transferCat=0
        var reportCat=0
        var generalCat=0


        if (model.receiving==false)
            receivingCat+=1
        if (model.putaway==false)
            receivingCat+=1


        if (model.picking==false)
            shippingCat+=1
        if (model.waitForLoading==false)
            shippingCat+=1
        if (model.shipping==false)
            shippingCat+=1
        if (model.shippingCancel==false)
            shippingCat+=1



        if (model.locationTransfer==false)
            transferCat+=1
        if (model.transferTask==false)
            transferCat+=1
        if (model.inventoryTypeModifyTask==false)
            transferCat+=1


        if (model.locationInventory==false)
            reportCat+=1
        if (model.pickPutReport==false)
            reportCat+=1
        if (model.serialReport==false)
            reportCat+=1


        if (model.productWithoutMaster==false)
            generalCat+=1
        if (model.insertSerial==false)
            generalCat+=1
        if (model.insertSerial==false)
            generalCat+=1
        if (model.trackingSerial==false)
            generalCat+=1


        pref.setPermissionBy(Utils.RECEIVING_CAT,receivingCat)
        pref.setPermissionBy(Utils.SHIPPING_CAT,shippingCat)
        pref.setPermissionBy(Utils.TRANSFER_CAT,transferCat)
        pref.setPermissionBy(Utils.REPORT_CAT,reportCat)
        pref.setPermissionBy(Utils.GENERAL_CAT,generalCat)


        checkPermissionForOptions(model.pickPutReport,R.id.linPutPick)

        checkPermissionForOptions(model.picking,R.id.relPicking)

        checkPermissionForOptions(model.receiving,R.id.relReceive)

        checkPermissionForOptions(model.offlineSerial,R.id.linOfflineScanSerial)

        checkPermissionForOptions(model.putaway,R.id.relPutaway)

        checkPermissionForOptions(model.waitForLoading,R.id.linWaitForLoading)

        checkPermissionForOptions(model.transferTask,R.id.linTransferTask)

        checkPermissionForOptions(model.inventoryTypeModifyTask,R.id.linInventoryModify)

        checkPermissionForOptions(model.serialReport,R.id.linSerialInventory)

        checkPermissionForOptions(model.shippingCancel,R.id.relCanselShipping)

        checkPermissionForOptions(model.trackingSerial,R.id.linTrackingSerial)

        checkPermissionForOptions(model.locationTransfer,R.id.linLocationTransfer)

        checkPermissionForOptions(model.productWithoutMaster,R.id.linProductWithoutMaster)

        checkPermissionForOptions(model.locationInventory,R.id.linLocationInventory)

        checkPermissionForOptions(model.insertSerial,R.id.serials)

        checkPermissionForOptions(model.shipping,R.id.relShipping)

    }

    private fun checkPermissionForOptions(optionValue:Boolean,id:Int,)
    {
        if (optionValue==false)
            requireActivity().findViewById<View>(id)?.visibility=View.GONE
        else  (context as Activity).findViewById<View>(id)?.visibility=View.VISIBLE
    }

    private fun checkFilledEdi(ediUsername:EditText,ediPassword:EditText,rel:RelativeLayout,submit:TextView)
    {
        ediUsername.addTextChangedListener()
        {
            checkLoginEnabled(ediUsername, ediPassword, rel,submit,R.color.mainYellow,R.color.paleYellow2)
        }
        ediPassword.addTextChangedListener()
        {
            checkLoginEnabled(ediUsername,ediPassword,rel,submit,R.color.mainYellow,R.color.paleYellow2)
        }

    }

    private fun checkLoginEnabled(ediUsername: EditText, ediPassword: EditText,
                                  rel: RelativeLayout, submit: TextView,colorMain:Int,colorSecond:Int) {
        if (textEdi(ediUsername).length == 0 || textEdi(ediPassword).length == 0)
        {
            changeBackgroundTint(rel, requireActivity(), colorSecond)
            submit.isClickable=false
            submit.isEnabled=false
        } else
        {
            changeBackgroundTint(rel, requireActivity(),colorMain)
            submit.isClickable=true
            submit.isEnabled=true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(view!=null){
            viewModel.getLoginResult().removeObservers(viewLifecycleOwner)
        }

    }



    override fun onResume()
    {
        super.onResume()
        requireActivity().findViewById<RelativeLayout>(R.id.rel1)?.visibility=View.GONE
        requireActivity().findViewById<TextView>(R.id.summaryTv)?.visibility=View.GONE

        hideView(requireActivity(),R.id.rel1,View.GONE)
        hideView(requireActivity(),R.id.summaryTv,View.GONE)

        hideShortCut(requireActivity())

    }


    override fun init()
    {
       setDescAndCopyRight(b.copyRightTv,b.appDescriptionTv,requireActivity())
       changeBackgroundTint(b.login.rel,requireActivity(),R.color.paleYellow2)
//
    }



    override fun getLayout(): Int {
         return R.layout.fragment_login
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}