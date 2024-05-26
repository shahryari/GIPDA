package com.example.warehousemanagment.ui.fragment

import android.content.DialogInterface
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
import com.example.warehousemanagment.databinding.DialogCountBinding
import com.example.warehousemanagment.databinding.DialogSerialScanBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentDetailReceivingBinding
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.receive.confirm.ConfirmReceiveDetailModel
import com.example.warehousemanagment.model.models.receive.count.ReceiveDetailCountModel
import com.example.warehousemanagment.model.models.receive.receiveDetail.ReceiveDetailRow
import com.example.warehousemanagment.model.models.receive.receiving_detail_serials.ReceivingDetailSerialModel
import com.example.warehousemanagment.model.models.receive.remove_serial.RemoveSerialModel
import com.example.warehousemanagment.ui.adapter.ReceiveDetailAdapter
import com.example.warehousemanagment.ui.adapter.SerialAdapter
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.ReceivingDetailViewModel
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ReceivingDetailFragment() :
    BaseFragment<ReceivingDetailViewModel, FragmentDetailReceivingBinding>(),
    ZXingScannerView.ResultHandler {
    lateinit var receivingId: String
    var chronometer: CountDownTimer? = null
    var scanDialog: AlertDialog? = null
    var serialAdapter: SerialAdapter? = null
    var scanDialogBinding: DialogSerialScanBinding? = null

    var sortType = Utils.ProductTitle
    var receivePage = Utils.PAGE_START
    var receiveOrder = Utils.ASC_ORDER
    var lastReceivingPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receivingId = arguments?.getString(Utils.RECEIVING_ID, "").toString()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setReciveDetailList()
        b.swipeLayout.setOnRefreshListener()
        {
            refreshReceiveDetail()
        }
        obserReceiveDetailData()
        observeReceiveCount()
        clearEdi(b.mainToolbar.clearImg, b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener()
        {
            showFilterSheetDialog()
        }


    }

    private fun startTimerForReceiveingData() {
        chronometer?.cancel()
        chronometer = object : CountDownTimer(Utils.DELAY_SERIAL, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                refreshReceiveDetail()
            }
        }.start()
    }

    private fun refreshReceiveDetail() {
        disposeRequest()
        receivePage = Utils.PAGE_START
        viewModel.clearReceiveDetail()
        setReciveDetailList()
    }

    private fun setReciveDetailList() {
        viewModel.setReceiveDetailsList(
            pref.getDomain(),
            receivingId, pref.getTokenGlcTest(), textEdi(b.mainToolbar.searchEdi),
            receivePage, Utils.ROWS, sortType, receiveOrder, b.progressBar,
            b.swipeLayout
        )
    }

    private fun observeReceiveCount() {
        viewModel.getDetailCount().observe(viewLifecycleOwner, object : Observer<Int> {
            override fun onChanged(it: Int) {
                setBelowCount(
                    requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.productsToReceive)
                )

            }
        })
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
                if (receiveOrder == Utils.ASC_ORDER) {
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
                if (receiveOrder != Utils.ASC_ORDER) {
                    receiveOrder = Utils.ASC_ORDER
                    refreshReceiveDetail()
                }
            }

            override fun onDescClick() {
                if (receiveOrder != Utils.DESC_ORDER) {
                    receiveOrder = Utils.DESC_ORDER
                    refreshReceiveDetail()
                }

            }

            override fun onLocationCodeClick() {}

            override fun onProductCodeClick() {
                if (sortType != Utils.ProductCode) {
                    sortType = Utils.ProductCode
                    refreshReceiveDetail()
                }

            }

            override fun onProductTitleClick() {
                if (sortType != Utils.ProductTitle) {
                    sortType = Utils.ProductTitle
                    refreshReceiveDetail()
                }

            }

            override fun onOwnerClick() {
                if (sortType != Utils.CREATED_ON) {
                    sortType = Utils.CREATED_ON
                    refreshReceiveDetail()
                }

            }

            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }


    fun scanBarcodeWithPhone(scannerView: ZXingScannerView) {
        scannerView.visibility = View.VISIBLE
        initRequestPermission(requireActivity(), Utils.QSCANNER_PERMISSIONS, Utils.REQUEST_CAMERA)
        if (hasPermissions(requireActivity(), *Utils.QSCANNER_PERMISSIONS) == true) {
            scannerView.setResultHandler(this)
            scannerView.startCamera()
        } else initRequestPermission(
            requireActivity(),
            Utils.QSCANNER_PERMISSIONS,
            Utils.REQUEST_CAMERA
        )

    }

    override fun handleResult(result: Result) {
        val rawResult = result.text
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.scanResults))
        builder.setPositiveButton(getString(R.string.ok), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                scanDialogBinding?.scannerView?.resumeCameraPreview(this@ReceivingDetailFragment)
                log("rawResult", rawResult)
                scanDialogBinding?.scannerView?.stopCamera()
                scanDialogBinding?.scannerView?.visibility = View.GONE

                scanDialogBinding?.layoutTopInfo?.serialEdi?.setText(rawResult)
            }
        })
        builder.setNegativeButton(getString(R.string.cancel),
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    scanDialogBinding?.scannerView?.stopCamera()
                    scanDialogBinding?.scannerView?.visibility = View.GONE
                }
            })

        builder.setMessage(result.text)
        val dialog = builder.create()
        dialog.show()

    }


    private fun setDetailAdapter(serialList: List<ReceiveDetailRow>) {
        if (lastReceivingPosition - Utils.ROWS <= 0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastReceivingPosition - Utils.ROWS)

        val adapter = ReceiveDetailAdapter(serialList, requireActivity(),
            object : ReceiveDetailAdapter.OnCallBackListener {
                override fun onScanClick(
                    receivingDetailId: String,
                    productId: String,
                    progress: ProgressBar,
                    productTitle: String,
                    productCode: String,
                    workerTaskId: String,
                    ownerCode: String,
                    invtypeTitle: String
                ) {

                    showScanDialog(
                        receivingDetailId,
                        productId,
                        productTitle,
                        productCode,
                        progress,
                        workerTaskId,
                        ownerCode,
                        invtypeTitle
                    )
                }

                override fun onHandClick(
                    receivingDetailID: String, workerTaskID: String,
                    productTitle: String, productCode: String, ownerCode: String,
                    invtypeTitle: String,
                ) {
                    showCountDialog(
                        receivingDetailID,
                        workerTaskID,
                        productTitle,
                        productCode,
                        ownerCode,
                        invtypeTitle
                    )
                }

                override fun reachToEnd(position: Int) {
                    receivePage = receivePage + 1
                    viewModel.setReceiveDetailsList(
                        pref.getDomain(),
                        receivingId,
                        pref.getTokenGlcTest(),
                        textEdi(b.mainToolbar.searchEdi),
                        receivePage,
                        Utils.ROWS,
                        Utils.DESC_ORDER,
                        Utils.CREATED_ON,
                        b.progressBar,
                        b.swipeLayout
                    )
                }
            })
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForReceiveingData()
        }

//        adapter.setFilter(search(textEdi(b.mainToolbar.searchEdi),
//            serialList, SearchFields.ProductTitle))
//
//        b.mainToolbar.searchEdi.doAfterTextChanged {
//            adapter.setFilter(search(textEdi(b.mainToolbar.searchEdi)
//                ,serialList, SearchFields.ProductTitle))
//        }
    }

    private fun showCountDialog(
        receivingDetailID: String,
        workerTaskID: String,
        productTitle: String,
        productCode: String,
        ownerCode: String,
        invtypeTitle: String
    ) {
        val dialogBinding = DialogCountBinding.inflate(
            LayoutInflater.from(requireActivity()), null
        )
        val countDialog = createAlertDialog(
            dialogBinding,
            R.drawable.shape_background_rect_border_gray_solid_white, requireActivity()
        )


        initCountDialog(dialogBinding, productTitle, productCode, invtypeTitle)
        dialogBinding.layoutTopInfo.ownerCode.setText(ownerCode)

        clearEdi(dialogBinding.layoutTopInfo.clearImg, dialogBinding.layoutTopInfo.serialEdi)

        dialogBinding.rel4.cansel.setOnClickListener { countDialog.dismiss() }
        dialogBinding.closeImg.setOnClickListener { countDialog.dismiss() }

        dialogBinding.rel4.confirm.setOnClickListener()
        {
            val count = textEdi(dialogBinding.layoutTopInfo.serialEdi)
            if (count.length != 0) {
                showConfrimHandDialog(
                    receivingDetailID, count, workerTaskID, dialogBinding, countDialog,
                    getString(R.string.count), getString(R.string.areYouSureCount)
                )

            } else toast(getString(R.string.fillCountEdi), requireActivity())
        }


    }

    private fun initCountDialog(
        dialogBinding: DialogCountBinding,
        productTitle: String,
        productCode: String,
        invtypeTitle: String
    ) {
        dialogBinding.header.text = getString(R.string.count)
        dialogBinding.layoutTopInfo.serialEdi.setHint(getString(R.string.count))
        dialogBinding.layoutTopInfo.invType.text = invtypeTitle
        /*----------------------------------------*/
        dialogBinding.layoutTopInfo.serialEdi.inputType = InputType.TYPE_CLASS_NUMBER
        /*this TYPE_CLASS_NUMBER is suspected*/
        dialogBinding.layoutTopInfo.addSerial.visibility = View.INVISIBLE
        dialogBinding.layoutTopInfo.productTitle.text = productTitle
        dialogBinding.layoutTopInfo.productCode.text = productCode
        dialogBinding.layoutTopInfo.title1.setText(getString(R.string.productTitle))
        dialogBinding.layoutTopInfo.add.visibility = View.GONE

    }

    private fun countReceiveDetail(
        receivingDetailID: String,
        count: String,
        workerTaskID: String,
        dialogBinding: DialogCountBinding,
        countDialog: AlertDialog,
        mySheetAlertDialog: SheetAlertDialog?
    ) {

        viewModel.countReceiveDetail(
            pref.getDomain(),
            receivingDetailID, count.toInt(), workerTaskID,
            pref.getTokenGlcTest(), dialogBinding.progress
        )
        viewModel.getReceiveDetailCount()
            .observe(viewLifecycleOwner, object : Observer<ReceiveDetailCountModel> {
                override fun onChanged(it: ReceiveDetailCountModel) {
                    countDialog.dismiss()
                    mySheetAlertDialog?.dismiss()
                    refreshReceiveDetail()

                }

            })
    }

    private fun showScanDialog(
        receivingDetailId: String, productId: String,
        productTitle: String, productCode: String, progress: ProgressBar, workerTaskId: String,
        ownerCode: String,
        invtypeTitle: String
    ) {
        scanDialogBinding = DialogSerialScanBinding
            .inflate(LayoutInflater.from(requireActivity()), null)
        scanDialog?.dismiss()
        scanDialog = createAlertDialog(
            scanDialogBinding!!,
            R.drawable.shape_background_rect_border_gray_solid_white, requireActivity()
        )


        scanDialogBinding!!.layoutTopInfo.relQuantity.visibility = View.VISIBLE
        scanDialogBinding!!.layoutTopInfo.quantityEdi.isEnabled = false
        disposeRequest()

        viewModel.setSerialList(
            pref.getDomain(), receivingDetailId, pref.getTokenGlcTest(), progress
        )
        observeSerialList(scanDialogBinding!!, productId, receivingDetailId, workerTaskId)

        observeSerialCountActiveStatus(scanDialogBinding!!.layoutTopInfo.quantityEdi)

        initSerialScanDialogInfo(scanDialogBinding!!, productTitle, productCode, invtypeTitle)
        scanDialogBinding!!.layoutTopInfo.ownerCode.setText(ownerCode)

        clearEdi(
            scanDialogBinding!!.layoutTopInfo.clearImg,
            scanDialogBinding!!.layoutTopInfo.serialEdi
        )
        clearEdi(scanDialogBinding!!.clearImg, scanDialogBinding!!.searchEdi)
        clearEdi(
            scanDialogBinding!!.layoutTopInfo.quantityclearImg,
            scanDialogBinding!!.layoutTopInfo.quantityEdi
        )



        checkEnterKey(scanDialogBinding!!.layoutTopInfo.serialEdi)
        {
            addSerialByBoth(receivingDetailId, productId, workerTaskId)
        }

        scanDialogBinding!!.layoutTopInfo.add.setOnClickListener()
        {
            addSerialByBoth(receivingDetailId, productId, workerTaskId)
        }

        scanDialogBinding!!.layoutTopInfo.iconBarcode.setOnClickListener()
        {
            scanBarcodeWithPhone(scanDialogBinding!!.scannerView)
        }


        scanDialogBinding!!.rel4.cansel.setOnClickListener { scanDialog?.dismiss() }
        scanDialogBinding!!.closeImg.setOnClickListener { scanDialog?.dismiss() }
        scanDialog!!.setOnDismissListener()
        {
            scanDialogBinding?.scannerView?.stopCamera()
            scanDialogBinding?.scannerView?.visibility = View.GONE
        }


        scanDialogBinding!!.layoutTopInfo.serialEdi.requestFocus()


    }


    private fun addSerialByBoth(
        receivingDetailId: String,
        productId: String,
        workerTaskId: String
    ) {
        if (lenEdi(scanDialogBinding!!.layoutTopInfo.serialEdi) != 0) {
            if (checkIfIsValidChars(
                    textEdi(scanDialogBinding!!.layoutTopInfo.serialEdi),
                    pref.getUnValidChars(), pref.getSerialLenMax(),
                    pref.getSerialLenMin(), requireActivity()
                ) == true
            ) {
                addSerial(
                    scanDialogBinding!!, receivingDetailId,
                    scanDialogBinding!!.layoutTopInfo.serialEdi,
                    productId, pref.getTokenGlcTest(),
                    scanDialogBinding!!.progressAll, workerTaskId
                )
            }

        }

    }


    private fun addSerial(
        dialogBinding: DialogSerialScanBinding, receivingDetailId: String,
        serialEdi: EditText,
        productId: String, token: String,
        progress: ProgressBar, workerTaskId: String
    ) {
        if (lenEdi(serialEdi) != 0) {

            viewModel.addReceivingSerial(
                pref.getDomain(), receivingDetailId, textEdi(serialEdi), productId, token, progress
            )
            {
                if (it.isSucceed == true) {
                    serialEdi.setText("")
                    viewModel.setSerialList(
                        pref.getDomain(), receivingDetailId, pref.getTokenGlcTest(), progress
                    )
                }

            }


        }

    }

    private fun disposeRequest() {
        viewModel.dispose()
    }

    private fun observeSerialCountActiveStatus(serialCountEdi: EditText) {
        viewModel.getScanCountStatus().observe(viewLifecycleOwner,
            object : Observer<Boolean> {
                override fun onChanged(status: Boolean) {
                    if (status == false) {
                        serialCountEdi.isEnabled = false
                    } else {
                        serialCountEdi.isEnabled = true
                    }
                }

            })
    }

    private fun observeSerialList(
        dialogBinding: DialogSerialScanBinding,
        productId: String, receivingDetailId: String, workerTaskId: String
    ) {
        viewModel.getSerialsList().observe(viewLifecycleOwner,
            object : Observer<List<ReceivingDetailSerialModel>> {
                override fun onChanged(serialList: List<ReceivingDetailSerialModel>) {
                    showSerialAdapter(
                        dialogBinding.rv, serialList, dialogBinding.searchEdi,
                        productId, dialogBinding, workerTaskId
                    )
                    showSerialsSize(serialList, dialogBinding.serialsCount)

                    dialogBinding.rel4.confirm.setOnClickListener()
                    {
                        if (serialList.size != 0)
                            onConfirmSeiralsClick(
                                receivingDetailId, workerTaskId, serialList.size, dialogBinding
                            )
                        else toast(getString(R.string.thereIsNoSerial), requireActivity())
                    }

                }
            })
    }

    private fun onConfirmSeiralsClick(
        receivingDetailID: String,
        worderTaskId: String,
        serialListSize: Int,
        dialogBinding: DialogSerialScanBinding
    ) {
        showConfirmSheetDialog(
            receivingDetailID, worderTaskId,
            getString(R.string.serial_scan_confirm),
            getString(R.string.are_you_confirm).replace("0", serialListSize.toString()),
            serialListSize,
            dialogBinding,
        )

    }

    private fun showSerialAdapter(
        rv: RecyclerView,
        serialList: List<ReceivingDetailSerialModel>,
        searchEdi: EditText, productId: String,
        dialogBinding: DialogSerialScanBinding, workerTaskId: String
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
                        model.receivingDetailID,
                        model.itemSerialID,
                        productId,
                        dialogBinding,
                        workerTaskId
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

    private fun initSerialScanDialogInfo(
        dialogBinding: DialogSerialScanBinding, productTitle: String,
        productCode: String, invtypeTitle: String
    ) {
        dialogBinding.layoutTopInfo.productTitle.setText(productTitle)
        dialogBinding.layoutTopInfo.productCode.setText(productCode)
        dialogBinding.layoutTopInfo.title1.setText(getString(R.string.productTitle))
        dialogBinding.layoutTopInfo.invType.text = invtypeTitle

    }

    private fun showSerialsSize(
        serialList: List<ReceivingDetailSerialModel>,
        serialsCount: TextView
    ) {
        val sb = StringBuilder()
        sb.append(getString(R.string.tools_scannedItems))
        sb.append(serialList.size)
        serialsCount.setText(sb.toString())
    }

    private fun showConfrimHandDialog(
        receivingDetailID: String,
        count: String,
        workerTaskID: String,
        dialogBinding: DialogCountBinding,
        countDialog: AlertDialog,
        title: String,
        desc: String
    ) {
        var mySheetAlertDialog: SheetAlertDialog? = null
        mySheetAlertDialog =
            SheetAlertDialog(title, desc, object : SheetAlertDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    countReceiveDetail(
                        receivingDetailID, count, workerTaskID,
                        dialogBinding, countDialog, mySheetAlertDialog
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

    private fun showConfirmSheetDialog(
        receivingDetailID: String,
        workerTaskId: String,
        title: String,
        desc: String,
        size: Int,
        dialogBinding: DialogSerialScanBinding,
    ) {
        var mySheetAlertDialog: SheetAlertDialog? = null
        mySheetAlertDialog =
            SheetAlertDialog(title, desc, object : SheetAlertDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {
                    if (
                        dialogBinding.layoutTopInfo.quantityEdi.isEnabled
                        && dialogBinding.layoutTopInfo.quantityEdi.length()!=0
                        && dialogBinding.layoutTopInfo.quantityEdi
                            .text.toString().toInt()>size)
                    {
                        viewModel.confirmReceiveDetailSerial(
                            pref.getDomain(),
                            receivingDetailID,
                            quantity = dialogBinding.layoutTopInfo.quantityEdi
                                .text.toString().toInt(),
                            workerTaskId,
                            pref.getTokenGlcTest(),
                            progress
                        )
                    } else {
                        viewModel.confirmReceiveDetailSerial(
                            pref.getDomain(), receivingDetailID,
                            quantity = size, workerTaskId, pref.getTokenGlcTest(), progress
                        )
                    }



                    observeConfirm(mySheetAlertDialog)
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

    private fun observeConfirm(mySheetAlertDialog: SheetAlertDialog?) {
        viewModel.getConfirmReceiveDetail().observe(viewLifecycleOwner,
            object : Observer<ConfirmReceiveDetailModel> {
                override fun onChanged(it: ConfirmReceiveDetailModel) {
                    mySheetAlertDialog?.dismiss()
                    scanDialog?.dismiss()

                    refreshReceiveDetail()
                }

            })
    }

    private fun showDeleteSheetDialog(
        title: String,
        desc: String,
        receivingDetailID: String,
        itemSerialID: String,
        productId: String, dialogBinding: DialogSerialScanBinding, workerTaskId: String
    ) {
        var mySheetAlertDialog: SheetAlertDialog? = null
        mySheetAlertDialog =
            SheetAlertDialog(title, desc, object : SheetAlertDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    removeSerial(
                        receivingDetailID, itemSerialID, productId,
                        progress, mySheetAlertDialog, dialogBinding, workerTaskId
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
        receivingDetailID: String,
        itemSerialID: String,
        productId: String,
        progress: ProgressBar,
        mySheetAlertDialog: SheetAlertDialog?,
        dialogBinding: DialogSerialScanBinding,
        workerTaskId: String
    ) {


        viewModel.removeSerial(
            pref.getDomain(),
            receivingDetailID,
            itemSerialID,
            productId,
            pref.getTokenGlcTest(),
            progress
        )
        viewModel.getRemoveSerialModel()
            .observe(viewLifecycleOwner, object : Observer<RemoveSerialModel> {
                override fun onChanged(it: RemoveSerialModel) {
                    mySheetAlertDialog?.dismiss()

                    disposeRequest()
                    viewModel.setSerialList(
                        pref.getDomain(),
                        receivingDetailID,
                        pref.getTokenGlcTest(),
                        dialogBinding.progress
                    )
                    observeSerialList(dialogBinding, productId, receivingDetailID, workerTaskId)
                }

            })
    }

    private fun obserReceiveDetailData() {
        viewModel.getReceiveDetailsList()
            .observe(requireActivity(), object : Observer<List<ReceiveDetailRow>> {
                override fun onChanged(list: List<ReceiveDetailRow>) {
                    if (view != null && isAdded) {
                        b.swipeLayout.isRefreshing = false
                        lastReceivingPosition = list.size - 1
                        setDetailAdapter(list)

                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (view != null) {
            viewModel.getDetailCount().removeObservers(viewLifecycleOwner)
            viewModel.getReceiveDetailsList().removeObservers(viewLifecycleOwner)
            viewModel.getSerialsList().removeObservers(viewLifecycleOwner)
            viewModel.getReceiveDetailCount().removeObservers(viewLifecycleOwner)
            viewModel.getConfirmReceiveDetail().removeObservers(viewLifecycleOwner)
            viewModel.getAddingSerialResult().removeObservers(viewLifecycleOwner)
            viewModel.getRemoveSerialModel().removeObservers(viewLifecycleOwner)
            disposeRequest()
        }

    }

    override fun init() {
        setToolbarTitle(requireActivity(), getString(R.string.receivingDetail))

        setToolbarBackground(b.mainToolbar.rel2, requireActivity())

        val receiveNumber = arguments?.getString(Utils.RECEIVE_NUMBER)
        val driveFullName = arguments?.getString(Utils.DRIVE_FULLNAME)
        val dockCode = arguments?.getString(Utils.DOCK_CODE)
        val createdOn = arguments?.getString(Utils.CREATED_ON)
        val carTypeTitle = arguments?.getString(Utils.CAR_TYPE_TITLE)
        val containerNumber = arguments?.getString(Utils.CONTAINER_NUMBER)


        val plaque1 = arguments?.getString(Utils.PLAQUE_1)
        val plaque2 = arguments?.getString(Utils.PLAQUE_2)
        val plaque3 = arguments?.getString(Utils.PLAQUE_3)
        val plaque4 = arguments?.getString(Utils.PLAQUE_4)
        b.receiveItem.plaque.setText(
            getBuiltString(
                plaque3.toString(),
                plaque2.toString(),
                plaque1.toString()
            )
        )
        b.receiveItem.plaqueYear.text = plaque4

        b.receiveItem.recevieNumber.setText(receiveNumber)
        b.receiveItem.driverFullName.text = driveFullName
        b.receiveItem.containerNumber.text = containerNumber


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

