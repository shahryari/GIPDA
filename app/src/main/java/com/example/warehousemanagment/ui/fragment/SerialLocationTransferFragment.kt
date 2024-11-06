package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSerialTransferBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.model.classes.checkEnterKey
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.chronometer
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.createAlertDialog
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideKeyboard
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.serial_transfer.SerialTransferProductRow
import com.example.warehousemanagment.model.models.transfer_task.DestinyLocationTransfer
import com.example.warehousemanagment.ui.adapter.DestinyLocationAdapter
import com.example.warehousemanagment.ui.adapter.SerialTransferAdapter
import com.example.warehousemanagment.ui.adapter.SimpleSerialAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetDestinationLocationDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.SerialTransferViewModel

class SerialLocationTransferFragment : BaseFragment<SerialTransferViewModel,FragmentReceivingBinding>() {
    var sortType:String= Utils.LOCATION_CODE_SORT
    var orderType:String= Utils.ASC_ORDER
    var page= Utils.PAGE_START
    var lastPosition=0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        b.swipeLayout.setOnRefreshListener {
            refresh()
        }

        b.filterImg.img.setOnClickListener {
            showFilterSheetDialog()
        }


        refresh()

        observeSourceLocationList()
        observeLocationCount()


        b.mainToolbar.searchIcon.setOnClickListener {
            hideKeyboard(requireActivity())
            refresh()
        }

        b.mainToolbar.searchEdi.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                refresh()
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun observeLocationCount()
    {
        viewModel.getProductSize().
        observe(viewLifecycleOwner) { it ->
            setBelowCount(
                requireActivity(), getString(R.string.tools_you_have), it,
                getString(R.string.manualToTransfer)
            )
        }
    }

    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.locationCode.text=getString(R.string.locationCode)
                binding.relOwnerCode.visibility= View.GONE
                binding.productCode.text=getString(R.string.product_code)
                binding.productTitle.text=getString(R.string.productTitle)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.OwnerCode -> checkTick(binding.ownerCOdeImg,binding)
                    Utils.LOCATION_CODE_SORT -> checkTick(binding.locationCodeImg,binding)
                    Utils.ProductTitle -> checkTick(binding.productTitleImg,binding)
                    Utils.ProductCode -> checkTick(binding.productCodeImg,binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(orderType== Utils.ASC_ORDER){
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
                if (orderType!= Utils.ASC_ORDER)
                {
                    orderType= Utils.ASC_ORDER
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
                if (sortType!= Utils.LOCATION_CODE_SORT)
                {
                    sortType= Utils.LOCATION_CODE_SORT
                    refresh()
                }
            }

            override fun onProductCodeClick()
            {
                if (sortType!= Utils.ProductCode)
                {
                    sortType= Utils.ProductCode
                    refresh()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!= Utils.ProductTitle)
                {
                    sortType= Utils.ProductTitle
                    refresh()
                }

            }

            override fun onOwnerClick() {
                if (sortType!= Utils.OwnerCode)
                {
                    sortType= Utils.OwnerCode
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
        viewModel.clearList()
        setSourceLocationData()
    }

    private fun setSourceLocationData()
    {
        viewModel.getSerialTransferProducts(
            pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi),
            page,sortType,orderType,
            pref.getTokenGlcTest(),
            requireContext(),
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
        viewModel.getTransferProducts()
            .observe(viewLifecycleOwner){
                lastPosition=it.size-1
                showLocationList(it)
            }
    }


    private fun showLocationList(list:List<SerialTransferProductRow>)
    {
        if(lastPosition- Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(
            lastPosition - Utils.ROWS
        )



        val  adapter = SerialTransferAdapter(list, requireActivity(),
            object : SerialTransferAdapter.OnCallBackListener
            {
                override fun onClick(model: SerialTransferProductRow)
                {
                    val dialogBinding = DialogSerialTransferBinding.
                    inflate(LayoutInflater.from(requireActivity()))
                    val dialog= createAlertDialog(dialogBinding,
                        R.drawable.shape_background_rect_border_gray_solid_white, requireActivity())

                    dialogBinding.closeImg.setOnClickListener {dialog.dismiss()}
                    showSerials(dialogBinding)
                    dialogBinding.rel4.cansel.setOnClickListener { dialog.dismiss() }
                    initDialog(dialogBinding,model)
                    clearEdi(
                        dialogBinding.layoutTopInfo.clearImgLocation,
                        dialogBinding.layoutTopInfo.locationDestiny
                    )
                    clearEdi(dialogBinding.layoutTopInfo.clearImg,dialogBinding.layoutTopInfo.quantity)


                    dialogBinding.layoutTopInfo.locationDestiny.doAfterTextChanged()
                    {
                        if (lenEdi(dialogBinding.layoutTopInfo.locationDestiny)!=0)
                        {
                            startTimerForGettingData()
                            {
                                showDestinyLocatoins(
                                    model,
                                    dialogBinding.layoutTopInfo.locationDestiny,
                                )
                            }
                        }
                    }
                    checkEnterKey(dialogBinding.layoutTopInfo.quantity,)
                    {
                        viewModel.checkSerial(
                            pref.getDomain(),
                            model.locationProductID,
                            textEdi(dialogBinding.layoutTopInfo.quantity),
                            pref.getTokenGlcTest(),
                            requireContext(),
                            {
                                dialogBinding.layoutTopInfo.quantity.setText("")
                            },{
                                dialogBinding.layoutTopInfo.quantity.setText("")
                            }
                        )
                    }
                    dialogBinding.layoutTopInfo.scanBarcode.setOnClickListener {
                        viewModel.checkSerial(
                            pref.getDomain(),
                            model.locationProductID,
                            textEdi(dialogBinding.layoutTopInfo.quantity),
                            pref.getTokenGlcTest(),
                            requireContext(),
                            {
                                dialogBinding.layoutTopInfo.quantity.setText("")
                            },{
                                dialogBinding.layoutTopInfo.quantity.setText("")
                            }
                        )
                    }
//                    dialogBinding.layoutTopInfo.locationDestiny
//                        .setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
//                            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                                showConfirmSheetForSubmit(dialogBinding, model, dialog)
//                                return@OnEditorActionListener true
//                            }
//                            false
//                        })



                    dialogBinding.layoutTopInfo.quantity.requestFocus()

                    dialogBinding.rel4.confirm.setOnClickListener()
                    {
                        showConfirmSheetForSubmit(dialogBinding, model, dialog)

                    }
                }

                override fun reachToEnd(position: Int) {
                    page += 1
                    setSourceLocationData()
                }
            })
        b.rv.adapter = adapter

    }

    private fun showSerials(binding: DialogSerialTransferBinding) {
        viewModel.getSerials().observe(viewLifecycleOwner){list->
            val adapter = SimpleSerialAdapter(
                list,requireContext(),
                onDelete = {
                    viewModel.deleteSerial(it)
                },
                {

                }
            )

            binding.rv.adapter = adapter

        }
    }

    private fun showConfirmSheetForSubmit(
        dialogBinding: DialogSerialTransferBinding,
        model: SerialTransferProductRow,
        dialog: AlertDialog
    ) {
        if (
            dialogBinding.layoutTopInfo.locationDestiny.text.isNotEmpty()
        ) {
            if (viewModel.tempSerials.isEmpty()) {
                toast("Please scan serials first", requireActivity())
            } else {
                val sb = getBuiltString(
                    getString(R.string.areYouSureTransfer),
                    model.productName, getString(R.string.from2),
                    model.locationID, getString(R.string.to2),
                    dialogBinding.layoutTopInfo.locationDestiny.text.toString(),
                    getString(R.string.ask2)
                )


                showConfirmSheet(
                    getString(R.string.confirmTransfer), sb, model,
                    textEdi(dialogBinding.layoutTopInfo.locationDestiny),
                    dialog
                )
            }

        } else toast(getString(R.string.fillAllFields), requireActivity())
    }

    private fun showDestinyLocatoins(model: SerialTransferProductRow, locationDestiny:TextView )
    {
        var sheet: SheetDestinationLocationDialog?=null
        sheet= SheetDestinationLocationDialog(
            requireActivity(),
            object : SheetDestinationLocationDialog.OnClickListener{

                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun setRvData(rv: RecyclerView, progressBar: ProgressBar
                                       , countTv:TextView, searchEdi: EditText
                )
                {
                    searchEdi.doAfterTextChanged()
                    {

                        hideKeyboard(requireActivity())
                        if (lenEdi(searchEdi)==0){
                            viewModel.setClearList()
                        }else{
                            viewModel.setClearList()
                            searchForDesiniation(locationDestiny,progressBar,rv, countTv, model, sheet,
                                textEdi(searchEdi))
                        }
                    }


                }

                override fun init(
                    rv: RecyclerView,
                    progressBar: ProgressBar,
                    countTv: TextView,
                    searchEdi: EditText
                ) {
                    searchEdi.setText(locationDestiny.text)
                    hideKeyboard(requireActivity())
                    viewModel.setClearList()
                    searchForDesiniation(locationDestiny,progressBar,rv, countTv, model, sheet,
                        textEdi(searchEdi))
                }


            })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun searchForDesiniation(
        locationDestiny: TextView, progressBar: ProgressBar,
        rv: RecyclerView, countTv: TextView, model: SerialTransferProductRow,
        sheet: SheetDestinationLocationDialog?, searchLocationDestiny: String
    )
    {

        chronometer?.cancel()
        chronometer = object : CountDownTimer(pref.getDelayForInventorySearch(), Utils.COUNT_DOWN_INTERVAL)
        {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish()
            {
                hideKeyboard(requireActivity())
                viewModel.setDestinyLocatoinTransfer(
                    pref.getDomain(),model,pref.getTokenGlcTest(),requireContext(),progressBar,searchLocationDestiny)
                observeDestinyLocations(rv,countTv,locationDestiny,sheet)

            }
        }.start()
    }

    private fun observeDestinyLocations(
        rv: RecyclerView, countTv: TextView, locationDestiny: TextView,
        sheet: SheetDestinationLocationDialog?,
    )
    {
        viewModel.getDestinyLocationTransfer().observe(viewLifecycleOwner
        ) { it ->
            hideKeyboard(requireActivity())
            countTv.text =
                getBuiltString(getString(R.string.tools_scannedItems), it.size.toString())
            showDestinationList(rv, it, sheet, locationDestiny)
        }
    }

    private fun showDestinationList(
        rv: RecyclerView,
        it: List<DestinyLocationTransfer>,
        sheet: SheetDestinationLocationDialog?,
        locationDestiny: TextView,
    ) {
        rv.adapter = DestinyLocationAdapter(
            it,
            requireActivity(),
            object : DestinyLocationAdapter.OnCallBackListener
            {
                override fun onItemClick(model: DestinyLocationTransfer)
                {

                    sheet?.dismiss()
                    locationDestiny.text = model.locationCode
                    chronometer?.cancel()

                }
            })
    }


    private fun showConfirmSheet(
        title: String, msg: String,
        model: SerialTransferProductRow,
        locationIdTo: String,
        dialog: AlertDialog
    )
    {
        var sheet: SheetAlertDialog?=null
        sheet= SheetAlertDialog(title,msg,
            object : SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    sheet?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {
                    viewModel.transferSerials(
                        pref.getDomain(),
                        model.locationProductID,
                        serials = viewModel.tempSerials,
                        destinationLocation = locationIdTo,
                        pref.getTokenGlcTest(),
                        requireContext(),
                        {
                            dialog.dismiss()
                            refresh()
                            toast(getString(R.string.successfullConfirmed),requireContext())
                        },{
                            dialog.dismiss()
                        }
                    )

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


    private fun initDialog(dialogBinding: DialogSerialTransferBinding, model: SerialTransferProductRow)
    {

        dialogBinding.rel4.confirm.text = getString(R.string.transfer)
        dialogBinding.layoutTopInfo.productTitle.text=model.productName
        dialogBinding.layoutTopInfo.quantity.hint = "Serial"
        dialogBinding.layoutTopInfo.quantity.inputType = InputType.TYPE_CLASS_TEXT

    }


    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
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