package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSerialScanBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentSerialPutawayDetailBinding
import com.example.warehousemanagment.model.classes.checkEnterKey
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.createAlertDialog
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.search
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.putaway.serial_putaway.ReceiptDetailLocationRow
import com.example.warehousemanagment.model.models.receive.receiving_detail_serials.ReceivingDetailSerialModel
import com.example.warehousemanagment.ui.adapter.ReceiptLocationAdapter
import com.example.warehousemanagment.ui.adapter.SerialAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.SerialPutawayDetailLocationViewModel

class SerialPutawayDetailLocationFragment
    : BaseFragment<SerialPutawayDetailLocationViewModel,FragmentSerialPutawayDetailBinding>(){
    lateinit var receiptDetailId: String
    var chronometer: CountDownTimer? = null
    var scanDialog: AlertDialog? = null
    var serialAdapter: SerialAdapter? = null
    var scanDialogBinding: DialogSerialScanBinding? = null

    var sortType = Utils.ProductTitle
    var page = Utils.PAGE_START
    var order = Utils.ASC_ORDER
    var lastReceivingPosition = 0

    lateinit var productCode: String
    lateinit var productTitle: String
    lateinit var invType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiptDetailId = arguments?.getString(Utils.RECEIVING_ID, "").toString()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLocationList()
        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }
        observeLocationData()
        observeReceiveCount()
        clearEdi(b.mainToolbar.clearImg, b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener()
        {
            showFilterSheetDialog()
        }


    }
    private fun showFilterSheetDialog() {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener {
            override fun initView(binding: DialogSheetSortFilterBinding) {
                binding.relLocationCode.visibility = View.GONE
                binding.ownerCode.text = getString(R.string.createdOn)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding) {
                when (sortType) {
                    Utils.ProductCode -> checkTick(binding.productCodeImg, binding)
                    Utils.ProductTitle -> checkTick(binding.productTitleImg, binding)
                    Utils.CREATED_ON -> checkTick(binding.ownerCOdeImg, binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView) {
                if (order == Utils.ASC_ORDER) {
                    asc.backgroundTintList =
                        ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
                    desc.backgroundTintList = null
                } else {
                    desc.backgroundTintList =
                        ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
                    asc.backgroundTintList = null
                }
            }


            override fun onCloseClick() {
                sheet?.dismiss()
            }

            override fun onAscClick() {
                if (order != Utils.ASC_ORDER) {
                    order = Utils.ASC_ORDER
                    refresh()
                }
            }

            override fun onDescClick() {
                if (order != Utils.DESC_ORDER) {
                    order = Utils.DESC_ORDER
                    refresh()
                }

            }

            override fun onLocationCodeClick() {}

            override fun onProductCodeClick() {
                if (sortType != Utils.ProductCode) {
                    sortType = Utils.ProductCode
                    refresh()
                }

            }

            override fun onProductTitleClick() {
                if (sortType != Utils.ProductTitle) {
                    sortType = Utils.ProductTitle
                    refresh()
                }

            }

            override fun onOwnerClick() {
                if (sortType != Utils.CREATED_ON) {
                    sortType = Utils.CREATED_ON
                    refresh()
                }

            }

            override fun onRel5Click() {
            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun showScanDialog(
        receiptDetailId: String,
//        itemLocationId: String,
        locationCode: String,
        productTitle: String,
        productCode: String,
        progress: ProgressBar,
        invtypeTitle: String
    ) {
        scanDialogBinding = DialogSerialScanBinding
            .inflate(LayoutInflater.from(requireActivity()))
        scanDialog?.dismiss()
        scanDialog = createAlertDialog(
            scanDialogBinding!!,
            R.drawable.shape_background_rect_border_gray_solid_white, requireActivity()
        )


        scanDialogBinding!!.layoutTopInfo.relQuantity.visibility = View.VISIBLE
        scanDialogBinding!!.layoutTopInfo.relOwnerCode.visibility = View.GONE
        scanDialogBinding!!.layoutTopInfo.serialEdi.hint = "Location Code"
        scanDialogBinding!!.layoutTopInfo.serialEdi.setText(locationCode)
        scanDialogBinding!!.layoutTopInfo.quantityEdi.hint = "Input Serial"
        scanDialogBinding!!.layoutTopInfo.productTitle.text = productTitle
        scanDialogBinding!!.layoutTopInfo.productCode.text = productCode
        scanDialogBinding!!.layoutTopInfo.invType.text = invtypeTitle
        viewModel.dispose()

        viewModel.setSerialList(
            pref.getDomain(),
            receiptDetailId,
            locationCode,
            pref.getTokenGlcTest(),
            requireActivity(), progress
        )
        observeSerialList(scanDialogBinding!!,locationCode)
        observeSerialCount(scanDialogBinding!!.serialsCount)

//        observeSerialCountActiveStatus(scanDialogBinding!!.layoutTopInfo.quantityEdi)




        clearEdi(
            scanDialogBinding!!.layoutTopInfo.clearImg,
            scanDialogBinding!!.layoutTopInfo.serialEdi
        )
        clearEdi(scanDialogBinding!!.clearImg, scanDialogBinding!!.searchEdi)
        clearEdi(
            scanDialogBinding!!.layoutTopInfo.quantityclearImg,
            scanDialogBinding!!.layoutTopInfo.quantityEdi
        )


        if (locationCode.isEmpty())
            scanDialogBinding!!.layoutTopInfo.serialEdi.requestFocus()
        else
            scanDialogBinding!!.layoutTopInfo.quantityEdi.requestFocus()

        checkEnterKey(scanDialogBinding!!.layoutTopInfo.serialEdi){
            scanDialogBinding!!.layoutTopInfo.serialEdi.isEnabled = false
            scanDialogBinding!!.layoutTopInfo.quantityEdi.requestFocus()

        }

        checkEnterKey(scanDialogBinding!!.layoutTopInfo.quantityEdi)
        {
            if (lenEdi(scanDialogBinding!!.layoutTopInfo.serialEdi) != 0 && lenEdi(scanDialogBinding!!.layoutTopInfo.quantityEdi) != 0){
                viewModel.scanSerial(
                    baseUrl = pref.getDomain(),
                    textEdi(scanDialogBinding!!.layoutTopInfo.serialEdi),
                    textEdi(scanDialogBinding!!.layoutTopInfo.quantityEdi),
                    receiptDetailId,
                    pref.getTokenGlcTest(),
                    requireContext(),
                    scanDialogBinding!!.progress,
                    {
                        scanDialogBinding!!.layoutTopInfo.quantityEdi.setText("")
                        viewModel.setSerialList(
                            pref.getDomain(),
                            receiptDetailId,
                            textEdi(scanDialogBinding!!.layoutTopInfo.serialEdi),
                            pref.getTokenGlcTest(),
                            requireActivity(),
                            progress
                        )
                    }
                )
            } else {
                toast("Please fill all inputs",requireContext())
            }
        }
//
        scanDialogBinding!!.layoutTopInfo.add.setOnClickListener()
        {
            if (lenEdi(scanDialogBinding!!.layoutTopInfo.serialEdi) != 0 && lenEdi(scanDialogBinding!!.layoutTopInfo.quantityEdi) != 0){
                viewModel.scanSerial(
                    baseUrl = pref.getDomain(),
                    textEdi(scanDialogBinding!!.layoutTopInfo.serialEdi),
                    textEdi(scanDialogBinding!!.layoutTopInfo.quantityEdi),
                    receiptDetailId,
                    pref.getTokenGlcTest(),
                    requireContext(),
                    scanDialogBinding!!.progress,
                    {
                        scanDialogBinding!!.layoutTopInfo.quantityEdi.setText("")
                        viewModel.setSerialList(
                            pref.getDomain(),
                            receiptDetailId,
                            textEdi(scanDialogBinding!!.layoutTopInfo.serialEdi),
                            pref.getTokenGlcTest(),
                            requireActivity(),
                            progress
                        )
                    }
                )
            } else {
                toast("Please fill all inputs",requireContext())
            }
        }

//        scanDialogBinding!!.layoutTopInfo.iconBarcode.setOnClickListener()
//        {
//            scanBarcodeWithPhone(scanDialogBinding!!.scannerView)
//        }
//

        scanDialogBinding!!.rel4.cansel.setOnClickListener { scanDialog?.dismiss() }
        scanDialogBinding!!.rel4.root.visibility = View.GONE
        scanDialogBinding!!.closeImg.setOnClickListener { scanDialog?.dismiss() }
        scanDialogBinding!!.rel4.confirm.setOnClickListener {
            scanDialog?.dismiss()
        }
        scanDialog!!.setOnDismissListener()
        {
            scanDialogBinding?.scannerView?.stopCamera()
            scanDialogBinding?.scannerView?.visibility = View.GONE
        }




    }

    private fun showSerialAdapter(
        rv: RecyclerView,
        serialList: List<ReceivingDetailSerialModel>,
        locationCode: String,
        searchEdi: EditText,
        dialogBinding: DialogSerialScanBinding
    ) {
        serialAdapter = SerialAdapter(serialList, requireActivity(),
            object : SerialAdapter.OnCallBackListener {
                override fun onDelete(model: ReceivingDetailSerialModel) {
                    val sb = StringBuilder()
                    sb.append(getString(R.string.are_you_sure_for_delete))
                    sb.append(model.serialNumber)
                    sb.append(getString(R.string.are_you_sure_for_delete2))

                    showDeleteSheetDialog(
                        getString(R.string.serial_scan_confirm),
                        sb.toString(),
                        model.itemSerialID,
                        locationCode,
                        dialogBinding
                    )
                }

                override fun imgVisible(img: ImageView) {

                }
            })
        rv.adapter = serialAdapter

        searchEdi.doAfterTextChanged {
            serialAdapter?.setFilter(
                search(
                    textEdi(searchEdi),
                    serialList,
                    SearchFields.SerialNumber
                )
            )
            log("serialSearch", serialList.size.toString())
        }


    }

    private fun showDeleteSheetDialog(
        title: String,
        desc: String,
        itemSerialID: String,
        itemLocationId: String,dialogBinding: DialogSerialScanBinding
    ) {
        var mySheetAlertDialog: SheetAlertDialog? = null
        mySheetAlertDialog =
            SheetAlertDialog(title, desc, object : SheetAlertDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    removeSerial(
                        itemSerialID,
                        itemLocationId,
                        progress,
                        mySheetAlertDialog, dialogBinding
                    )
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

    private fun removeSerial(
        itemSerialID: String,
        locationCode: String,
        progress: ProgressBar,
        mySheetAlertDialog: SheetAlertDialog?,
        dialogBinding: DialogSerialScanBinding,
    ) {


        viewModel.removeSerial(
            pref.getDomain(),
            itemSerialID,
            pref.getTokenGlcTest(),
            requireContext(),
            progress
        )
        viewModel.getRemoveSerialModel()
            .observe(viewLifecycleOwner){
                mySheetAlertDialog?.dismiss()

                viewModel.dispose()
                viewModel.setSerialList(
                    pref.getDomain(),
                    receiptDetailId,
                    locationCode,
                    pref.getTokenGlcTest(),
                    requireContext(),
                    dialogBinding.progress
                )
                observeSerialList(dialogBinding,locationCode)

            }
    }

    private fun observeSerialList(
        dialogBinding: DialogSerialScanBinding,
        locationCode: String,
    ){
        viewModel.getSerialsList().observe(viewLifecycleOwner
        ) { serialList ->
            showSerialAdapter(
                dialogBinding.rv,
                serialList.map { ReceivingDetailSerialModel(false,it.itemSerialID,receiptDetailId,it.serial) },
                locationCode,
                dialogBinding.searchEdi,
                dialogBinding
            )
            showSerialsSize(
                serialList.map { ReceivingDetailSerialModel(false,it.itemSerialID,receiptDetailId,it.serial) },
                dialogBinding.serialsCount
            )

            dialogBinding.rel4.confirm.setOnClickListener()
            {
//                if (serialList.isNotEmpty())
//                    onConfirmSeiralsClick(
//                        receivingDetailId, workerTaskId, serialList.size, dialogBinding
//                    )
//                else toast(getString(R.string.thereIsNoSerial), requireActivity())
            }
        }
    }

    private fun showSerialsSize(
        serialList: List<ReceivingDetailSerialModel>,
        serialsCount: TextView
    ) {
        val sb = StringBuilder()
        sb.append(getString(R.string.tools_scannedItems))
        sb.append(serialList.size)
        serialsCount.text = sb.toString()
    }

    private fun observeSerialCount(
        serialsCount: TextView
    ) {
        viewModel.getSerialCount().observe(this){

            val sb = StringBuilder()
            sb.append(getString(R.string.tools_scannedItems))
            sb.append(it)
            serialsCount.text = sb.toString()
        }
    }




    private fun refresh() {
        viewModel.dispose()
        page = Utils.PAGE_START
        viewModel.clearLocations()
        setLocationList()
    }

    private fun setLocationList(){
        viewModel.setLocations(
            pref.getDomain(),
            receiptDetailId,
            pref.getTokenGlcTest(),
            textEdi(b.mainToolbar.searchEdi),
            page,
            10,
            sortType,
            order,
            context!!,
            b.progressBar,
            b.swipeLayout
        )
    }

    private fun observeReceiveCount() {
        viewModel.getDetailCount().observe(viewLifecycleOwner
        ) { it ->
            setBelowCount(
                requireActivity(), getString(R.string.tools_you_have),
                it, getString(R.string.productsToReceive)
            )
        }
    }

    private fun startTimerForReceiveingData() {
        chronometer?.cancel()
        chronometer = object : CountDownTimer(Utils.DELAY_SERIAL, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                refresh()
            }
        }.start()
    }

    private fun observeLocationData() {
        viewModel.getLocations()
            .observe(this){
                if (view != null && isAdded) {
                    b.swipeLayout.isRefreshing = false
                    lastReceivingPosition = it.size - 1
                    showLocationList(it)

                }
            }
    }

    private fun showLocationList(list: List<ReceiptDetailLocationRow>) {
        if (lastReceivingPosition - Utils.ROWS <= 0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastReceivingPosition - Utils.ROWS)

        val adapter = ReceiptLocationAdapter(
            list,
            context!!,
            onItemClick = {
                showScanDialog(
                    receiptDetailId,
                    it.locationCode,
                    productTitle = productTitle,
                    productCode = productCode,
                    invtypeTitle = invType,
                    progress = b.progressBar
                )
            },
            onReachToEnd = {
                page += 1
                setLocationList()
            }
        )

        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForReceiveingData()
        }

    }


    override fun init() {
        setToolbarTitle(requireActivity(), "Serial Putaway Detail")

        setToolbarBackground(b.mainToolbar.rel2, requireActivity())

        b.header.layDetail.visibility = View.VISIBLE

        val receiveNumber = arguments?.getString(Utils.RECEIVE_NUMBER)
        val driveFullName = arguments?.getString(Utils.DRIVE_FULLNAME)
        productCode = arguments?.getString(Utils.ProductCode)?:""
        productTitle = arguments?.getString(Utils.ProductTitle)?:""
        invType = arguments?.getString(Utils.locationInventory)?:""
        val quantity = arguments?.getInt(Utils.Quantity)
        val scan = arguments?.getInt("scan")
        val containerNumber = arguments?.getString(Utils.CONTAINER_NUMBER)


        val plaque1 = arguments?.getString(Utils.PLAQUE_1)
        val plaque2 = arguments?.getString(Utils.PLAQUE_2)
        val plaque3 = arguments?.getString(Utils.PLAQUE_3)
        val plaque4 = arguments?.getString(Utils.PLAQUE_4)

        b.layPutSerial.visibility = View.VISIBLE
        b.putSerial.tv.text = "Put Serial into Location"

        b.putSerial.tv.setOnClickListener {
            showScanDialog(
                receiptDetailId,
                "",
                productTitle,
                productCode,
                b.progressBar,
                invType
            )
        }


        b.header.recevieNumber.text = receiveNumber
        b.header.containerNumber.text = containerNumber
        b.header.driverFullName.text = driveFullName
        b.header.receiptDetialNumber.text = productTitle
        b.header.product.text = productCode

        b.header.plaque.text = getBuiltString(
            plaque3.toString(),
            plaque2.toString(),
            plaque1.toString()
        )
        b.header.plaqueYear.text = plaque4
        b.header.total.text = quantity.toString()
        b.header.scan.text = scan.toString()

    }

    override fun getLayout(): Int {
        return R.layout.fragment_serial_putaway_detail
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }


}