package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentSerialPutawayDetailBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.putaway.serial_putaway.MySerialReceiptDetailRow
import com.example.warehousemanagment.ui.adapter.SerialPutawayDetailAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.SerialPutawayDetailViewModel

class SerialPutawayDetailFragment() : BaseFragment<SerialPutawayDetailViewModel,FragmentSerialPutawayDetailBinding>() {

    var sortType= Utils.DockAssignTime
    var page= Utils.PAGE_START
    var order= Utils.ASC_ORDER
    var lastPosition=0
    var chronometer: CountDownTimer?=null
    lateinit var receiptId: String
    lateinit var receiptNumber: String
    lateinit var containerNumber: String
    lateinit var driverFullName: String
    lateinit var plaque1: String
    lateinit var plaque2: String
    lateinit var plaque3: String
    lateinit var plaque4: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }

        refresh()

        observeList()
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

    private fun observeList() {
        viewModel.getDetailList().observe(this){
            b.swipeLayout.isRefreshing=false
            lastPosition=it.size-1
            showList(it)
        }
    }

    private fun showList(list: List<MySerialReceiptDetailRow>){
        if(lastPosition - Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition - Utils.ROWS)

        val adapter = SerialPutawayDetailAdapter(
            list,
            context!!,
            {
                page += 1
                setListData()
            }
        ) {model->
            val bundle = Bundle()
            bundle.putString(Utils.DRIVE_FULLNAME, driverFullName)
            bundle.putString(Utils.RECEIVE_NUMBER, receiptNumber)
            bundle.putString(Utils.RECEIVING_ID,model.receiptDetailID)
            bundle.putString(Utils.CONTAINER_NUMBER,containerNumber)
            bundle.putString(Utils.PLAQUE_1,plaque1)
            bundle.putString(Utils.PLAQUE_2,plaque2)
            bundle.putString(Utils.PLAQUE_3,plaque3)
            bundle.putString(Utils.PLAQUE_4,plaque4)
            bundle.putInt(Utils.Quantity,model.quantity)
            bundle.putInt("scan",model.scanCount)
            bundle.putString(Utils.ProductCode,model.productCode)
            bundle.putString(Utils.ProductTitle,model.productName)
            bundle.putString(Utils.locationInventory,model.invTypeTitle)

            navController?.navigate(R.id.action_serialPutawayDetailFragment_to_serialPutawayDetailLocationFragment,bundle)

        }
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData { refresh()}
        }
    }

    private fun refresh()
    {
        viewModel.dispose()
        page = Utils.PAGE_START
        viewModel.clearDetailList()
        setListData()
    }

    private fun setListData(){
        viewModel.setDetailList(
            pref.getDomain(),
            pref.getTokenGlcTest(),
            textEdi(b.mainToolbar.searchEdi),
            receiptId,
            1,
            10,
            sortType,
            order,
            context!!,
            progressBar = b.progressBar,
            swipeLayout = b.swipeLayout
        )
    }

    private fun observeCount()
    {
        viewModel.getDetailCount().observe(viewLifecycleOwner
        ) { it ->
            setBelowCount(
                requireActivity(), getString(R.string.tools_you_have),
                it, getString(R.string.productsToReceive)
            )
        }

    }

    override fun init() {
        setToolbarTitle(requireActivity(), "Serial Putaway Detail")

        setToolbarBackground(b.mainToolbar.rel2, requireActivity())

        receiptId = arguments?.getString(Utils.RECEIVING_ID)?:""
        receiptNumber = arguments?.getString(Utils.RECEIVE_NUMBER)?:""
        driverFullName = arguments?.getString(Utils.DRIVE_FULLNAME)?:""
        containerNumber = arguments?.getString(Utils.CONTAINER_NUMBER)?:""


        plaque1 = arguments?.getString(Utils.PLAQUE_1)?:""
        plaque2 = arguments?.getString(Utils.PLAQUE_2)?:""
        plaque3 = arguments?.getString(Utils.PLAQUE_3)?:""
        plaque4 = arguments?.getString(Utils.PLAQUE_4)?:""

        b.header.plaque.text = getBuiltString(
            plaque3,
            plaque2,
            plaque1
        )
        b.header.plaqueYear.text = plaque4

        b.header.driverFullName.text = driverFullName
        b.header.recevieNumber.text = receiptNumber
        b.header.containerNumber.text = containerNumber
    }

    override fun getLayout(): Int {
        return R.layout.fragment_serial_putaway_detail
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}