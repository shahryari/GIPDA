package com.example.warehousemanagment.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.wait_to_load.wait_truck.WaitTruckLoadingRow
import com.example.warehousemanagment.ui.adapter.WaitForLoadingAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.WaitForLoadingViewModel


class WaitForLoadingFragment :
    BaseFragment<WaitForLoadingViewModel, FragmentReceivingBinding>()
{
    var sortType= Utils.DockAssignTime
    var receivePage= Utils.PAGE_START
    var receiveOrder= Utils.ASC_ORDER
    var lastPosition=0
    var isCompleted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)


        if (isCompleted){
            b.closeTabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.mainYellow))
            b.openTabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.yellowGray))
            b.closeTab.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            b.openTab.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        }
        else {
            b.openTabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.mainYellow))
            b.closeTabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.yellowGray))
            b.openTab.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            b.closeTab.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        }
        b.closeTabLayout.setOnClickListener {
            b.closeTabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.mainYellow))
            b.openTabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.yellowGray))
            b.closeTab.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            b.openTab.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            isCompleted = true
            viewModel.clearReceiveList()
            refresh()

        }
        b.openTabLayout.setOnClickListener {
            b.openTabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.mainYellow))
            b.closeTabLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.yellowGray))
            b.openTab.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            b.closeTab.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            viewModel.clearReceiveList()
            isCompleted = false
            refresh()
        }

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }
        refresh()

        observeWaitForLoadingList()
        observeWaitCount()
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
                binding.relLocationCode.visibility=View.GONE
                binding.ownerCode.text=getString(R.string.dockAssignTime )
                binding.productCode.text=getString(R.string.shippingNumber2)
                binding.productTitle.text=getString(R.string.driverFullName)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {

                    Utils.DockAssignTime-> checkTick(binding.ownerCOdeImg, binding)
                    Utils.ShippingNumber ->checkTick(binding.productCodeImg,binding)
                    Utils.DRIVE_FULLNAME-> checkTick(binding.productTitleImg, binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(receiveOrder==Utils.ASC_ORDER)
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

            }

            override fun onProductCodeClick()
            {
                if (sortType!=Utils.ShippingNumber)
                {
                    sortType=Utils.ShippingNumber
                    refresh()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!=Utils.DRIVE_FULLNAME)
                {
                    sortType=Utils.DRIVE_FULLNAME
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
    private fun setWaitForLoading()
    {
        viewModel.setWaitForLoadingList(
            pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi),
            isCompleted,
            receivePage,
            Utils.ROWS,
            sortType,
            receiveOrder,
            pref.getTokenGlcTest(),
            b.progressBar,
            b.swipeLayout
        )
    }

    private fun refresh()
    {
        viewModel.dispose()
        receivePage = Utils.PAGE_START
        viewModel.clearReceiveList()
        setWaitForLoading()
    }

    private fun observeWaitForLoadingList()
    {
        viewModel.getWaitForLoadingList().observe(viewLifecycleOwner
        ) { it ->
            b.swipeLayout.isRefreshing = false
            lastPosition = it.size - 1
            showWaitsForLoadingList(it)
        }

    }
    private fun observeWaitCount()
    {
        viewModel.getWaifForLoadingCount()
            .observe(viewLifecycleOwner) { it ->
                setBelowCount(
                    requireActivity(), getString(R.string.youHaveWaitedTruck),
                    it, getString(R.string.waitedTruckToLoad)
                )
            }
    }


    private fun showWaitsForLoadingList(list:List<WaitTruckLoadingRow>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition-Utils.ROWS)

        val  adapter = WaitForLoadingAdapter(list, requireActivity(),
            object : WaitForLoadingAdapter.OnCallBackListener
        {
            override fun onClick(model: WaitTruckLoadingRow)
            {
                if (isCompleted){
                    val bundle=Bundle()
                    bundle.putString(Utils.ShippingId,model.shippingID)
                    bundle.putString(Utils.DRIVE_FULLNAME,model.driverFullName)
                    bundle.putString(Utils.ShippingNumber,model.shippingNumber)
                    bundle.putString(Utils.BOLNumber,model.bOLNumber)

                    bundle.putString(Utils.PLAQUE_1,model.plaqueNumberFirst)
                    bundle.putString(Utils.PLAQUE_2,model.plaqueNumberSecond)
                    bundle.putString(Utils.PLAQUE_3,model.plaqueNumberThird)
                    bundle.putString(Utils.PLAQUE_4,model.plaqueNumberFourth)
                    bundle.putString(Utils.PLAQUE,model.plaqueNumber)
                    navController?.navigate(R.id.action_waitForLoadingFragment_to_waitForLoadingDetailFragment,bundle)
                } else {
                    val sb= getBuiltString(getString(R.string.areYouSureTruck),
                        " ",model.shippingNumber," ", getString(R.string.shippingNumber),
                        " ",getString(R.string.and)," ",
                        model.plaqueNumber.replaceFirst("-",""),
                        " ",getString(R.string.plaquNumber))

                    showConfirmSheet(getString(R.string.confirmAssign),
                        sb,
                        model)
                }

            }

            override fun reachToEnd(position: Int)
            {
                receivePage=receivePage+1
                setWaitForLoading()
            }
        })
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData {
                /**/
                refresh()
                /**/
            }
        }

//
//        adapter.setFilter(search(textEdi(b.mainToolbar.searchEdi),list,SearchFields.ShippingNumber,
//            SearchFields.CreatedOnString,SearchFields.CarTypeTitle,
//            SearchFields.PlaqueNumber))
//        b.mainToolbar.searchEdi.doAfterTextChanged()
//        {
//            adapter.setFilter(search(textEdi(b.mainToolbar.searchEdi),list,SearchFields.ShippingNumber,
//                SearchFields.CreatedOnString,SearchFields.CarTypeTitle,
//            SearchFields.PlaqueNumber))
//        }


    }


    private fun showConfirmSheet(title: String, msg: String, model: WaitTruckLoadingRow)
    {
        var sheet: SheetAlertDialog?=null
        sheet= SheetAlertDialog(title,msg,
            object : SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    sheet?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {
                    viewModel.setLoadingAssign(
                        pref.getDomain(),model.shippingAddressID,pref.getTokenGlcTest(),progress)
                    observeLoadingAssign(sheet)

                }

                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {
                    refresh()
                }


            })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun observeLoadingAssign(sheet: SheetAlertDialog?)
    {
        viewModel.getLoadingAssignModel().observe(viewLifecycleOwner
        ) { sheet?.dismiss() }
    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){
            viewModel.getWaitForLoadingList().removeObservers(viewLifecycleOwner)
        }

    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.waite_for_loading))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
        b.tabs.visibility = View.VISIBLE
        b.openTab.text = getString(R.string.truckInDock)
        b.closeTab.text = getString(R.string.pickingComplete)
    }




    override fun getLayout(): Int {
         return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
