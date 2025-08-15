package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.DialogTransferBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.model.classes.checkEnterKey
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.createAlertDialog
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideKeyboard
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.transfer_task.source_location.SourceLocationRow
import com.example.warehousemanagment.ui.adapter.ManualLocationTransAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.ManualLocationTransViewModel


class ManualLocationTransferFragment :
    BaseFragment<ManualLocationTransViewModel, FragmentReceivingBinding>()
{

    var sortType:String=Utils.LOCATION_CODE_SORT
    var orderType:String=Utils.ASC_ORDER
    var page=Utils.PAGE_START
    var lastPosition=0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
//        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        clearEdi(b.locationClearImg,b.locationCode)
        clearEdi(b.productCodeImg,b.productCode)
        b.swipeLayout.setOnRefreshListener {
            refresh()
        }

        b.filterImg.root.visibility = View.GONE
        b.mainToolbar.root.visibility = View.GONE
        b.setting.visibility = View.VISIBLE
        b.relSetting.setOnClickListener {
            showFilterSheetDialog()
        }
        b.relFilter.setOnClickListener {
            refresh()
        }

        b.lin1.visibility = View.VISIBLE
//        b.filterImg.img.setOnClickListener {
//            showFilterSheetDialog()
//        }


        refresh()

        observeSourceLocationList()
        observeLocationCount()


//        b.mainToolbar.searchIcon.setOnClickListener {
//            hideKeyboard(requireActivity())
//            refresh()
//        }

//        b.mainToolbar.searchEdi.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                refresh()
//                return@OnEditorActionListener true
//            }
//            false
//        })

    }

    fun observeLocationCount()
    {
        viewModel.getSourceLocationTransferCount().
        observe(viewLifecycleOwner,object :Observer<Int>
        {
            override fun onChanged(it:Int)
            {
                setBelowCount(requireActivity(),getString(R.string.tools_you_have),it,
                    getString(R.string.manualToTransfer))

            }
        })
    }

    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.locationCode.text=getString(R.string.locationCode)
                binding.relOwnerCode.visibility=View.GONE
                binding.productCode.text=getString(R.string.product_code)
                binding.productTitle.text=getString(R.string.productTitle)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.OwnerCode ->checkTick(binding.ownerCOdeImg,binding)
                    Utils.LOCATION_CODE_SORT ->checkTick(binding.locationCodeImg,binding)
                    Utils.ProductTitle ->checkTick(binding.productTitleImg,binding)
                    Utils.ProductCode ->checkTick(binding.productCodeImg,binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(orderType==Utils.ASC_ORDER){
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
                if (orderType!=Utils.ASC_ORDER)
                {
                    orderType=Utils.ASC_ORDER
                    refresh()
                }

            }

            override fun onDescClick() {
                if (orderType != Utils.DESC_ORDER) {
                    orderType = Utils.DESC_ORDER
                    refresh()
                }
            }

            override fun onLocationCodeClick() {
                if (sortType!=Utils.LOCATION_CODE_SORT)
                {
                    sortType=Utils.LOCATION_CODE_SORT
                    refresh()
                }
            }

            override fun onProductCodeClick()
            {
                if (sortType!=Utils.ProductCode)
                {
                    sortType=Utils.ProductCode
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

            override fun onOwnerClick() {
                if (sortType!=Utils.OwnerCode)
                {
                    sortType=Utils.OwnerCode
                    refresh()
                }

            }

            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }

    private fun refresh() {
        hideKeyboard(requireActivity())
        viewModel.dispose()
        page = Utils.PAGE_START
        viewModel.clearReportList()
        setSourceLocationData()
    }

    private fun setSourceLocationData()
    {
        viewModel.setSourceLocationTransfer(
            pref.getDomain(),
            textEdi(b.locationCode),
            textEdi(b.productCode),
            pref.getTokenGlcTest(),
            page,Utils.ROWS,sortType,orderType,
            b.progressBar,
            b.swipeLayout
        )
    }


//    private fun searchForDesiniation(
//        locationDestiny: TextView, progressBar: ProgressBar,
//        rv: RecyclerView, countTv: TextView, model: SourceLocationRow,
//        sheet: SheetDestinationLocationDialog?, searchLocationDestiny: String, hostLocationCode: String
//    )
//    {
//
//        chronometer?.cancel()
//        chronometer = object : CountDownTimer(pref.getDelayForInventorySearch(), Utils.COUNT_DOWN_INTERVAL)
//        {
//            override fun onTick(millisUntilFinished: Long) {}
//            override fun onFinish()
//            {
//                hideKeyboard(requireActivity())
//                viewModel.setDestinyLocatoinTransfer(
//                    pref.getDomain(),model,pref.getTokenGlcTest(),progressBar,searchLocationDestiny)
//                observeDestinyLocations(rv,countTv,locationDestiny,sheet,hostLocationCode)
//
//            }
//        }.start()
//    }

    private fun observeSourceLocationList()
    {
        viewModel.getSourceLocationTransfer()
            .observe(viewLifecycleOwner, object : Observer<List<SourceLocationRow>>
            {
                override fun onChanged(it: List<SourceLocationRow>)
                {
                    lastPosition=it.size-1
                    showLocationList(it)
                }
            })
    }


    private fun showLocationList(list:List<SourceLocationRow>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(
            lastPosition -Utils.ROWS
        )



        val  adapter = ManualLocationTransAdapter(list, requireActivity(),
            object : ManualLocationTransAdapter.OnCallBackListener
            {
                override fun onClick(model: SourceLocationRow)
                {
                    val dialogBinding = DialogTransferBinding.
                    inflate(LayoutInflater.from(requireActivity()), null)
                    val dialog=createAlertDialog(dialogBinding,
                        R.drawable.shape_background_rect_border_gray_solid_white, requireActivity())

                    dialogBinding.closeImg.setOnClickListener {dialog.dismiss()}
                    dialogBinding.rel4.cansel.setOnClickListener { dialog.dismiss() }
                    initDialog(dialogBinding,model)
                    clearEdi(
                        dialogBinding.layoutTopInfo.clearImgLocation,
                        dialogBinding.layoutTopInfo.locationDestiny
                    )



                    checkEnterKey(dialogBinding.layoutTopInfo.locationDestiny,)
                    {
                        showConfirmSheetForSubmit(dialogBinding, model, dialog)
                    }
                    dialogBinding.layoutTopInfo.locationDestiny
                        .setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            showConfirmSheetForSubmit(dialogBinding, model, dialog)
                            return@OnEditorActionListener true
                        }
                        false
                    })

                    dialogBinding.layoutTopInfo.quantity.requestFocus()

                    dialogBinding.rel4.confirm.setOnClickListener()
                    {
                        showConfirmSheetForSubmit(dialogBinding, model, dialog)

                    }
                }

                override fun reachToEnd(position: Int) {
                    page=page+1
                    setSourceLocationData()
                }
            })
        b.rv.adapter = adapter

    }

    private fun showConfirmSheetForSubmit(
        dialogBinding: DialogTransferBinding,
        model: SourceLocationRow,
        dialog: AlertDialog
    ) {
        if (lenEdi(dialogBinding.layoutTopInfo.quantity) != 0 &&
            dialogBinding.layoutTopInfo.locationDestiny.text.length != 0
        ) {
            val sb = getBuiltString(
                getString(R.string.areYouSureTransfer),
                model.productTitle, getString(R.string.from2),
                model.locationCode, getString(R.string.to2),
                dialogBinding.layoutTopInfo.locationDestiny.text.toString(),
                getString(R.string.ask2)
            )


            showConfirmSheet(
                getString(R.string.confirmTransfer), sb, model,
                textEdi(dialogBinding.layoutTopInfo.locationDestiny),
                textEdi(dialogBinding.layoutTopInfo.quantity).toInt(),
                textEdi(b.mainToolbar.searchEdi),
                dialog
            )
        } else toast(getString(R.string.fillAllFields), requireActivity())
    }

//    private fun showDestinyLocatoins(model: SourceLocationRow, locationDestiny:TextView )
//    {
//        var sheet: SheetDestinationLocationDialog?=null
//        sheet= SheetDestinationLocationDialog(
//            requireActivity(),
//            object : SheetDestinationLocationDialog.OnClickListener{
//
//                override fun onCloseClick() {
//                    sheet?.dismiss()
//                }
//
//                override fun setRvData(rv: RecyclerView, progressBar: ProgressBar
//                                       ,countTv:TextView,searchLocationDestiny:EditText)
//                {
//                    searchLocationDestiny.doAfterTextChanged()
//                    {
//
//                        hideKeyboard(requireActivity())
//                        if (lenEdi(searchLocationDestiny)==0){
//                            viewModel.setClearList()
//                        }else{
//                            viewModel.setClearList()
//                            searchForDesiniation(locationDestiny,progressBar,rv, countTv, model, sheet,
//                                textEdi(searchLocationDestiny),model.locationCode)
//                        }
//                    }
//
//
//                }
//
//                override fun init(
//                    rv: RecyclerView,
//                    progressBar: ProgressBar,
//                    countTv: TextView,
//                    searchLocationDestiny: EditText
//                ) {
//                    searchLocationDestiny.setText(locationDestiny.text)
//                    hideKeyboard(requireActivity())
//                    viewModel.setClearList()
//                    searchForDesiniation(locationDestiny,progressBar,rv, countTv, model, sheet,
//                        textEdi(searchLocationDestiny),model.locationCode)
//                }
//
//
//            })
//        sheet.show(this.getParentFragmentManager(), "")
//    }

//    private fun observeDestinyLocations(
//        rv: RecyclerView, countTv: TextView, locationDestiny: TextView,
//        sheet: SheetDestinationLocationDialog?,
//        hostLocationCode: String
//    )
//    {
//        viewModel.getDestinyLocationTransfer().observe(viewLifecycleOwner,
//            object : Observer<List<DestinyLocationTransfer>>
//            {
//                override fun onChanged(it: List<DestinyLocationTransfer>)
//                {
//                    hideKeyboard(requireActivity())
//                    countTv.text= getBuiltString(getString(R.string.tools_scannedItems),it.size.toString())
//                    showDestinationList(rv, it, sheet, locationDestiny,hostLocationCode)
//                }
//
//            })
//    }

//    private fun showDestinationList(
//        rv: RecyclerView,
//        it: List<DestinyLocationTransfer>,
//        sheet: SheetDestinationLocationDialog?,
//        locationDestiny: TextView,
//        hostLocationCode: String
//    ) {
//        rv.adapter = DestinyLocationAdapter(
//            it,
//            requireActivity(),
//            object : DestinyLocationAdapter.OnCallBackListener
//            {
//                override fun onItemClick(model: DestinyLocationTransfer)
//                {
//                    if (model.locationCode.equals(hostLocationCode,ignoreCase = true))
//                    {
//                        toast(getString(R.string.locationHostCantBeEqual),requireActivity())
//                    }else{
//                        sheet?.dismiss()
//                        locationDestiny.text = model.locationCode
//                        locationDestinyId = model.locationID
//                        chronometer?.cancel()
//                    }
//
//                }
//            })
//    }

    private fun showConfirmSheet(title: String, msg: String,
                                 model: SourceLocationRow,
                                 locationIdTo:String, quantity:Int, locationCode: String,
                                 dialog:AlertDialog)
    {
        var sheet: SheetAlertDialog?=null
        sheet= SheetAlertDialog(title,msg,
            object : SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    sheet?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {
                    viewModel.setSubmitLocationTransfer(
                        pref.getDomain(),pref.getTokenGlcTest(),model,
                        locationIdTo,quantity, locationCode,progress, onError = {
                            sheet?.dismiss()
                    })
                    {
                        dialog.dismiss()
                        sheet?.dismiss()
                        setSourceLocationData()
                        toast(getString(R.string.successfullConfirmed),requireActivity())
                    }

                }

                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {

                }


            })
        sheet.show(this.getParentFragmentManager(), "")
    }


    private fun initDialog(dialogBinding: DialogTransferBinding, model: SourceLocationRow)
    {

        dialogBinding.rel4.confirm.setText(getString(R.string.transfer))
        dialogBinding.layoutTopInfo.productTitle.text=model.productTitle


        dialogBinding.layoutTopInfo.availableinventory.setText(
            getBuiltString(
                "Available Inventory : ",
                model.availableInventory.toString()
            )
        )

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
        setToolbarTitle(requireActivity(),getString(R.string.locationTransfer))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }




    override fun getLayout(): Int {
        return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
