package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetBottomBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentSerialPutawayBinding
import com.example.warehousemanagment.databinding.PatternSerialPutawayBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.putaway.serial_putaway.SerialReceiptOnPutawayRow
import com.example.warehousemanagment.ui.adapter.SerialPutawayAssignAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetConfirmDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.SerialPutawayViewModel

class SerialPutawayFragment : BaseFragment<SerialPutawayViewModel,FragmentSerialPutawayBinding>() {

    var sortType= Utils.DockAssignTime
    var page= Utils.PAGE_START
    var order= Utils.ASC_ORDER
    var lastPosition=0
    var chronometer: CountDownTimer?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }

        refresh()

        observeSerialList()
        observeCount()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener()
        {
            showFilterSheetDialog()
        }


    }


    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.relLocationCode.visibility= View.INVISIBLE
                binding.ownerCode.text=getString(R.string.dockAssignTime)
                binding.productCode.text=getString(R.string.recevieNumber)
                binding.productTitle.text=getString(R.string.containerNumber)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.RECEIVE_NUMBER-> checkTick(binding.productCodeImg,binding)
                    Utils.CONTAINER_NUMBER-> checkTick(binding.productTitleImg,binding)
                    Utils.DockAssignTime-> checkTick(binding.ownerCOdeImg, binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(order== Utils.ASC_ORDER)
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
                if (order!= Utils.ASC_ORDER)
                {
                    order= Utils.ASC_ORDER
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

            override fun onProductCodeClick()
            {
                if (sortType!= Utils.RECEIVE_NUMBER)
                {
                    sortType= Utils.RECEIVE_NUMBER
                    refresh()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!= Utils.CONTAINER_NUMBER)
                {
                    sortType= Utils.CONTAINER_NUMBER
                    refresh()
                }

            }

            override fun onOwnerClick()
            {
                if (sortType!= Utils.DockAssignTime)
                {
                    sortType= Utils.DockAssignTime
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
        page = Utils.PAGE_START
        viewModel.clearSerialList()
        setSerailData()
    }

    private fun observeCount()
    {
        viewModel.getSerialCount().observe(viewLifecycleOwner
        ) { it ->
            setBelowCount(
                requireActivity(), getString(R.string.tools_you_have),
                it, getString(R.string.tools_you_have_3_trcuk_to_receive)
            )
        }

    }

    private fun observeSerialList()
    {
        viewModel.getSerialList()
            .observe(viewLifecycleOwner){
                if (view!=null && isAdded)
                {
                    b.swipeLayout.isRefreshing=false
                    lastPosition=it.size-1
                    showSerialList(it)
                }
            }
    }
    private fun showSubmitAssignSheet(serialReceiptOnPutawayRow: SerialReceiptOnPutawayRow)
    {
        var mySheetAlertDialog: SheetConfirmDialog? = null
        mySheetAlertDialog = SheetConfirmDialog(getString(R.string.driverTaskSubmit),
            "Are you sure to remove [${serialReceiptOnPutawayRow.receiptNumber}] from your self?",
            object : SheetConfirmDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    viewModel.removeSerialReceipt(
                        pref.getDomain(),
                        serialReceiptOnPutawayRow.receiptID,
                        pref.getTokenGlcTest(),
                        requireContext(),
                        progress,
                        {
                            mySheetAlertDialog?.dismiss()
                            refresh()
                        },
                        {mySheetAlertDialog?.dismiss()}
                    )
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {

                }

                override fun init(binding: DialogSheetBottomBinding) {

                }

            })
        mySheetAlertDialog.show(getParentFragmentManager(), "")
    }
    private fun showSerialList(list:List<SerialReceiptOnPutawayRow>)
    {
        if(lastPosition - Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition - Utils.ROWS)

        val  adapter = SerialPutawayAssignAdapter(list, requireActivity(), object : SerialPutawayAssignAdapter.OnCallBackListener
        {
            override fun onAssign(model: SerialReceiptOnPutawayRow) {
                showSubmitAssignSheet(model)
            }

            override fun onItemClick(model: SerialReceiptOnPutawayRow) {
                val bundle = Bundle()
                bundle.putString(Utils.DRIVE_FULLNAME, model.driverFullName)
                bundle.putString(Utils.RECEIVE_NUMBER, model.receiptNumber)
                bundle.putString(Utils.RECEIVING_ID,model.receiptID)
                bundle.putString(Utils.CONTAINER_NUMBER,model.containerNumber)
                bundle.putString(Utils.PLAQUE_1,model.plaqueNumberFirst)
                bundle.putString(Utils.PLAQUE_2,model.plaqueNumberSecond)
                bundle.putString(Utils.PLAQUE_3,model.plaqueNumberThird)
                bundle.putString(Utils.PLAQUE_4,model.plaqueNumberFourth)
                bundle.putString(Utils.OwnerCode,model.ownerCode)
                bundle.putString(Utils.ownerName,model.ownerName)

                navController?.navigate(R.id.action_serialPutawayFragment_to_serialPutawayDetailFragment,bundle)
            }


            override fun reachToEnd(position: Int)
            {
                page += 1
                setSerailData()
            }

            override fun init(binding: PatternSerialPutawayBinding) {
                binding.assignBtn.text = "Remove From Me"
                binding.assignBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.red)
            }
        })
        b.rv.adapter = adapter



        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData { refresh()}
        }


    }

    private fun setSerailData()
    {
        viewModel.setSerialList(
            pref.getDomain(),
            pref.getTokenGlcTest(), textEdi(b.mainToolbar.searchEdi),
            page, Utils.ROWS, sortType, order,
            context!!,
            b.progressBar,
            b.swipeLayout
        )
    }



    override fun onDestroy()
    {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){
            viewModel.getSerialCount().removeObservers(viewLifecycleOwner)
            viewModel.getSerialList().removeObservers(viewLifecycleOwner)
        }

    }
    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun init() {

        setToolbarTitle(requireActivity(),"Serial Putaway")

        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }

    override fun getLayout(): Int {
        return R.layout.fragment_serial_putaway
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}