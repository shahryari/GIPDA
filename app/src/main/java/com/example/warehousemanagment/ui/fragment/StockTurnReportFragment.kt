package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentStockTurnReportBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.stock.StockTurnItemLocationRow
import com.example.warehousemanagment.ui.adapter.StockTurnReportAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.StockTurnReportViewModel

class StockTurnReportFragment : BaseFragment<StockTurnReportViewModel, FragmentStockTurnReportBinding>() {
    var sortType= Utils.CREATED_ON
    var stockPage= Utils.PAGE_START
    var receiveOrder= Utils.ASC_ORDER
    var lastPosition=0
    var chronometer: CountDownTimer?=null
    lateinit var stockTurnTeamLocationID: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }

        refresh()

        observeStockList()
        observeStockCount()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        b.filterImg.root.visibility = View.GONE
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
                binding.relLocationCode.visibility=View.INVISIBLE
                binding.relOwnerCode.visibility=View.INVISIBLE
                binding.relProductTitle.visibility=View.INVISIBLE
                binding.productCode.text=getString(R.string.createdOn)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.CREATED_ON-> checkTick(binding.productCodeImg,binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(receiveOrder== Utils.ASC_ORDER)
                {
                    asc.backgroundTintList= ContextCompat
                        .getColorStateList(requireActivity(), R.color.mainYellow)
                    desc.backgroundTintList=null
                }else
                {
                    desc.backgroundTintList= ContextCompat.
                    getColorStateList(requireActivity(), R.color.mainYellow)
                    asc.backgroundTintList=null
                }
            }


            override fun onCloseClick() { sheet?.dismiss() }

            override fun onAscClick()
            {
                if (receiveOrder!= Utils.ASC_ORDER)
                {
                    receiveOrder= Utils.ASC_ORDER
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
                if (sortType!= Utils.CREATED_ON)
                {
                    sortType= Utils.CREATED_ON
                    refresh()
                }

            }

            override fun onProductTitleClick()
            {


            }

            override fun onOwnerClick()
            {


            }

            override fun onRel5Click() {
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }

    private fun refresh()
    {
        stockPage = Utils.PAGE_START
        viewModel.clearList()
        setStockTakeData()
    }

    private fun observeStockCount()
    {
        viewModel.getCount().observe(viewLifecycleOwner, Observer<Int> { it ->
            setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                it, "Stock Location Items")
        })

    }

    private fun observeStockList()
    {
        viewModel.getStockTurnItemList()
            .observe(viewLifecycleOwner){

                b.swipeLayout.isRefreshing=false
                lastPosition=it.size-1
                showStockTakeList(it)
            }
    }
    private fun showStockTakeList(list:List<StockTurnItemLocationRow>)
    {
        if(lastPosition- Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition- Utils.ROWS)

        val  adapter = StockTurnReportAdapter(
            list,
            viewModel.getTaskTypes(),
            viewModel.invList(requireContext()),

            ) {

            stockPage = stockPage + 1
            setStockTakeData()
        }
        b.rv.adapter = adapter



        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData { refresh()}
        }


    }

    private fun setStockTakeData()
    {
        viewModel.setStockTurnItemList(
            pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi),
            stockTurnTeamLocationID,
            stockPage,
            receiveOrder,
            pref.getTokenGlcTest(),
            b.progressBar,
            b.swipeLayout,
            requireContext()
        )
    }



    override fun onDestroy()
    {
        super.onDestroy()
        if(view!=null){
            viewModel.getStockTurnItemList().removeObservers(viewLifecycleOwner)
        }

    }
    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),"Stock Taking Location Report")

        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
        b.stockTakeLocation.add3.visibility = View.GONE
        b.stockTakeLocation.add2.visibility = View.GONE
        b.stockTakeLocation.add1.visibility = View.GONE
        b.stockTakeLocation.count.isEnabled = false
        b.stockTakeLocation.count2.isEnabled = false
        b.stockTakeLocation.count3.isEnabled = false
        b.stockTakeLocation.values1.visibility = View.GONE
        b.stockTakeLocation.values2.visibility = View.GONE
        b.stockTakeLocation.values3.visibility = View.GONE
        b.stockTakeLocation.lineSave.visibility = View.INVISIBLE
        arguments?.getString("StockTurnTeamLocationID")?.let {
            stockTurnTeamLocationID = it
        }
        val locationCode=arguments?.getString(Utils.locationCode)
        val productTitle=arguments?.getString(Utils.ProductTitle)
        val productCode=arguments?.getString(Utils.ProductCode)
        val ownerCode=arguments?.getString(Utils.OwnerCode)
        val firstQuantity=arguments?.getString("FirstQuantity")
        val secondQuantity=arguments?.getString("SecondQuantity")
        val thirdQuantity=arguments?.getString("ThirdQuantity")
        val realInventory=arguments?.getInt(Utils.Quantity)
        val invTypeTitle=arguments?.getString("InventoryType")

        b.stockTakeLocation.locationCode.text=locationCode
        b.stockTakeLocation.productTitle.text=productTitle
        b.stockTakeLocation.goodSystemCode.text=productCode
        b.stockTakeLocation.ownerCode.text=ownerCode
        b.stockTakeLocation.count.setText(firstQuantity?:"")
        b.stockTakeLocation.count2.setText(secondQuantity?:"")
        b.stockTakeLocation.count3.setText(thirdQuantity?:"")
        b.stockTakeLocation.quantity.text=realInventory.toString()
        b.stockTakeLocation.invTypeTitle.text=invTypeTitle
    }




    override fun getLayout(): Int {
        return R.layout.fragment_stock_turn_report
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }




}