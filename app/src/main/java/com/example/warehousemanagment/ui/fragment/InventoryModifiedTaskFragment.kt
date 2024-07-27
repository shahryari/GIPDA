package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.DialogTransferBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.inventory.inventory.InventoryModifyRow
import com.example.warehousemanagment.ui.adapter.InventerModifyTaskAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.InventoryModifiedTaskViewModel


class InventoryModifiedTaskFragment
    : BaseFragment<InventoryModifiedTaskViewModel, FragmentReceivingBinding>()
{

    var sortType=Utils.CREATED_ON
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastPosition=0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        b.swipeLayout.setOnRefreshListener()
        {
            /**/
            refresh()
            /**/
        }
        /**/
        refresh()
        /**/


        observeInventoryModifiedTask()
        observeInventoryCount()
        b.filterImg.img.setOnClickListener()
        {
            /**/
            showFilterSheetDialog()
            /**/
        }


    }


    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.locationCode.text=getString(R.string.destinationlcationcode)
                binding.ownerCode.text=getString(R.string.createdOn)
                binding.productCode.text=getString(R.string.source_location_code)
                binding.productTitle.text=getString(R.string.productTitle)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.DestinationLcationCode ->checkTick(binding.locationCodeImg,binding)
                    Utils.CREATED_ON-> checkTick(binding.ownerCOdeImg, binding)
                    Utils.SourceLocationCode ->checkTick(binding.productCodeImg,binding)
                    Utils.ProductTitle-> checkTick(binding.productTitleImg, binding)
                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(receiveOrder==Utils.ASC_ORDER)
                {
                    asc.backgroundTintList= ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
                    desc.backgroundTintList=null
                }else
                {
                    desc.backgroundTintList= ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
                    asc.backgroundTintList=null
                }
            }


            override fun onCloseClick() { sheet?.dismiss() }

            override fun onAscClick()
            {
                if (receiveOrder!=Utils.ASC_ORDER)
                {
                    receiveOrder=Utils.ASC_ORDER
                    refresh()
                }

            }

            override fun onDescClick() {
                if (receiveOrder != Utils.DESC_ORDER) {
                    receiveOrder = Utils.DESC_ORDER
                    refresh()
                }
            }

            override fun onLocationCodeClick() {
                if (sortType!=Utils.DestinationLcationCode)
                {
                    sortType=Utils.DestinationLcationCode
                    refresh()
                }
            }

            override fun onProductCodeClick()
            {
                if (sortType!=Utils.SourceLocationCode)
                {
                    sortType=Utils.SourceLocationCode
                    refresh()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!=Utils.ProductTitle)
                {
                    sortType=Utils.ProductTitle
                    refresh()
                }

            }

            override fun onOwnerClick()
            {
                if (sortType!=Utils.CREATED_ON)
                {
                    sortType=Utils.CREATED_ON
                    refresh()
                }
            }

            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }
    private fun refresh()
    {
        viewModel.dispose()
        receivePage = Utils.PAGE_START
        viewModel.clearReceiveList()
        setInventModifiedTask()
    }


    private fun setInventModifiedTask()
    {
        viewModel.setInventoryModifiedTask(
            pref.getDomain(),textEdi(b.mainToolbar.searchEdi),
            receivePage, Utils.ROWS, sortType
            , receiveOrder, pref.getTokenGlcTest(), b.progressBar,b.swipeLayout)
    }

    private fun observeInventoryModifiedTask()
    {
        viewModel.getInventoryModifeidTask()
            .observe(viewLifecycleOwner, object : Observer<List<InventoryModifyRow>>
            {
                override fun onChanged(it: List<InventoryModifyRow>)
                {
                    b.swipeLayout.isRefreshing=false
                    lastPosition=it.size-1
                    showLocationTaskList(it)
                }

            })
    }
    private fun observeInventoryCount()
    {
        viewModel.getInventoryCount()
            .observe(viewLifecycleOwner, object : Observer<Int>
            {
                override fun onChanged(it: Int)
                {
                    setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                        it, getString(R.string.locationTransferTask))
                }

            })
    }


    private fun showLocationTaskList(list:List<InventoryModifyRow>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition-Utils.ROWS)

        val  adapter = InventerModifyTaskAdapter(list, requireActivity(),
            object : InventerModifyTaskAdapter.OnCallBackListener
        {
            override fun onClick(model: InventoryModifyRow)
            {
                val sb= getBuiltString(getString(R.string.areYouSureTransfer),
                    model.productTitle,
                    getString(R.string.from2),
                    model.sourceLocationCode,
                    getString(R.string.by),
                    model.firstInvTypeTitle,
                    getString(R.string.to2),
                    model.destinationLcationCode,
                    getString(R.string.by),
                    model.invTypeTitle,
                    getString(R.string.ask2))



                showConfirmSheet(model.locationTransferID,
                    getString(R.string.confirmTransfer),sb)

            }
            override fun reachToEnd(position: Int)
            {
                receivePage=receivePage+1
                setInventModifiedTask()
            }
        })
        b.rv.adapter = adapter


        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData {
                /**/
                refresh()
                /**/
            }
        }

//        adapter.setFilter(search(textEdi(b.mainToolbar.searchEdi),
//            list, Utils.DestinationLcationCode,
//            Utils.SourceLocationCode,
//            Utils.ProductCode, Utils.ProductTitle
//            , Utils.OwnerCode, Utils.Quantity))
//        b.mainToolbar.searchEdi.doAfterTextChanged()
//        {
//            adapter.setFilter(search(textEdi(b.mainToolbar.searchEdi),
//                list, Utils.DestinationLcationCode,
//                Utils.SourceLocationCode,
//                Utils.ProductCode, Utils.ProductTitle
//                , Utils.OwnerCode, Utils.Quantity))
//        }


    }


    private fun showConfirmSheet(locationTransferId:String,title:String,msg:String)
    {
        var sheet: SheetAlertDialog?=null
        sheet= SheetAlertDialog(title,msg,
            object : SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                   sheet?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {

                    viewModel.completeInventoryModifiedTask(
                        pref.getDomain(),locationTransferId,pref.getTokenGlcTest(),progress)
                    {
                        sheet?.dismiss()
                        refresh()
                        toast(getString(R.string.successfullConfirmed),requireActivity())
                    }

                }

                override fun onCloseClick() {
                   sheet?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {
//                    refresh()
                }


            })
        sheet.show(this.getParentFragmentManager(), "")
    }





    private fun initDialog(dialogBinding: DialogTransferBinding)
    {

        dialogBinding.rel4.confirm.setText(getString(R.string.transfer))



    }


    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){

        }

    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.inventTypeModifyTask))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }




    override fun getLayout(): Int {
         return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
