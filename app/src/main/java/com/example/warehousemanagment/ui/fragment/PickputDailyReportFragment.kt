package com.example.warehousemanagment.ui.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.DialogSheetTaskTypeBinding
import com.example.warehousemanagment.databinding.FragmentPickputDailyReportBinding
import com.example.warehousemanagment.databinding.PatternWarehouseBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.chronometer
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.getDimen
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
import com.example.warehousemanagment.model.models.report_inventory.pickput.PickAndPutRow
import com.example.warehousemanagment.ui.adapter.OwnerAdapter
import com.example.warehousemanagment.ui.adapter.PickPutAdapter
import com.example.warehousemanagment.ui.adapter.ProductAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetPalletDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.ui.dialog.SheetTaskDialog
import com.example.warehousemanagment.viewmodel.PickputDailyReportViewModel
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class PickputDailyReportFragment :
    BaseFragment<PickputDailyReportViewModel, FragmentPickputDailyReportBinding>()
{
    var subSettingHeight=0
    var sortType:String=Utils.LOCATION_CODE_SORT
    var orderType:String=Utils.ASC_ORDER
    var page=Utils.PAGE_START
    var lastPosition=0
    var ownerCode=""
    var taskTypeId:Int ?=null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        b.serialClearImg.visibility=View.GONE
        clearEdi(b.locationClearImg,b.locationCode)
        clearEdi(b.productTitleClearImg,b.productTitleEdi)
        clearEdi(b.ownerCodeImg,b.ownerCode)
        clearEdi(b.taskTypeImg,b.taskTypeId)

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }
        b.taskTypeId.doAfterTextChanged {
            if (lenEdi(b.taskTypeId)==0){
                taskTypeId=null
            }
        }


        b.relSetting.setOnClickListener()
        {
            showFilterSheet()
        }

        b.date.setOnClickListener {
            setDateFromCalander(b.serialEdi)
        }
        observeReport()
        observeArrayCount()


        b.relFilter.setOnClickListener()
        {

            if (lenEdi(b.serialEdi)==0){
                toast( getString(R.string.youHaveToDate),requireActivity())
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
        b.ownerCode.doAfterTextChanged {
            if (lenEdi(b.ownerCode)==0)
                ownerCode=""
        }




        b.taskTypeId.setOnClickListener {
            showTaskTypeSheet()
        }

        b.locationCode.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
            {
                refresh()
                 true
            }else false

        }
        b.locationCode.requestFocus()

    }

    private fun showTaskTypeSheet()
    {
        var sheet: SheetTaskDialog? = null

        sheet = SheetTaskDialog(viewModel.getTaskTypes(),
            object : SheetTaskDialog.OnClickListener {
                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun onItemClick(binding: DialogSheetTaskTypeBinding, model: CatalogModel) {
                    taskTypeId = model.valueField
                    b.taskTypeId.setText(model.title)
                    sheet?.dismiss()

                }



                override fun init(binding: DialogSheetTaskTypeBinding ) {
                    if (taskTypeId == null) {

                    }



                }
            })
        sheet.show(this.getParentFragmentManager(), "")
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
        viewModel.getOwners().observe(viewLifecycleOwner,
            object : Observer<List<OwnerModel>>
            {
                override fun onChanged(list: List<OwnerModel>)
                {
                    arrCounts.text= getBuiltString(getString(R.string.tools_scannedItems),
                        " ",list.size.toString())
                    val adapter = OwnerAdapter(list, requireActivity(),
                        object : OwnerAdapter.OnCallBackListener
                        {
                            override fun onClick(model: OwnerModel)
                            {
                                sheet?.dismiss()
                                ownerCode = model.ownerCode
                                tv.text = model.ownerCode

                            }

                            override fun init(binding: PatternWarehouseBinding)
                            {

                            }

                        })
                    rv.adapter = adapter

                    clearEdi(binding.clearImg,binding.searchEdi)
                    binding.searchEdi.doAfterTextChanged {
                        adapter.setFilter(search(textEdi(binding.searchEdi),list,
                            SearchFields.OwnerCode,
                            SearchFields.OwnerInfoFullName))
                    }

                }

            })
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
        viewModel.getProductsList().observe(viewLifecycleOwner,
            object : Observer<List<ProductModel>>
            {
                override fun onChanged(list: List<ProductModel>)
                {
                    arrCounts.text= getBuiltString(getString(R.string.tools_scannedItems),
                        " ",list.size.toString())
                    val adapter = ProductAdapter(list, requireActivity(),
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

                    searchEdi.doAfterTextChanged {
                        adapter.setFilter(search(
                            textEdi(searchEdi),list,
                            SearchFields.ProductTitle,SearchFields.ProductCode,SearchFields.OwnerCode
                        ))
                    }

                }
            })
    }


    private fun refresh()
    {
        hideKeyboard(requireActivity())
        viewModel.dispose()
        page = Utils.PAGE_START
        viewModel.clearReportList()
        setReportPickAndPut()
    }

    private fun setReportPickAndPut()
    {
        viewModel.reportPickAndPutInventory(
            pref.getDomain(), taskTypeId = taskTypeId,
            textEdi(b.locationCode), textEdi(b.serialEdi), textEdi(b.productTitleEdi),
            ownerCode,page, Utils.ROWS, sortType,
            orderType, pref.getTokenGlcTest(), b.progressBar,b.swipeLayout
        )
    }

    private fun showFilterSheet()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener {
            override fun initView(binding: DialogSheetSortFilterBinding) {

            }

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

            override fun onAscClick() {
                if (orderType != Utils.ASC_ORDER)
                {
                    orderType = Utils.ASC_ORDER
                    refresh()
                }

            }

            override fun onDescClick() {
                if (orderType != Utils.DESC_ORDER)
                {
                    orderType = Utils.DESC_ORDER
                    refresh()
                }

            }

            override fun onLocationCodeClick() {
                if ( sortType != Utils.LOCATION_CODE_SORT)
                {
                    sortType = Utils.LOCATION_CODE_SORT
                    refresh()
                }

            }

            override fun onProductCodeClick() {
                if ( sortType != Utils.PRODUCT_CODE_SORT)
                {
                    sortType = Utils.PRODUCT_CODE_SORT
                    refresh()
                }

            }

            override fun onProductTitleClick() {
                if ( sortType != Utils.PRODUCT_TITLE_SORT)
                {
                    sortType = Utils.PRODUCT_TITLE_SORT
                    refresh()
                }

            }

            override fun onOwnerClick() {
                if (sortType != Utils.OWNER_SORT)
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

    private fun getTodayDate():String{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = simpleDateFormat.format(Date())
        return currentDate.toString().replace("-","")

    }

    private fun setDateFromCalander(edi:EditText)
    {
        val picker = PersianDatePickerDialog(requireActivity())
            .setPositiveButtonString(getString(R.string.ok))
            .setNegativeButton(getString(R.string.neverMind))
            .setTodayButton("")
            .setTodayButtonVisible(true)
            .setMinYear(1390)
            .setTypeFace(Typeface.createFromAsset(requireActivity().assets,"fonts/medium.ttf"))
            .setMaxYear(1402)
            .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
            .setMaxDay(PersianDatePickerDialog.THIS_DAY)
            .setInitDate(PersianCalendar().get(Calendar.YEAR),
                        PersianCalendar().get(Calendar.MONTH),
                        PersianCalendar().get(Calendar.DAY_OF_MONTH))
            .setActionTextColor(Color.GRAY) //.setTypeFace()
            .setMaxMonth(12)
            .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
            .setShowInBottomSheet(true)
            .setListener(object : PersianPickerListener {
                override fun onDateSelected(model: PersianPickerDate)
                {
                    edi.setText(getBuiltString(model.persianYear.toString(),
                    "-",model.persianMonth.toString(),"-",model.persianDay.toString()))
                }

                override fun onDismissed() {}
            })

        picker.show()
    }

    private fun observeArrayCount()
    {
        viewModel.getArrayCount().observe(viewLifecycleOwner,object :Observer<Int>
        {
            override fun onChanged(it: Int)
            {
                setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.pickPutDailyReport))
            }

        })

    }

    fun observeReport()
    {

        viewModel.getReportLocationList().
        observe(viewLifecycleOwner,object : Observer<List<PickAndPutRow>>
        {
            override fun onChanged(it: List<PickAndPutRow>)
            {
                b.swipeLayout.isRefreshing=false
                lastPosition=it.size-1
                showLocationList(it)
            }
        })
    }


    private fun showLocationList(list:List<PickAndPutRow>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition-Utils.ROWS)

        val  adapter = PickPutAdapter(list, requireActivity(),
            object : PickPutAdapter.OnCallBackListener
        {
            override fun onClick()
            {

            }

            override fun reachToEnd(position: Int) {
                page=page+1
                setReportPickAndPut()
            }
        })
        b.rv.adapter = adapter


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
        setToolbarTitle(requireActivity(),getString(R.string.pickandput))
        b.date.setImageResource(R.drawable.ic_calendar_1_)
        b.serialEdi.hint=getString(R.string.date)
        b.serialEdi.isClickable=false
        b.serialEdi.isEnabled=false

        b.rel7.visibility=View.VISIBLE

        subSettingHeight=(getDimen(requireActivity()).height*Utils.SUB_SETTING_SORT).toInt()

    }




    override fun getLayout(): Int {
         return R.layout.fragment_pickput_daily_report
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
