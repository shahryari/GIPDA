package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSerialBinding
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.DialogSheetInvListBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentPickputDailyReportBinding
import com.example.warehousemanagment.databinding.PatternWarehouseBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.chronometer
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.createAlertDialog
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideKeyboard
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.search
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.report_inventory.report_location.LocationInventoryRows
import com.example.warehousemanagment.ui.adapter.LocationInventoryReportAdapter
import com.example.warehousemanagment.ui.adapter.LocationProductSerialAdapter
import com.example.warehousemanagment.ui.adapter.OwnerAdapter
import com.example.warehousemanagment.ui.adapter.ProductAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetInvDialog
import com.example.warehousemanagment.ui.dialog.SheetPalletDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.LocatoinInventoryReportViewModel


class LocationInventoryReportFragment :
    BaseFragment<LocatoinInventoryReportViewModel, FragmentPickputDailyReportBinding>()
{
    var sortType:String=Utils.LOCATION_CODE_SORT
    var orderType:String=Utils.ASC_ORDER
    var lastPosition=0
    var page=Utils.PAGE_START
    var serialPage = Utils.PAGE_START
    var adapter:LocationInventoryReportAdapter ?=null
    var ownerId=""
    var invTypeId:Int ?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        clearEdi(b.locationClearImg,b.locationCode)
        clearEdi(b.productTitleClearImg,b.productTitleEdi)
        clearEdi(b.ownerCodeImg,b.ownerCode)
        clearEdi(b.productCodeImg,b.productCode)
        clearEdi(b.invTypeImg,b.invTypeId)


        b.invTypeId.doAfterTextChanged {
            if (lenEdi(b.invTypeId)==0)
                invTypeId=null
        }
        b.ownerCode.doAfterTextChanged {
            if (lenEdi(b.ownerCode)==0)
                ownerId=""
        }


        b.swipeLayout.setOnRefreshListener()
        {
            if (checkAreEmptyFields(b.locationCode,b.productTitleEdi,
                    b.ownerCode,b.productCode,b.invTypeId)
            )
            {
                b.swipeLayout.isRefreshing=false
                reset()
            }else refresh()
        }


        b.relSetting.setOnClickListener()
        {
            showFilterSheet()
        }
        observeReport()
        observeLocationCount()


        b.relFilter.setOnClickListener()
        {
            if (checkAreEmptyFields(b.locationCode,b.productTitleEdi,
                    b.ownerCode,b.productCode,b.invTypeId)
            ){
                toast( getString(R.string.atLeast1Item),requireActivity())
            }else{
                refresh()
            }
        }



        b.productTitleEdi.doAfterTextChanged()
        {
            startTimerForGettingData()
            {
                if (lenEdi(b.productTitleEdi)!=0)
                {
                    viewModel.setProductList(
                        pref.getDomain(),textEdi(b.productTitleEdi)
                        ,pref.getTokenGlcTest())
                    getProductSheetData(b.productTitleEdi,getString(R.string.productTitle))
                }
            }
        }
        b.ownerCode.setOnClickListener()
        {
            viewModel.setOwnerList(
                pref.getDomain(),pref.getTokenGlcTest())
            showOwnerSheet(b.ownerCode)
        }
        b.invTypeId.setOnClickListener {
            showInvSheet()
        }

        b.locationCode.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
               refresh()
                true
            }else false

        }


        b.locationCode.requestFocus()

    }

    private fun showOwnerSheet(ownerCode: TextView)
    {
        var sheet: SheetPalletDialog? = null
        sheet = SheetPalletDialog(getString(R.string.warehouse),
            object : SheetPalletDialog.OnClickListener
            {
                override fun onCloseClick() { sheet?.dismiss() }

                override fun initData(binding: DialogSheetDestinyLocationBinding)
                {
                    binding.title.text=getString(R.string.ownerCode)
                    observeOwner(sheet, ownerCode, binding.rv,binding.serialsCount,binding)
                }

            })
        sheet.show(this.getParentFragmentManager(),"")

    }
    private fun observeOwner(
        sheet: SheetPalletDialog?,
        tv: TextView,
        rv: RecyclerView,
        arrCounts: TextView,
        binding: DialogSheetDestinyLocationBinding
    )
    {
        viewModel.getOwners().observe(viewLifecycleOwner
        ) { list ->
            arrCounts.text = getBuiltString(
                getString(R.string.tools_scannedItems),
                " ", list.size.toString()
            )
            val adapter = OwnerAdapter(list, requireActivity(),
                object : OwnerAdapter.OnCallBackListener {
                    override fun onClick(model: OwnerModel) {
                        sheet?.dismiss()
                        ownerId = model.ownerInfoID
                        tv.text = model.ownerCode

                    }

                    override fun init(binding: PatternWarehouseBinding) {

                    }

                })
            rv.adapter = adapter


            clearEdi(binding.clearImg, binding.searchEdi)
            binding.searchEdi.doAfterTextChanged {
                adapter.setFilter(
                    search(
                        textEdi(binding.searchEdi), list,
                        SearchFields.OwnerCode,
                        SearchFields.OwnerInfoFullName
                    )
                )
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
                    clearEdi(binding.clearImg,binding.searchEdi)
                    observeProduct(productSheet, tv, binding.rv,binding.serialsCount,binding.searchEdi)
                }
            })
        productSheet.show(this.getParentFragmentManager(),"")
    }

    private fun observeProduct(
        sheet: SheetPalletDialog?,
        tv: TextView,
        rv: RecyclerView,
        arrCounts: TextView,
        searchEdi: EditText
    )
    {
        viewModel.getProductsList().observe(viewLifecycleOwner
        ) { list ->
            arrCounts.text =
                getBuiltString(getString(R.string.tools_scannedItems), " ", list.size.toString())
            val adapter = ProductAdapter(list, requireActivity(),
                object : ProductAdapter.OnCallBackListener {
                    override fun onClick(model: ProductModel) {
                        sheet?.dismiss()
                        tv.text = model.productTitle
                        chronometer?.cancel()
                    }

                })
            rv.adapter = adapter

            searchEdi.doAfterTextChanged {
                adapter.setFilter(
                    search(
                        textEdi(searchEdi), list,
                        SearchFields.ProductCode,
                        SearchFields.ProductTitle,
                        SearchFields.OwnerCode
                    )
                )


            }
        }
    }


    private fun refresh() {
        reset()
        setReportInventory()
    }

    private fun reset() {
        hideKeyboard(requireActivity())
        viewModel.dispose()
        viewModel.clearReportList()
        page = Utils.PAGE_START

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
                if (orderType!=Utils.ASC_ORDER)
                {
                    orderType = Utils.ASC_ORDER
                    refresh()
                }

            }

            override fun onDescClick() {
                if (orderType!=Utils.DESC_ORDER)
                {
                    orderType = Utils.DESC_ORDER
                    refresh()
                }

            }

            override fun onLocationCodeClick() {
                if (sortType!=Utils.LOCATION_CODE_SORT)
                {
                    sortType = Utils.LOCATION_CODE_SORT
                    refresh()
                }

            }

            override fun onProductCodeClick() {
                if (sortType!=Utils.PRODUCT_CODE_SORT)
                {
                    sortType = Utils.PRODUCT_CODE_SORT
                    refresh()
                }

            }

            override fun onProductTitleClick() {
                if (sortType!=Utils.PRODUCT_TITLE_SORT)
                {
                    sortType = Utils.PRODUCT_TITLE_SORT
                    refresh()
                }

            }

            override fun onOwnerClick() {
                if (sortType!=Utils.OWNER_SORT)
                {
                    sortType = Utils.OWNER_SORT
                    refresh()
                }

            }

            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun showInvSheet()
    {

        var sheet: SheetInvDialog? = null
        sheet = SheetInvDialog(viewModel.invList(requireActivity()), object : SheetInvDialog.OnClickListener {
            override fun onCloseClick() {
                sheet?.dismiss()
            }

            override fun onInvClick(model: CatalogModel) {
                b.invTypeId.setText(model.title)
                invTypeId=model.valueField
                sheet?.dismiss()
            }

            override fun init(binding: DialogSheetInvListBinding) {

            }
        })
        sheet.show(this.getParentFragmentManager(), "")
    }

    fun checkAreEmptyFields(edi1:EditText, edi2: EditText, edi3: EditText, edi4:EditText, edi5:EditText): Boolean {
        if (lenEdi(edi1)+ lenEdi(edi2)+ lenEdi(edi3)+ lenEdi(edi4)+ lenEdi(edi5)==0){
            return true
        }
        return false
    }
    fun receiveReportInventory(
        invTypeId: Int?,
        productCode:String,
        locationCode: String,
        productTitle: String,
        ownerCode: String,
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
            pref.getDomain(),invTypeId,productCode,locationCode,productTitle,
            ownerCode,page,rows,sort,order,cookie, progressBar,swipeLayout)
    }
    fun observeReport()
    {
        viewModel.getReportLocationList().
        observe(viewLifecycleOwner
        ) { it ->
            lastPosition = it.size - 1
            showLocationList(it)
        }
    }
    fun observeLocationCount()
    {
        viewModel.getLocationCount().
        observe(viewLifecycleOwner) { it ->
            setBelowCount(
                requireActivity(), getString(R.string.tools_you_have),
                it, getString(R.string.location_inventory)
            )
        }
    }



    private fun setSerials(model: LocationInventoryRows,progressBar: ProgressBar) {
        viewModel.setSerialList(
            pref.getDomain(),
            model.locationProductID,
            pref.getTokenGlcTest(),
            serialPage,
            progressBar
        )
    }

    private fun observeSerials(binding: DialogSerialBinding,model: LocationInventoryRows) {
        viewModel.getLocationSerials().observe(viewLifecycleOwner) {list->
            val adapter = LocationProductSerialAdapter(
                requireContext(),
                list,
                onReachEnd = {
                    serialPage = serialPage + 1
                    setSerials(model,binding.progress)
                }
            ) {binding,_->
                binding.delete.visibility = View.GONE
                binding.excel.visibility = View.GONE
            }
            binding.rv.adapter = adapter
        }
    }

    private fun observeSerialCount(binding: DialogSerialBinding) {
        viewModel.getSerialCount().observe(viewLifecycleOwner){
            binding.serialsCount.text = it.toString()
        }
    }

    private fun showLocationList(list:List<LocationInventoryRows>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition-Utils.ROWS)
       adapter = LocationInventoryReportAdapter(list, requireActivity(),
            object : LocationInventoryReportAdapter.OnCallBackListener
        {
            override fun onClick(model: LocationInventoryRows)
            {
                val dialogBinding = DialogSerialBinding.inflate(LayoutInflater.from(requireContext()))
                val dialog = createAlertDialog(dialogBinding,R.drawable.shape_background_rect_border_gray_solid_white,requireContext())

                dialogBinding.lineTotal.visibility = View.VISIBLE
                serialPage = 1
                viewModel.clearSerials()
                setSerials(model,dialogBinding.progress)
                observeSerials(dialogBinding,model)
                observeSerialCount(dialogBinding)

                dialogBinding.rel4.confirm.visibility = View.GONE

                dialogBinding.closeImg.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.rel4.cansel.setOnClickListener {
                    dialog.dismiss()
                }




                dialog.show()

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
            invTypeId,
            textEdi(b.productCode),
            textEdi(b.locationCode), textEdi(b.productTitleEdi),
            ownerId, page, Utils.ROWS, sortType,
            orderType, pref.getTokenGlcTest(), b.progressBar,
            b.swipeLayout
        )
    }


    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){

        }

    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.location_inventory))
        b.rel1.visibility=View.GONE
        b.rel5.visibility=View.VISIBLE
        b.rel6.visibility=View.VISIBLE

    }





    override fun getLayout(): Int {
         return R.layout.fragment_pickput_daily_report
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
