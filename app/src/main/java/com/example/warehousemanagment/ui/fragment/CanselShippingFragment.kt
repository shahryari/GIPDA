package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.kotlin_wallet.ui.base.BaseFragment
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.databinding.PatternReceivingBinding
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.shipping.shipping_truck.ShippingTruckRow
import com.example.warehousemanagment.ui.adapter.ShippingAdapter
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.CanselShippingViewModel

class CanselShippingFragment: BaseFragment<CanselShippingViewModel
        ,FragmentReceivingBinding>()
{
    var sortType=Utils.DockAssignTime
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastPosition=0
    var chronometer: CountDownTimer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }
        refresh()

        observeShipping()
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
                binding.relLocationCode.visibility = View.INVISIBLE
                binding.ownerCode.text = getString(R.string.dockAssignTime)
                binding.productCode.text = getString(R.string.driverFullName)
                binding.productTitle.text = getString(R.string.shippingNumber2)

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

    private fun observeCount()
    {
        viewModel.getShippingCount().observe(viewLifecycleOwner,object :Observer<Int>
        {
            override fun onChanged(it: Int)
            {
                setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.truckToCancelShip))
            }

        })

    }

    private fun setShippingList()
    {
        viewModel.setShippingList(pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi),
            receivePage, Utils.ROWS, sortType, receiveOrder,
            pref.getTokenGlcTest(),b.progressBar,b.swipeLayout
        )
    }

    private fun refresh()
    {
        viewModel.dispose()
        viewModel.clearList()
        receivePage = Utils.PAGE_START
        setShippingList()
    }

    private fun observeShipping()
    {
        viewModel.getShippingList().observe(viewLifecycleOwner,
            object : Observer<List<ShippingTruckRow>>
            {
                override fun onChanged(it: List<ShippingTruckRow>)
                {
                    b.swipeLayout.isRefreshing=false
                    lastPosition=it.size-1
                    showShippingList(it)
                }
            })
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

                }

                override fun onClick(model: ShippingTruckRow, position: Int)
                {
                    val bundle=Bundle()
                    bundle.putString(Utils.ShippingId,model.shippingID)
                    bundle.putString(Utils.DRIVE_FULLNAME,model.driverFullName)
                    bundle.putString(Utils.CREATED_ON,model.createdOn)
                    bundle.putString(Utils.ShippingNumber,model.shippingNumber)
                    bundle.putString(Utils.BOLNumber,model.bOLNumber)
                    bundle.putString(Utils.DOCK_CODE,model.dockCode)

                    bundle.putString(Utils.PLAQUE_1,model.plaqueNumberFirst)
                    bundle.putString(Utils.PLAQUE_2,model.plaqueNumberSecond)
                    bundle.putString(Utils.PLAQUE_3,model.plaqueNumberThird)
                    bundle.putString(Utils.PLAQUE_4,model.plaqueNumberFourth)

                    pref.saveAdapterPosition(position)

                    navController?.navigate(R.id.action_canselShippingFragment_to_canselShippingDetailFragment,bundle)
                }


                override fun reachToEnd(position: Int)
                {
                    receivePage=receivePage+1
                    setShippingList()
                }

                override fun onLeftDockClick(model: ShippingTruckRow) {

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


//        adapter.setFilter(
//            search(textEdi(b.mainToolbar.searchEdi),list,
//                SearchFields.DriverFullName, SearchFields.ShippingNumber,
//                SearchFields.Dock, SearchFields.CreatedOn, SearchFields.CarPlaqueNumber,
//                SearchFields.BOLNumber)
//        )
//        b.mainToolbar.searchEdi.doAfterTextChanged()
//        {
//            adapter.setFilter(
//                search(textEdi(b.mainToolbar.searchEdi),list,
//                    SearchFields.DriverFullName, SearchFields.ShippingNumber,
//                    SearchFields.Dock, SearchFields.CreatedOn, SearchFields.CarPlaqueNumber,
//                    SearchFields.BOLNumber)
//            )
//        }


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

    override fun onDestroy()
    {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null)
        {
            viewModel.getShippingList().removeObservers(viewLifecycleOwner)
            viewModel.getShippingCount().removeObservers(viewLifecycleOwner)
        }

    }


    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.cancelShipping))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }


    override fun getLayout(): Int {
         return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}