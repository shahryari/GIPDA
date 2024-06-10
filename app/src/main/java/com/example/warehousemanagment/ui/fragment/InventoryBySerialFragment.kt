package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentPickputDailyReportBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.chronometer
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideKeyboard
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.report_inventory.serial_inventory.SerialInventoryRows
import com.example.warehousemanagment.ui.adapter.ProductAdapter
import com.example.warehousemanagment.ui.adapter.SerialInventoryReportAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetPalletDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.InventorySerialViewModel


class InventoryBySerialFragment :
    BaseFragment<InventorySerialViewModel, FragmentPickputDailyReportBinding>()
{

    var sortType:String= Utils.LOCATION_CODE_SORT
    var orderType:String= Utils.ASC_ORDER
    var lastPosition=0
    var page= Utils.PAGE_START
    var adapter: SerialInventoryReportAdapter?=null
    lateinit var productId:String

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        clearEdi(b.serialClearImg,b.serialEdi)
        refresh()

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }


        b.relSetting.setOnClickListener()
        {
            showFilterSheet()

        }
        observeReport()
        observeLocationCount()

        b.serialEdi.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                refresh()
                return@OnEditorActionListener true
            }
            false
        })


        b.relFilter.setOnClickListener()
        {
            refresh()
        }

        b.productTitleEdi.doAfterTextChanged()
        {
            startTimerForGettingData()
            {
                if (lenEdi(b.productTitleEdi) !=0)
                {
                    viewModel.setProductList(
                        pref.getDomain(),
                        textEdi(b.productTitleEdi)
                        ,pref.getTokenGlcTest())

                    getProductSheetData(b.productTitleEdi,getString(R.string.productTitle))
                }
            }
        }


    }


    private fun getProductSheetData(tv: TextView, title: String)
    {
        var productSheet: SheetPalletDialog?=null
        productSheet = SheetPalletDialog(getString(R.string.warehouse),
            object : SheetPalletDialog.OnClickListener
            {
                override fun onCloseClick() { productSheet?.dismiss() }

                override fun initData(binding: DialogSheetDestinyLocationBinding)
                {
                    binding.title.text=title
                    observeProduct(productSheet, tv, binding.rv,binding.serialsCount)
                }
            })
        productSheet.show(this.getParentFragmentManager(),"")
    }

    private fun observeProduct(
        sheet: SheetPalletDialog?,
        tv: TextView,
        rv: RecyclerView,
        arrCounts: TextView
    )
    {
        viewModel.getProductsList().observe(viewLifecycleOwner,
            object : Observer<List<ProductModel>>
            {
                override fun onChanged(it: List<ProductModel>)
                {
                    arrCounts.text= getBuiltString(getString(R.string.tools_scannedItems)," ",it.size.toString())
                    val adapter = ProductAdapter(it, requireActivity(),
                        object : ProductAdapter.OnCallBackListener
                        {
                            override fun onClick(model: ProductModel)
                            {
                                sheet?.dismiss()
                                tv.text=model.productTitle
                                chronometer?.cancel()
                            }

                        })
                    rv.adapter = adapter
                }
            })
    }


    private fun refresh() {
        hideKeyboard(requireActivity())
        viewModel.dispose()
        viewModel.clearReportList()
        page = Utils.PAGE_START
        setReportInventory()
    }

    private fun showFilterSheet()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener {
            override fun initView(binding: DialogSheetSortFilterBinding) {}

            override fun initTickedSort(binding: DialogSheetSortFilterBinding) {
                when (sortType) {
                    Utils.LOCATION_CODE_SORT -> checkTick(binding.locationCodeImg, binding)

                    Utils.PRODUCT_CODE_SORT -> checkTick(binding.productCodeImg, binding)

                    Utils.PRODUCT_TITLE_SORT -> checkTick(binding.productTitleImg, binding)

                    Utils.OWNER_SORT -> checkTick(binding.ownerCOdeImg, binding)
                }
            }


            override fun initAscDesc(asc: TextView, desc: TextView) {
                if (orderType == Utils.ASC_ORDER) {
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

            override fun onAscClick()
            {
                if (orderType!= Utils.ASC_ORDER)
                {
                    orderType = Utils.ASC_ORDER
                    refresh()
                }

            }

            override fun onDescClick() {
                if (orderType!= Utils.DESC_ORDER)
                {
                    orderType = Utils.DESC_ORDER
                    refresh()
                }

            }

            override fun onLocationCodeClick() {
                if (sortType!= Utils.LOCATION_CODE_SORT)
                {
                    sortType = Utils.LOCATION_CODE_SORT
                    refresh()
                }

            }

            override fun onProductCodeClick() {
                if (sortType!= Utils.PRODUCT_CODE_SORT)
                {
                    sortType = Utils.PRODUCT_CODE_SORT
                    refresh()
                }

            }

            override fun onProductTitleClick() {
                if (sortType!= Utils.PRODUCT_TITLE_SORT)
                {
                    sortType = Utils.PRODUCT_TITLE_SORT
                    refresh()
                }

            }

            override fun onOwnerClick() {
                if (sortType!= Utils.OWNER_SORT)
                {
                    sortType = Utils.OWNER_SORT
                    refresh()
                }

            }

            override fun onRel5Click() {
                 
            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }



    fun receiveReportInventory(
        serialNumber:String,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String,
        progressBar: ProgressBar,
        swipeLayout: SwipeRefreshLayout
    )
    {

        viewModel.reportLocationInventory(
            pref.getDomain(),serialNumber,
            productId = productId,
            page,rows,sort,order,cookie, progressBar,swipeLayout)
    }
    fun observeReport()
    {
        viewModel.getReportLocationList().
        observe(viewLifecycleOwner,object : Observer<List<SerialInventoryRows>>
        {
            override fun onChanged(it: List<SerialInventoryRows>)
            {
                lastPosition=it.size-1
                showLocationList(it)
                log("itSize=",it.size.toString())
            }
        })
    }
    fun observeLocationCount()
    {
        viewModel.getLocationCount().
        observe(viewLifecycleOwner,object : Observer<Int>
        {
            override fun onChanged(it:Int)
            {
                setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.location_inventory))
            }
        })
    }




    private fun showLocationList(list:List<SerialInventoryRows>)
    {
        if(lastPosition- Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition- Utils.ROWS)
        adapter = SerialInventoryReportAdapter(list, requireActivity(),
            object : SerialInventoryReportAdapter.OnCallBackListener
            {
                override fun onClick()
                {

                }

                override fun reachToEnd(position: Int)
                {
                    page=page+1
                    setReportInventory()

                }
            })
        b.rv.adapter = adapter

    }

    private fun setReportInventory()
    {
        receiveReportInventory(
            textEdi(b.serialEdi), page, Utils.ROWS, sortType,
            orderType, pref.getTokenGlcTest(), b.progressBar,
            b.swipeLayout
        )
    }


    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onDestroy()
    {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){

        }

    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.serial_inventory))
        b.rel1.visibility=View.VISIBLE
        b.rel2.visibility=View.GONE
        b.rel3.visibility=View.GONE
        b.rel4.visibility=View.GONE
        b.rel5.visibility=View.GONE
        b.date.visibility=View.GONE

        productId= arguments?.getString(Utils.PRODUCT_ID,"").toString()


    }


    override fun getLayout(): Int {
        return R.layout.fragment_pickput_daily_report
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
