package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSerialScanBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentDetailReceivingBinding
import com.example.warehousemanagment.databinding.PatternShippingDetailBinding
import com.example.warehousemanagment.model.classes.checkEnterKey
import com.example.warehousemanagment.model.classes.checkIfIsValidChars
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.createAlertDialog
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.search
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.shipping.RemoveShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.ShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailRow
import com.example.warehousemanagment.ui.adapter.ShipingDetailAdapter
import com.example.warehousemanagment.ui.adapter.ShippingSerialAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.CanselShippingDetailViewModel


class CanselShippingDetailFragment :
    BaseFragment<CanselShippingDetailViewModel, FragmentDetailReceivingBinding>()
{
    lateinit var shippingId:String
    var quantitySerial:Int=0
    var serialSize:Int=0

    var sortType=Utils.ProductTitle
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastPosition=0
    var customerId:String = ""



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        shippingId= arguments?.getString(Utils.ShippingId).toString()
        setShippingDetail()

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }

        observeShippingDetail()
        observeShippingCount()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener {showFilterSheetDialog()}

        onChangeCustomerName()

    }

    private fun onChangeCustomerName()
    {
        b.mainToolbar2.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData()
            {
                refresh()
//                if (lenEdi(b.mainToolbar2.searchEdi) != 0) {
//                    viewModel.setCustomers(
//                        pref.getDomain(),
//                        pref.getTokenGlcTest(),
//                        textEdi(b.mainToolbar2.searchEdi),
//                        b.progressBar
//                    ) {
//                        viewModel.dispose()
//                        getCustomers(b.mainToolbar2.searchEdi, getString(R.string.customers))
//                    }
//                    hideKeyboard(requireActivity())
//
//
//                } else customerId = ""
            }
        }
    }

//    private fun getCustomers(tv: TextView, title: String)
//    {
//        var productSheet: SheetPalletDialog?=null
//        productSheet = SheetPalletDialog(getString(R.string.warehouse),
//            object : SheetPalletDialog.OnClickListener
//            {
//                override fun onCloseClick() { productSheet?.dismiss() }
//
//                override fun initData(binding: DialogSheetDestinyLocationBinding)
//                {
//                    binding.title.text=title
//                    clearEdi(binding.clearImg,binding.searchEdi)
//                    observeCustomers(productSheet, tv, binding.rv,binding.serialsCount,binding)
//                }
//            })
//        productSheet.show(this.getParentFragmentManager(),"")
//    }

//    private fun observeCustomers(
//        sheet: SheetPalletDialog?,
//        tv: TextView,
//        rv: RecyclerView,
//        arrCounts: TextView,
//        binding: DialogSheetDestinyLocationBinding
//    )
//    {
//        viewModel.getCustomers().observe(viewLifecycleOwner,
//            object : Observer<List<CustomerModel>>
//            {
//                override fun onChanged(list: List<CustomerModel>)
//                {
//                    arrCounts.text= getBuiltString(getString(R.string.tools_scannedItems),
//                        " ",list.size.toString())
//                    val adapter = CustomerAdapter(list, requireActivity(),
//                        object : CustomerAdapter.OnCallBackListener
//                        {
//                            override fun onClick(model: CustomerModel)
//                            {
//                                sheet?.dismiss()
//                                customerId = model.customerID
//                                tv.text = model.customerFullName
//
//                                chronometer?.cancel()
//                                refresh()
//
//                            }
//
//                            override fun init(binding: PatternWarehouseBinding)
//                            {
//                                binding.tv1.text=getString(R.string.customerFullName)
//                                binding.tv2.text=getString(R.string.customerCode)
//
//                            }
//
//                        })
//                    rv.adapter = adapter
//
//
//                    clearEdi(binding.clearImg,binding.searchEdi)
//                    binding.searchEdi.doAfterTextChanged {
//                        adapter.setFilter(search(textEdi(binding.searchEdi),list,
//                            SearchFields.CustomerCode,
//                            SearchFields.CustomerFullName))
//                    }
//
//                }
//
//            })
//    }


    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.relLocationCode.visibility=View.INVISIBLE
                binding.ownerCode.text=getString(R.string.createdOn)
                binding.productCode.text=getString(R.string.customerFullName)
                binding.productTitle.text=getString(R.string.productTitle)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.CUSTOMER_FULL_NAME ->checkTick(binding.productCodeImg,binding)
                    Utils.ProductTitle ->checkTick(binding.productTitleImg,binding)
                    Utils.DockAssignTime ->checkTick(binding.ownerCOdeImg,binding)

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

            override fun onLocationCodeClick() {}

            override fun onProductCodeClick()
            {
                if (sortType!=Utils.CUSTOMER_FULL_NAME)
                {
                    sortType=Utils.CUSTOMER_FULL_NAME
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

    private fun observeShippingCount()
    {
        viewModel.getShippingDetailCount().observe(viewLifecycleOwner,
            object :Observer<ShippingDetailModel>
            {
                override fun onChanged(it: ShippingDetailModel)
                {
                    b.swipeLayout.isRefreshing=false

                    setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                        it.total, " "+getString(R.string.productsAnd)+" "+it.remain+ " "+getString(R.string.Items))

                }

            })

    }

    private fun refresh()
    {
        viewModel.dispose()
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
                    lastPosition=it.size-1
                    showShippingDetailList(it)
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
                    val dialogBinding = DialogSerialScanBinding.inflate(
                        LayoutInflater.from(requireActivity()), null)
                    val dialog = createAlertDialog(dialogBinding,
                        R.drawable.shape_background_rect_border_gray_solid_white, requireActivity())

                    initSheepingDialog(dialogBinding, model)
                    dialogBinding.layoutTopInfo.invType.text=model.invTypeTitle

                    disposeRequest()

                    viewModel.setShippingSerials(
                        pref.getDomain(),model.shippingAddressDetailID,pref.getTokenGlcTest())
                    observeSerialList(dialogBinding)

                    clearEdi(dialogBinding.layoutTopInfo.clearImg,
                            dialogBinding.layoutTopInfo.serialEdi)
                    clearEdi(dialogBinding.clearImg,dialogBinding.searchEdi)

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
                        val sb=StringBuilder()
                        sb.append(getString(R.string.youScan))
                        sb.append(" "+serialSize+" ")
                        sb.append(getString(R.string.serialItemsAre))
                        showConfirmSheetDialog(getString(R.string.serial_scan_confirm), sb.toString(),
                            model.shippingAddressDetailID ,dialog)




                    }
                    dialogBinding.layoutTopInfo.serialEdi.requestFocus()

                    dialog.setOnDismissListener { refresh() }

                    dialogBinding.closeImg.setOnClickListener { dialog.dismiss() }
                    dialogBinding.rel4.cansel.setOnClickListener { dialog.dismiss() }
                }

                override fun reachToEnd(position: Int)
                {
                    receivePage=receivePage+1
                    setShippingDetail()
                }

                override fun onCloseClick(model: ShippingDetailRow) {

                }

                override fun init(binding: PatternShippingDetailBinding) {
                    binding.relClose.visibility=View.GONE
                }
            })
        b.rv.adapter = adapter


        b.mainToolbar.searchEdi.doAfterTextChanged {
            startTimerForGettingData {
                /**/
                refresh()
                /**/
            }
        }
//        adapter.setFilter(search(
//                textEdi(b.mainToolbar.searchEdi),list,
//                SearchFields.Quantity, SearchFields.ShippingQuantity,
//                SearchFields.CustomerFullName, SearchFields.ProductTitle)
//        )
//        b.mainToolbar.searchEdi.doAfterTextChanged()
//        {
//            adapter.setFilter(
//                search(
//                    textEdi(b.mainToolbar.searchEdi),list,
//                    SearchFields.Quantity, SearchFields.ShippingQuantity,
//                    SearchFields.CustomerFullName, SearchFields.ProductTitle)
//            )
//        }

    }



    private fun addSerialByBoth(
        dialogBinding: DialogSerialScanBinding,
        model: ShippingDetailRow
    ) {
        if (lenEdi(dialogBinding.layoutTopInfo.serialEdi) != 0 )
        {
            if (quantitySerial < model.quantity)
            {
                    if (checkIfIsValidChars(
                            textEdi(dialogBinding.layoutTopInfo.serialEdi),
                            pref.getUnValidChars(),pref.getSerialLenMax(),
                            pref.getSerialLenMin(),requireActivity()) == true)
                    {
                        addSerial(
                            model.shippingAddressDetailID,
                            dialogBinding.layoutTopInfo.serialEdi, pref.getTokenGlcTest(),
                            dialogBinding.progressAll)
                    }


            } else toast(
                getString(R.string.youCanAdd)
                        + model.quantity + getString(R.string.serials), requireActivity()
            )
        }
    }



    private fun setShippingDetail()
    {
        viewModel.setShippingList(pref.getDomain(),shippingId, textEdi(b.mainToolbar.searchEdi),
            customerName = textEdi(b.mainToolbar2.searchEdi),
            receivePage,Utils.ROWS,sortType,receiveOrder,pref.getTokenGlcTest(),b.progressBar,
        b.swipeLayout)
    }


    private fun addSerial( shippingAddressDetailID: String,
                          serialEdi: EditText, token:String,
                          progress: ProgressBar)
    {
        if (lenEdi(serialEdi)!=0 )
        {
            viewModel.addSerial(pref.getDomain(),shippingAddressDetailID, textEdi(serialEdi),progress ,token
            , onReceiveError = {
                serialEdi.setText("")
            })
            {

                serialEdi.setText("")
                serialEdi.requestFocus()

                viewModel.setShippingSerials(pref.getDomain(),shippingAddressDetailID,pref.getTokenGlcTest())
            }

        }

    }

    private fun observeSerialList(dialogBinding: DialogSerialScanBinding)
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
                    {
                        viewModel.removeShippingSerial(pref.getDomain(),model.shippingAddressDetailID,model.serialNumber
                            ,pref.getTokenGlcTest(),dialogBinding.progress)
                        observeRemoveSerial(model, dialogBinding,it)
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
        dialogBinding: DialogSerialScanBinding,
        sheetAlertDialog: SheetAlertDialog
    )
    {
        viewModel.getRemovingSerialResult().observe(viewLifecycleOwner,
            object : Observer<RemoveShippingSerialModel>
            {
                override fun onChanged(t: RemoveShippingSerialModel)
                {
                    disposeRequest()
                    sheetAlertDialog.dismiss()
                    viewModel.setShippingSerials(pref.getDomain(),model.shippingAddressDetailID, pref.getTokenGlcTest())
                    observeSerialList(dialogBinding)
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
        title: String,
        desc: String,
        shippingAddressDetailID: String,
        dialog: AlertDialog
    )
    {
        var mySheetAlertDialog:SheetAlertDialog ?=null
        mySheetAlertDialog= SheetAlertDialog(title,desc
            ,object :SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {
                    dialog.dismiss()

                    viewModel.setLoadingFinish( pref.getDomain(),
                        shippingAddressDetailID,pref.getTokenGlcTest(), progress)
                    {
                        mySheetAlertDialog?.dismiss()

                    }
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


    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
        if (view!=null){
            viewModel.getShippingDetail().removeObservers(viewLifecycleOwner)
            viewModel.getShippingSerials().removeObservers(viewLifecycleOwner)
            viewModel.getAddSerialModel().removeObservers(viewLifecycleOwner)
            viewModel.getRemovingSerialResult().removeObservers(viewLifecycleOwner)
        }
    }
    private fun disposeRequest() {
        viewModel.dispose()
    }
     
    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.cancelShippingDetail))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())

        b.receiveItem.driverFullName.text=arguments?.getString(Utils.DRIVE_FULLNAME)
        b.receiveItem.recevieNumber.text=arguments?.getString(Utils.ShippingNumber)
        b.receiveItem.containerNumber.text=arguments?.getString(Utils.BOLNumber)


        val plaque1=arguments?.getString(Utils.PLAQUE_1)
        val plaque2=arguments?.getString(Utils.PLAQUE_2)
        val plaque3=arguments?.getString(Utils.PLAQUE_3)
        val plaque4=arguments?.getString(Utils.PLAQUE_4)
        b.receiveItem.plaque.setText(getBuiltString(plaque3.toString(),
            plaque2.toString(),plaque1.toString()))
        b.receiveItem.plaqueYear.text=plaque4

        b.receiveItem.title2?.text=getString(R.string.bolNumber)
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