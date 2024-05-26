package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.kotlin_wallet.ui.base.BaseFragment
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.*
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.shipping.RemoveShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.ShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailRow
import com.example.warehousemanagment.model.models.transfer_task.DestinyLocationTransfer
import com.example.warehousemanagment.ui.adapter.DestinyLocationAdapter
import com.example.warehousemanagment.ui.adapter.ShipingDetailAdapter
import com.example.warehousemanagment.ui.adapter.ShippingSerialAdapter
import com.example.warehousemanagment.ui.dialog.*
import com.example.warehousemanagment.viewmodel.ShippingDetailViewModel
import java.lang.StringBuilder


class ShippingDetailFragment :
    BaseFragment<ShippingDetailViewModel,FragmentDetailReceivingBinding>()
{
    lateinit var shippingId:String
    var quantitySerial:Int=0
    var serialSize:Int=0

    var sortType=Utils.ProductTitle
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastPosition=0

    var reasonId:Int ?=null
    var locationDestinyId:String ?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        shippingId= arguments?.getString(Utils.ShippingId).toString()

        setShippingDetail()

        b.swipeLayout.setOnRefreshListener {
            refresh()
        }

        observeShippingDetail()
        observeShippingCount()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)
        clearEdi(b.mainToolbar2.clearImg,b.mainToolbar2.searchEdi)

        b.filterImg.img.setOnClickListener {
            showFilterSheetDialog()
        }

        onChangeCustomerName()



    }

    private fun onChangeCustomerName()
    {
        b.mainToolbar2.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData()
            {
                refresh()
            }
        }
    }



    private fun observeShippingCount()
    {
        viewModel.getShippingCount().observe(viewLifecycleOwner,
        object :Observer<ShippingDetailModel>
        {
            override fun onChanged(it: ShippingDetailModel)
            {
                setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                    it.total, " "+getString(R.string.productsAnd)+" "+
                             it.remain+ " "+getString(R.string.Items))
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
                binding.locationCode.text=getString(R.string.customerFullName)
                binding.relOwnerCode.visibility=View.GONE
                binding.productCode.text=getString(R.string.product_code)
                binding.productTitle.text=getString(R.string.productTitle)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.CUSTOMER_FULL_NAME ->checkTick(binding.locationCodeImg,binding)
                    Utils.ProductTitle ->checkTick(binding.productTitleImg,binding)
                    Utils.ProductCode ->checkTick(binding.productCodeImg,binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(receiveOrder==Utils.ASC_ORDER){
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
                if (sortType!=Utils.CUSTOMER_FULL_NAME) {
                    sortType=Utils.CUSTOMER_FULL_NAME
                    refresh()
                }
            }

            override fun onProductCodeClick() {
                if (sortType!=Utils.ProductCode) {
                    sortType=Utils.ProductCode
                    refresh()
                }

            }

            override fun onProductTitleClick() {
                if (sortType!=Utils.ProductTitle) {
                    sortType=Utils.ProductTitle
                    refresh()
                }

            }

            override fun onOwnerClick() {


            }

            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }

    private fun refresh() {
        dispose()
        receivePage = Utils.PAGE_START
        viewModel.clearList()
        setShippingDetail()
    }

    private fun observeShippingDetail()
    {
        viewModel.getShippingDetail().observe(viewLifecycleOwner,
            object : Observer<List<ShippingDetailRow>>
            {
                override fun onChanged(it: List<ShippingDetailRow>)
                {
                    if (isAdded && view != null)
                    {
                        b.swipeLayout.isRefreshing=false

                        lastPosition=it.size-1
                        showShippingDetailList(it)

                    }
                }
            })
    }

    private fun showShippingDetailList(list: List<ShippingDetailRow>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition-Utils.ROWS)

        val adapter = ShipingDetailAdapter(list, requireActivity(),
             object : ShipingDetailAdapter.OnCallBackListener
            {
                override fun onClick(model:ShippingDetailRow)
                {

                    if (model.serializable==true) {
                        showScanDialog(model)
                    }
                    else
                    {
                        val sb = getBuiltString(getString(R.string.areYouSureProductTitle),
                            " ",model.productTitle," ",
                            getString(R.string.and)," ",model.productCode)

                        showConfirmSheetDialog(
                            getString(R.string.count), sb,
                            model.shippingAddressDetailID, null,model.quantity,true)


                    }
                }

                override fun reachToEnd(position: Int)
                {
                    receivePage=receivePage+1
                    setShippingDetail()
                }

                override fun onCloseClick(model: ShippingDetailRow) {
                    showCancelShippingDialog(model)
                }

                override fun init(binding: PatternShippingDetailBinding) {

                }
            })
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged {
            startTimerForReceiveingData()
        }

    }
    private fun showCancelShippingDialog(model: ShippingDetailRow)
    {


        val dialogBinding = DialogCancelShippingBinding.inflate(
            LayoutInflater.from(requireActivity()), null
        )
        val dialog = createAlertDialog(
            dialogBinding,
            R.drawable.shape_background_rect_border_gray_solid_white, requireActivity()
        )

        dialogBinding.closeImg.setOnClickListener { dialog.dismiss() }
        dialogBinding.rel4.cansel.setOnClickListener { dialog.dismiss() }
        initCancelShippingDialog(dialogBinding.layoutTopInfo,model)

        dialog.setOnDismissListener { refresh() }

        clearEdi(
            dialogBinding.layoutTopInfo.quantityclearImg,
            dialogBinding.layoutTopInfo.quantity)
        clearEdi(
            dialogBinding.layoutTopInfo.selectReasonImg,
            dialogBinding.layoutTopInfo.selectReason)
        clearEdi(
            dialogBinding.layoutTopInfo.destinationLocationClearImg,
            dialogBinding.layoutTopInfo.desinationLocation
        )

        dialogBinding.layoutTopInfo.selectReason.doAfterTextChanged {
            if (lenEdi(dialogBinding.layoutTopInfo.selectReason)==0)
                reasonId=null
        }
        dialogBinding.layoutTopInfo.selectReason.setOnClickListener {
            showReasonList(dialogBinding.layoutTopInfo.selectReason)
        }

        dialogBinding.layoutTopInfo.desinationLocation.doAfterTextChanged()
        {
            if (lenEdi(dialogBinding.layoutTopInfo.desinationLocation)!=0)
            {
                startTimerForGettingData()
                {
                    showDestinyLocatoins(
                        model,
                        dialogBinding.layoutTopInfo.desinationLocation,
                    )
                }
            }
        }




        dialogBinding.rel4.confirm.setOnClickListener {
            if (
                lenEdi(dialogBinding.layoutTopInfo.selectReason)!=0
                && lenEdi(dialogBinding.layoutTopInfo.quantity)!=0
                && lenEdi(dialogBinding.layoutTopInfo.desinationLocation)!=0
            )
            {
                if(
                    dialogBinding.layoutTopInfo.quantity.text.toString().toInt()
                    > model.quantity
                ){
                    toast(getString(R.string.quantityBiggerThan)+" "+model.quantity,requireActivity())
                }else
                {
                    viewModel.revoke(
                        baseUrl = pref.getDomain(),
                        model=model,
                        cookie = pref.getTokenGlcTest(),
                        progressBar = dialogBinding.progress,
                        quantity = dialogBinding.layoutTopInfo.quantity.text.toString().toInt(),
                        loadingCancelId = reasonId!!,
                        locationDestination=locationDestinyId!!,
                    ){
                        dialog.dismiss()
                    }
                }



            }else toast(getString(R.string.fillAllFields),requireActivity())

        }
        dialogBinding.rel4.cansel.setOnClickListener {
            dialog.dismiss()
        }



    }

    private fun showReasonList(reasonTv:TextView)
    {
        var sheet: SheetInvDialog? = null
        sheet = SheetInvDialog(viewModel.reasonList(),
            object : SheetInvDialog.OnClickListener {
            override fun onCloseClick() {
                sheet?.dismiss()
            }

            override fun onInvClick(model: CatalogModel) {
                reasonTv.text = model.title
                reasonId=model.valueField
                sheet?.dismiss()

            }

            override fun init(binding: DialogSheetInvListBinding) {
                 binding.title.text=getString(R.string.reason)
            }

            })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun showDestinyLocatoins(model: ShippingDetailRow, locationDestiny:TextView )
    {
        var sheet: SheetDestinationLocationDialog?=null
        sheet= SheetDestinationLocationDialog(
            requireActivity(),
            object : SheetDestinationLocationDialog.OnClickListener{

                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun setRvData(rv: RecyclerView, progressBar: ProgressBar
                                       , countTv:TextView, searchLocationDestiny:EditText)
                {
                    searchLocationDestiny.doAfterTextChanged()
                    {

                        hideKeyboard(requireActivity())
                        if (lenEdi(searchLocationDestiny)==0){
                            locationDestinyId=null
                            viewModel.setClearList()
                        }else{
                            viewModel.setClearList()
                            searchForDesiniation(locationDestiny,progressBar,rv, countTv, model, sheet,
                                textEdi(searchLocationDestiny))
                        }
                    }


                }

                override fun init(
                    rv: RecyclerView,
                    progressBar: ProgressBar,
                    countTv: TextView,
                    searchLocationDestiny: EditText
                ) {
                    searchLocationDestiny.setText(locationDestiny.text)
                    hideKeyboard(requireActivity())
                    viewModel.setClearList()
                    searchForDesiniation(locationDestiny,progressBar,rv, countTv, model, sheet,
                        textEdi(searchLocationDestiny))
                }


            })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun searchForDesiniation(
        locationDestiny: TextView, progressBar: ProgressBar,
        rv: RecyclerView, countTv: TextView, model: ShippingDetailRow,
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
                viewModel.setRevokeLocation(
                    pref.getDomain(),model,pref.getTokenGlcTest(),progressBar,searchLocationDestiny)
                observeDestinyLocations(rv,countTv,locationDestiny,sheet)

            }
        }.start()
    }

    private fun observeDestinyLocations(
        rv: RecyclerView, countTv: TextView, locationDestiny: TextView,
        sheet: SheetDestinationLocationDialog?,
    )
    {
        viewModel.getRevokLocation().observe(viewLifecycleOwner,
            object : Observer<List<DestinyLocationTransfer>>
            {
                override fun onChanged(it: List<DestinyLocationTransfer>)
                {
                    hideKeyboard(requireActivity())
                    countTv.text= getBuiltString(getString(R.string.tools_scannedItems),it.size.toString())
                    showDestinationList(rv, it, sheet, locationDestiny)
                }

            })
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
                    locationDestinyId = model.locationID
                    chronometer?.cancel()

                }
            })
    }


    private fun initCancelShippingDialog(layout: LayoutCancelShippingBinding, model: ShippingDetailRow)
    {

        layout.product.text=model.productTitle
        layout.productCode.text=model.productCode
        layout.owner.text=model.ownerCode
        layout.invType.text=model.invTypeTitle
        layout.customerName.text=model.customerFullName
        layout.quantityCount.text=model.quantity.toString()


    }


    private fun startTimerForReceiveingData()
    {
        chronometer?.cancel()
        chronometer = object : CountDownTimer(Utils.DELAY_SERIAL, 100)
        {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                refresh()
            }
        }.start()
    }

    private fun showScanDialog(model: ShippingDetailRow)
    {
        val dialogBinding = DialogSerialScanBinding.inflate(
            LayoutInflater.from(requireActivity()), null
        )
        val dialog = createAlertDialog(
            dialogBinding,
            R.drawable.shape_background_rect_border_gray_solid_white, requireActivity()
        )

        initSheepingDialog(dialogBinding, model)

        dispose()
        viewModel.setShippingSerials(
            pref.getDomain(),model.shippingAddressDetailID,
            pref.getTokenGlcTest())
        observeSerialList(dialogBinding,dialogBinding.layoutTopInfo.serialEdi)

        clearEdi(
            dialogBinding.layoutTopInfo.clearImg,
            dialogBinding.layoutTopInfo.serialEdi
        )
        clearEdi(dialogBinding.clearImg, dialogBinding.searchEdi)


        checkEnterKey(dialogBinding.layoutTopInfo.serialEdi,)
        {
            addSerialByBoth(dialogBinding, model)
        }


        dialogBinding.layoutTopInfo.add.setOnClickListener()
        {
            addSerialByBoth(dialogBinding, model)
        }



        dialogBinding.rel4.confirm.setOnClickListener()
        {
            if(serialSize!=0)
            {
                val sb = getBuiltString(getString(R.string.youScan)," "+ serialSize + " ",
                    getString(R.string.serialItemsAre))

                showConfirmSheetDialog(
                    getString(R.string.confirm), sb,
                    model.shippingAddressDetailID, dialog, model.quantity,false)
            }else toast(getString(R.string.youHaveNoSerial),requireActivity())


        }

        dialogBinding.layoutTopInfo.serialEdi.requestFocus()

        dialogBinding.closeImg.setOnClickListener { dialog.dismiss() }
        dialogBinding.rel4.cansel.setOnClickListener { dialog.dismiss() }

        dialog.setOnDismissListener { refresh() }
    }



    private fun addSerialByBoth(
        dialogBinding: DialogSerialScanBinding,
        model: ShippingDetailRow
    ) {
        if (lenEdi(dialogBinding.layoutTopInfo.serialEdi) != 0 )
        {

            if (quantitySerial < model.quantity)
            {
                    if ( checkIfIsValidChars(
                            textEdi(dialogBinding.layoutTopInfo.serialEdi),
                            pref.getUnValidChars(),pref.getSerialLenMax(),
                            pref.getSerialLenMin(),requireActivity()) == true)
                    {
                        addSerial(
                            model.shippingAddressDetailID,
                            dialogBinding.layoutTopInfo.serialEdi,
                            model.productID, pref.getTokenGlcTest(),
                            dialogBinding)
                    }

            } else {
                toast(
                    getString(R.string.youCanAdd)
                            + model.quantity + getString(R.string.serials), requireActivity()
                )
            }
        }
    }


    private fun setShippingDetail() {
        viewModel.setShippingList(
            pref.getDomain(),shippingId,
            textEdi(b.mainToolbar.searchEdi),
            customerName = textEdi(b.mainToolbar2.searchEdi),
           receivePage,Utils.ROWS,sortType,receiveOrder,
            pref.getTokenGlcTest(),b.progressBar,b.swipeLayout)
    }



    private fun addSerial(
        receivingDetailId: String,
          serialEdi: EditText,
          productId: String, token:String,
          dialogBinding: DialogSerialScanBinding
    )
    {
        if (lenEdi(serialEdi)!=0 )
        {
            viewModel.addSerial2(
                pref.getDomain(),receivingDetailId,textEdi(serialEdi)
                ,productId,dialogBinding.progressAll ,token,
                onReceiveError = {
                    serialEdi.setText("")
                })
            {

                serialEdi.setText("")
                if(it.isSucceed==true)
                {
                    serialEdi.requestFocus()
                    viewModel.setShippingSerials(
                        pref.getDomain(),receivingDetailId,
                        pref.getTokenGlcTest())

                }
            }

        }

    }

    private fun observeSerialList(dialogBinding: DialogSerialScanBinding,serialEdi:EditText)
    {
        viewModel.getShippingSerials().observe(viewLifecycleOwner,
            object : Observer<List<ShippingSerialModel>>
            {
                override fun onChanged(it: List<ShippingSerialModel>)
                {
                    quantitySerial=it.size
                    showSerialsSize(it, dialogBinding.serialsCount)
                    showSerialList(dialogBinding, it)
                }
            })
    }
    private fun showSerialsSize(serialList: List<ShippingSerialModel>, serialsCount:TextView)
    {
        val sb = StringBuilder()
        sb.append(getString(R.string.tools_scannedItems))
        sb.append(serialList.size)
        serialsCount.setText(sb.toString())
    }


    private fun showSerialList(dialogBinding: DialogSerialScanBinding,
        list: List<ShippingSerialModel>)
    {
        serialSize=list.size
        val adapter= ShippingSerialAdapter(
            list, requireActivity(),
            object : ShippingSerialAdapter.OnCallBackListener
            {
                override fun onDelete(model: ShippingSerialModel)
                {
                    val sb= getBuiltString(
                        getString(R.string.are_you_sure_for_delete),
                        model.serialNumber,
                        getString(R.string.are_you_sure_for_delete2)
                    )

                    showDeleteSheetDialog(getString(R.string.serial_scan),sb)
                    { mySheetAlertDialog->
                        viewModel.removeShippingSerial(
                            pref.getDomain(),model.shippingAddressDetailID,model.serialNumber
                            ,pref.getTokenGlcTest(),dialogBinding.progress)
                        observeRemoveSerial(model,mySheetAlertDialog)
                    }



                }

                override fun imgVisible(img: ImageView) {
                    img.visibility = View.INVISIBLE
                }
            })
        dialogBinding.rv.adapter=adapter
        dialogBinding.searchEdi.doAfterTextChanged {
            adapter.setFilter(search(textEdi(dialogBinding.searchEdi),list,SearchFields.SerialNumber))
        }

    }

    private fun showDeleteSheetDialog(title: String, desc: String, func:(mySheetAlertDialog:SheetAlertDialog)->Unit)
    {

        var mySheetAlertDialog:SheetAlertDialog ?=null
        mySheetAlertDialog= SheetAlertDialog(title,desc
            ,object :SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {
                    func(mySheetAlertDialog!!)
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }
                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {

                }

            })
        mySheetAlertDialog.show(this.getParentFragmentManager(), "")

    }

    private fun observeRemoveSerial(
        model: ShippingSerialModel,
        mySheetAlertDialog: SheetAlertDialog
    ) {
        mySheetAlertDialog.dismiss()
        viewModel.getRemovingSerialResult().observe(viewLifecycleOwner,
            object : Observer<RemoveShippingSerialModel> {
                override fun onChanged(t: RemoveShippingSerialModel)
                {
                    dispose()
                    viewModel.setShippingSerials(
                        pref.getDomain(),model.shippingAddressDetailID,
                        pref.getTokenGlcTest())
                }
            })
    }

    private fun initSheepingDialog(
        dialogBinding: DialogSerialScanBinding,
        model: ShippingDetailRow
    ) {
        dialogBinding.layoutTopInfo.relMoreInfo.visibility = View.VISIBLE
        dialogBinding.layoutTopInfo.productTitle.text = model.productTitle
        dialogBinding.layoutTopInfo.productCode.text = model.productCode
        dialogBinding.layoutTopInfo.quantity.text = model.quantity.toString()
        dialogBinding.layoutTopInfo.customerFullName.text = model.customerFullName
        dialogBinding.layoutTopInfo.serialEdi.inputType=InputType.TYPE_CLASS_TEXT
        dialogBinding.layoutTopInfo.ownerCode.text=model.ownerCode
    }



    private fun showConfirmSheetDialog(
        title: String, desc: String,
        shippingAddressDetailID: String, dialog: AlertDialog?, quantity: Int,visibility:Boolean)
    {
        var mySheetAlertDialog:SheetConfirmDialog ?=null
        mySheetAlertDialog= SheetConfirmDialog(title,desc
            ,object :SheetConfirmDialog.OnClickListener{
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, expectedCount: String)
                {
                    if (visibility==false)
                    {
                        viewModel.setLoadingFinish(
                            pref.getDomain(),shippingAddressDetailID,pref.getTokenGlcTest(), progress,)
                        {
                            dialog?.dismiss()
                            mySheetAlertDialog?.dismiss()
                        }
                    }else
                    {
                        if (expectedCount.length!=0 && expectedCount.toInt() ==quantity)
                        {
                            viewModel.setLoadingFinish(
                                pref.getDomain(),shippingAddressDetailID,pref.getTokenGlcTest(), progress)
                            {
                                dialog?.dismiss()
                                mySheetAlertDialog?.dismiss()
                            }

                        }else{
                            toast(getString(R.string.isNoteEqualQuantity),requireActivity())
                        }
                    }



                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {
                    refresh()
                }

                override fun init(binding: DialogSheetBottomBinding)
                {
                    if(visibility==true)
                        binding.rel2.visibility=View.VISIBLE
                    else binding.rel2.visibility=View.GONE
                }

            })
        mySheetAlertDialog.show(this.getParentFragmentManager(), "")
    }




    private fun dispose() {
        viewModel.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (view!=null){
            viewModel.getShippingDetail().removeObservers(viewLifecycleOwner)
            viewModel.getLoadingFinish().removeObservers(viewLifecycleOwner)
            viewModel.getRemovingSerialResult().removeObservers(viewLifecycleOwner)
            viewModel.getAddSerialModel().removeObservers(viewLifecycleOwner)
            viewModel.getShippingDetail().removeObservers(viewLifecycleOwner)
            dispose()
        }
    }

    override fun init()
    {
        b.receiveItem.title2?.text=getString(R.string.bolNumber)
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
        activity?.findViewById<TextView>(R.id.title)?.setText(getString(R.string.shippingDetail))

        b.receiveItem.driverFullName.text=arguments?.getString(Utils.DRIVE_FULLNAME)
        b.receiveItem.recevieNumber.text=arguments?.getString(Utils.ShippingNumber)
        b.receiveItem.containerNumber.text=arguments?.getString(Utils.BOLNumber)

        val plaque1=arguments?.getString(Utils.PLAQUE_1)
        val plaque2=arguments?.getString(Utils.PLAQUE_2)
        val plaque3=arguments?.getString(Utils.PLAQUE_3)
        val plaque4=arguments?.getString(Utils.PLAQUE_4)
        b.receiveItem.plaque.setText(getBuiltString(plaque3.toString(),plaque2.toString(),plaque1.toString()))
        b.receiveItem.plaqueYear.text=plaque4

       b.receiveItem.title1?.text=getString(R.string.shippingNumber2)
       b.mainToolbar2.searchEdi.setHint(getString(R.string.customers))
        b.mainToolbar2.rel2.visibility=View.VISIBLE


    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }


    override fun getLayout(): Int {
        return R.layout.fragment_detail_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}