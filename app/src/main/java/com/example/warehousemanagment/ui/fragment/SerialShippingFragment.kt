package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetBottomBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.databinding.PatternReceivingBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.check_truck.deny.DenyCheckTruckModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.shipping.shipping_truck.ShippingTruckRow
import com.example.warehousemanagment.ui.adapter.ShippingAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetConfirmDialog
import com.example.warehousemanagment.ui.dialog.SheetDenyAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetDenyApproveAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.SerialShippingViewModel

class SerialShippingFragment: BaseFragment<SerialShippingViewModel, FragmentReceivingBinding>()
{
    var sortType=Utils.DockAssignTime
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastPosition=0
    var chronometer: CountDownTimer?=null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        b.swipeLayout.setOnRefreshListener() {
            refresh()
        }
        refresh()

        observeShipping()
        observeCountOfList()
        observeTruckLoadingRemove()
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener() {
            showFilterSheetDialog()
        }

    }

    fun showConfirmDialog(shippingAddressId: String){
        var sheet: SheetConfirmDialog? = null
        sheet = SheetConfirmDialog(
            getString(R.string.remove),
            getString(R.string.areYouSureToRemove),
            object : SheetConfirmDialog.OnClickListener{
                override fun onCanselClick() {
                    sheet?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    viewModel.removeTruckLoading(pref.getDomain(),shippingAddressId,pref.getTokenGlcTest())
                }

                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {
                }

                override fun onDismiss() {
                    sheet?.dismiss()
                }

                override fun init(binding: DialogSheetBottomBinding) {
                }

            }
        )
        sheet.show(parentFragmentManager,"")
    }

    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.relLocationCode.visibility=View.INVISIBLE
                binding.ownerCode.text=getString(R.string.docTime)
                binding.productCode.text=getString(R.string.driverFullName)
                binding.productTitle.text=getString(R.string.shippingNumber2)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.DriverFullName ->checkTick(binding.productCodeImg,binding)
                    Utils.ShippingNumber ->checkTick(binding.productTitleImg,binding)
                    Utils.DockAssignTime-> checkTick(binding.ownerCOdeImg, binding)

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
                if (sortType!=Utils.DriverFullName)
                {
                    sortType=Utils.DriverFullName
                    refresh()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!=Utils.ShippingNumber)
                {
                    sortType=Utils.ShippingNumber
                    refresh()
                }

            }

            override fun onOwnerClick()
            {
                if (sortType!=Utils.DockAssignTime)
                {
                    sortType=Utils.DockAssignTime
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
        viewModel.clearList()
        receivePage = Utils.PAGE_START
        setShippingData()
    }

    private fun observeCountOfList()
    {
        viewModel.getShippingCount().observe(viewLifecycleOwner
        ) { it ->
            setBelowCount(
                requireActivity(), getString(R.string.tools_you_have),
                it, getString(R.string.truckToShip)
            )
        }
    }

    private fun setShippingData()
    {
        viewModel.setShippingList(
            pref.getDomain(),textEdi(b.mainToolbar.searchEdi),
            receivePage,Utils.ROWS,sortType,receiveOrder
            ,pref.getTokenGlcTest(),b.progressBar,b.swipeLayout)
    }

    private fun observeShipping()
    {
        viewModel.getShippingList().observe(viewLifecycleOwner
        ) {
            if (view != null && isAdded) {
                b.swipeLayout.isRefreshing = false

                lastPosition = it.size - 1
                showShippingList(it)

            }
        }
    }

    private fun observeTruckLoadingRemove(){
        viewModel.getTruckLoadingRemoved().observe(viewLifecycleOwner){
            showAlertDialog(it.messages.firstOrNull()?:"")
            refresh()
        }
    }

    fun showAlertDialog(message: String){
        var sheet : SheetAlertDialog? = null
        sheet = SheetAlertDialog(
            "Remove",
            message,
            object: SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    sheet?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    sheet?.dismiss()
                }

                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {
                    cansel.visibility = View.GONE
                }

                override fun onDismiss() {
                    sheet?.dismiss()
                }

            }
        )
        sheet.show(parentFragmentManager,"")
    }

    private fun showShippingList(list: List<ShippingTruckRow>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition-Utils.ROWS)

        val adapter = ShippingAdapter(
            list,
            requireActivity(),
            object : ShippingAdapter.OnCallBackListener
            {
                override fun init(binding: PatternReceivingBinding, currentStatusCode: String) {

                    if (currentStatusCode == Utils.WhLoadingFinish) {
                        binding.leftDock.visibility = View.VISIBLE
                    }
                    binding.leftDock.text = getString(R.string.leftDock)
                    binding.lineCustomerCount.visibility = View.VISIBLE
                }

                override fun onClick(model: ShippingTruckRow, position: Int)
                {
                    if (model.hasCheckTruck) {
                        val sb= getBuiltString(getString(R.string.AreYouSureShippingNumber),
                            model.shippingNumber," "+getString(R.string.plaquNumberAnd),model.plaqueNumber)
                        showConfirmDenySheetDialog(getString(R.string.carControll),
                            sb ,model)
                    }
                    else
                    {
                        val bundle=Bundle()
                        bundle.putString(Utils.ShippingId,model.shippingID)
                        bundle.putString(Utils.DRIVE_FULLNAME,model.driverFullName)
                        bundle.putString(Utils.CREATED_ON,model.createdOnString)
                        bundle.putString(Utils.ShippingNumber,model.shippingNumber)
                        bundle.putString(Utils.BOLNumber,model.bOLNumber)
                        bundle.putString(Utils.DOCK_CODE,model.dockCode)

                        bundle.putString(Utils.PLAQUE_1,model.plaqueNumberFirst)
                        bundle.putString(Utils.PLAQUE_2,model.plaqueNumberSecond)
                        bundle.putString(Utils.PLAQUE_3,model.plaqueNumberThird)
                        bundle.putString(Utils.PLAQUE_4,model.plaqueNumberFourth)
                        bundle.putString(Utils.PLAQUE,model.plaqueNumber)
                        bundle.putInt(Utils.total,model.total)
                        bundle.putInt(Utils.Done,model.doneCount)
                        bundle.putInt(Utils.doneQuantity,model.sumQuantity)
                        bundle.putInt(Utils.sumDoneQuantity,model.sumDonQuantity)
                        bundle.putInt(Utils.customerCount,model.customerCount)

                        pref.saveAdapterPosition(position)





                        navController?.navigate(R.id.action_serialShippingFragment_to_serialShippingDetailFragment,bundle)
                    }

                }


                override fun reachToEnd(position: Int)
                {
                    receivePage += 1
                    setShippingData()
                }

                override fun onLeftDockClick(model: ShippingTruckRow)
                {
                    showLeftDockSheetDialog(
                        title = getString(R.string.leftDockConfirm),
                        desc =
                        buildString {
                            append(getString(R.string.areYouSureLeftDock))
                            append(model.shippingNumber)
                            append("]")
                        },
                        shippingAddressId =model.shippingAddressID
                    )

                }

                override fun onTruckLoadingRemove(shippingAddressId: String) {
                    showConfirmDialog(shippingAddressId)
                }
            })
        b.rv.adapter = adapter


        b.mainToolbar.searchEdi.doAfterTextChanged {
            startTimerForReceiveingData()
        }




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
    private fun showDenySheetDialog(
        reasons: List<CatalogModel>, title: String,
        model: ShippingTruckRow,
        previousSheetDialog: SheetDenyApproveAlertDialog?
    )
    {
        var valueType=1
        var mySheetAlertDialog: SheetDenyAlertDialog?=null
        mySheetAlertDialog= SheetDenyAlertDialog(reasons,title
            ,object : SheetDenyAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progressBar: ProgressBar)
                {
                    viewModel.setDenyTruck(
                        pref.getDomain(),model.shippingAddressID,model.shippingID,
                        valueType,pref.getTokenGlcTest(),progressBar)

                    getDenyResult(mySheetAlertDialog, previousSheetDialog)

                }

                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun selectType(model: CatalogModel) {
                    valueType=model.valueField
                }

                override fun onDismiss() {

                }


            })

        mySheetAlertDialog.show(this.getParentFragmentManager(), "")

    }

    private fun getDenyResult(
        mySheetAlertDialog: SheetDenyAlertDialog?,
        previousSheetDialog: SheetDenyApproveAlertDialog?
    ) {
        viewModel.getDenyModel()
            .observe(viewLifecycleOwner, object : Observer<DenyCheckTruckModel> {
                override fun onChanged(it: DenyCheckTruckModel) {
                    mySheetAlertDialog?.dismiss()
                    previousSheetDialog?.dismiss()
                }

            })
    }

    private fun showConfirmDenySheetDialog(title: String, desc: String,
                                           model: ShippingTruckRow)
    {
        var mySheetAlertDialog: SheetDenyApproveAlertDialog?=null
        mySheetAlertDialog= SheetDenyApproveAlertDialog(title,desc
            ,object : SheetDenyApproveAlertDialog.OnClickListener
            {
                override fun init(binding: DialogSheetBottomBinding) {

                }

                override fun onDenyClick()
                {
                    showDenySheetDialog(viewModel.getDenyReason(),getString(R.string.denyReason),model,mySheetAlertDialog)

                }

                override fun onOkClick(progress: ProgressBar)
                {
                    viewModel.setConfirmTruck(
                        pref.getDomain(),model.shippingAddressID,pref.getTokenGlcTest(),progress)

                    getConfirmResult(mySheetAlertDialog)


                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onDismiss() {
                    refresh()
                }


            })
        mySheetAlertDialog.show(this.getParentFragmentManager(), "")


    }

    private fun showLeftDockSheetDialog(title: String, desc: String,
                                        shippingAddressId:String,
    )
    {
        var mySheetAlertDialog: SheetDenyApproveAlertDialog?=null
        mySheetAlertDialog= SheetDenyApproveAlertDialog(title,desc
            ,object : SheetDenyApproveAlertDialog.OnClickListener
            {
                override fun init(binding: DialogSheetBottomBinding) {
                    binding.okCanselBtn.ok.text = getString(R.string.ok)
                    binding.okCanselBtn.cansel.text = getString(R.string.cancel)
                }

                override fun onDenyClick()
                {
                    mySheetAlertDialog?.dismiss()

                }

                override fun onOkClick(progress: ProgressBar)
                {
                    viewModel.leftDock(
                        pref.getDomain(),
                        shippingAddressId = shippingAddressId,
                        cookie = pref.getTokenGlcTest(),
                        progressBar = progress,
                        onLeftDockError = {
                            mySheetAlertDialog?.dismiss()
                        },
                        onLeftDockSuccess = {
                            mySheetAlertDialog?.dismiss()
                            refresh()

                        }
                    )
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onDismiss() {

                }


            })
        mySheetAlertDialog.show(this.getParentFragmentManager(), "")


    }


    private fun getConfirmResult(mySheetAlertDialog: SheetDenyApproveAlertDialog?) {
        viewModel.getConfirmResult().observe(viewLifecycleOwner
        ) {
            mySheetAlertDialog?.dismiss()
            setShippingData()
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){
            viewModel.getShippingList().removeObservers(viewLifecycleOwner)
            viewModel.getShippingCount().removeObservers(viewLifecycleOwner)
        }

    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.shipping))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }



    override fun getLayout(): Int {
        return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}