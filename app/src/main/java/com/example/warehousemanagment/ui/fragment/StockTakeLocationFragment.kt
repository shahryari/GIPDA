package com.example.warehousemanagment.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogChangeProductBinding
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.DialogSheetInvListBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.DialogSheetTaskTypeBinding
import com.example.warehousemanagment.databinding.FragmentStockLocationBinding
import com.example.warehousemanagment.databinding.PatternStockTakingLocationBinding
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
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.stock.StockLocationRow
import com.example.warehousemanagment.model.models.stock.stock_take_location.StockTackingLocationRow
import com.example.warehousemanagment.ui.adapter.OwnerAdapter
import com.example.warehousemanagment.ui.adapter.ProductAdapter
import com.example.warehousemanagment.ui.adapter.StockLocationAdapter
import com.example.warehousemanagment.ui.adapter.StockTakingLocationAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetInvDialog
import com.example.warehousemanagment.ui.dialog.SheetPalletDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.ui.dialog.SheetStockLocationDialog
import com.example.warehousemanagment.viewmodel.StockTakeLocationViewModel

class StockTakeLocationFragment :
    BaseFragment<StockTakeLocationViewModel, FragmentStockLocationBinding>()
{

    private var ownerId:String=""
    var invTypeId=Utils.MINUS_ONE

    var sortType = Utils.LOCATION_CODE_SORT
    var stockPage = Utils.PAGE_START
    var receiveOrder = Utils.ASC_ORDER
    var lastPosition = 0
    var productCode:String=""

    lateinit var stockTurnId: String
    lateinit var stockTurnCode: String
    lateinit var stockTurnTitle: String
    var firstCountValue:String=""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.swipeLayout.setOnRefreshListener() {
            refresh()
        }

        refresh()

        observeStockLocationList()
        observeStockLocationCount()

        clearEdi(b.mainToolbar.clearImg, b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener() {
            showFilterSheetDialog()
        }

        b.mainToolbar.searchEdi.doAfterTextChanged() {
            startTimerForGettingData { refresh() }
        }

        b.insertLocationCount.setOnClickListener {
            showConfirmDialog(
                ifTypeIsInsertLocation = true,
            )

        }


    }


    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener {
            override fun initView(binding: DialogSheetSortFilterBinding) {
                binding.locationCode.text = getString(R.string.locationCode)
                binding.ownerCode.text = getString(R.string.ownerCode)
                binding.productCode.text = getString(R.string.product_code)
                binding.productTitle.text = getString(R.string.productTitle)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding) {
                when (sortType) {
                    Utils.LOCATION_CODE_SORT -> checkTick(binding.locationCodeImg, binding)
                    Utils.PRODUCT_TITLE_SORT -> checkTick(binding.productTitleImg, binding)
                    Utils.OWNER_SORT -> checkTick(binding.ownerCOdeImg, binding)
                    Utils.PRODUCT_TYPE_SORT -> checkTick(binding.productCodeImg, binding)

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
                if (sortType != Utils.LOCATION_CODE_SORT) {
                    sortType = Utils.LOCATION_CODE_SORT
                    refresh()
                }
            }

            override fun onProductCodeClick() {
                if (sortType != Utils.PRODUCT_TYPE_SORT) {
                    sortType = Utils.PRODUCT_TYPE_SORT
                    refresh()
                }

            }

            override fun onProductTitleClick() {
                if (sortType != Utils.PRODUCT_TITLE_SORT) {
                    sortType = Utils.PRODUCT_TITLE_SORT
                    refresh()
                }

            }

            override fun onOwnerClick() {
                if (sortType != Utils.OWNER_SORT) {
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

    private fun refresh() {
        viewModel.dispose()
        stockPage = Utils.PAGE_START
        viewModel.clearStockList()
        setStockTakeLocationData()
    }

    private fun observeStockLocationCount() {
        viewModel.getStockCount().observe(viewLifecycleOwner, object : Observer<Int> {
            override fun onChanged(it: Int) {

                setBelowCount(
                    requireActivity(),
                    getString(R.string.tools_you_have),
                    it,
                    getString(R.string.stockToTake)
                )
            }

        })

    }

    private fun observeStockLocationList() {
        viewModel.getStockTakeLocationList()
            .observe(viewLifecycleOwner, object : Observer<List<StockTackingLocationRow>> {
                override fun onChanged(it: List<StockTackingLocationRow>) {
                    if (view != null && isAdded) {
                        b.swipeLayout.isRefreshing = false
                        lastPosition = it.size - 1
                        showStockTakeLocationList(it)
                    }
                }
            })
    }

    private fun showStockTakeLocationList(list: List<StockTackingLocationRow>) {
        if (lastPosition - Utils.ROWS <= 0) b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition - Utils.ROWS)

        val adapter = StockTakingLocationAdapter(
            list,
            requireActivity(),
            object : StockTakingLocationAdapter.OnCallBackListener
            {
                override fun init(
                    stockBinding: PatternStockTakingLocationBinding,
                    model:StockTackingLocationRow,
                ) {
                    if(model.historyCount==1){
                        stockBinding.count.isEnabled=true
                        stockBinding.count2.isEnabled=false

                    }else if(model.historyCount==2){
                        stockBinding.count.isEnabled=false
                        stockBinding.count.setText(model.firstQuantity.toString())
                        stockBinding.count2.isEnabled=true

                        stockBinding.count2.setBackgroundTintList(
                            ColorStateList.valueOf(ContextCompat.
                            getColor(requireActivity(), R.color.red)))

                        stockBinding.count2
                            .setTextColor(ContextCompat
                                .getColor(requireActivity(), R.color.white))
                        stockBinding.count2
                            .setHintTextColor(ContextCompat
                                .getColor(requireActivity(), R.color.white))
                    }

                }

                override fun onChangeClick(model: StockTackingLocationRow) {
                    showConfirmDialog(model)
                }

                override fun onSaveClick(
                    model: StockTackingLocationRow,
                    countEdi: EditText,
                    countEdi2:EditText,
                    progressBar: ProgressBar
                )
                {
                    if(
                        ( lenEdi(countEdi)!=0 && countEdi.isEnabled)
                        || (lenEdi(countEdi2)!=0 && countEdi2.isEnabled)

                    )
                    {
                        hideKeyboard(requireActivity())
                        viewModel.stockTakingCount(
                            baseUrl = pref.getDomain(),
                            cookie = pref.getTokenGlcTest(),
                            stockTurnTeamLocationID = model.stockTurnTeamLocationID,
                            countQuantity =
                            if(!countEdi2.isEnabled && countEdi.isEnabled) textEdi(countEdi).toInt()
                            else // if(countEdi2.isEnabled && !countEdi2.isEnabled)
                                textEdi(countEdi2).toInt(),
                            progressBar = progressBar,
                            onCallBack = { isStockMessageEqualMinusOne->
                                refresh()
                            },
                            onError = {isErrorEqualMinusOne->
                            }
                        )
                    }else
                        toast(getString(R.string.fillCountEdi),requireActivity())

                }

                override fun reachToEnd(position: Int) {
                    stockPage = stockPage + 1
                    setStockTakeLocationData()
                }


            })
        b.rv.adapter = adapter


    }


    private fun showConfirmDialog(model: StockTackingLocationRow ? =null,
                                  ifTypeIsInsertLocation:Boolean=false)
    {
        val dialogBinding = DialogChangeProductBinding.inflate(
            LayoutInflater.from(requireActivity()), null
        )
        val dialog = createAlertDialog(
            dialogBinding,
            R.drawable.shape_background_rect_border_gray_solid_white,
            requireActivity()
        )

        dialog.setOnDismissListener {
            ownerId=""
        }

        if (ifTypeIsInsertLocation==true)
        {
            dialogBinding.locationCodeTitle.visibility=View.GONE
            dialogBinding.title1.visibility=View.GONE
            dialogBinding.dialogTitle.text=getString(R.string.newProduct)


        }else{
            dialogBinding.locationCodeTitle.text = model?.locationCode
            dialogBinding.rel1.visibility=View.GONE
        }




        dialogBinding.closeImg.setOnClickListener { dialog.dismiss() }
        dialogBinding.relConfirm.cansel.setOnClickListener { dialog.dismiss() }



        clearEdi(
            dialogBinding.clearImgProduct, dialogBinding.product
        )
        clearEdi(
            dialogBinding.clearImgQuanitity, dialogBinding.quantity
        )
        clearEdi(
            dialogBinding.clearImgLocation, dialogBinding.locationCode
        )
        clearEdi(
            dialogBinding.clearImgOwnerCode, dialogBinding.ownerCode
        ){
            ownerId = ""
        }
        clearEdi(
            dialogBinding.clearImgInvType, dialogBinding.invType
        )

//        dialogBinding.locationCode.requestFocus()

        dialogBinding.product.doAfterTextChanged()
        {
            startTimerForGettingData()
            {
                if (lenEdi(dialogBinding.product) != 0)
                {
                    viewModel.setProductList(
                        pref.getDomain(),
                        textEdi(dialogBinding.product),
                        pref.getTokenGlcTest()
                    )
                    getProductSheetData(
                        dialogBinding.product, getString(R.string.productTitle)
                    )
                }
            }
        }


        dialogBinding.locationCode.setOnClickListener()
        {
            viewModel.setLocationList(pref.getDomain(),stockTurnId,"", pref.getTokenGlcTest())
            showLocationSheet(dialogBinding.locationCode)
        }

        dialogBinding.ownerCode.setOnClickListener()
        {
            viewModel.setOwnerList(pref.getDomain(), pref.getTokenGlcTest())
            showOwnerSheet(dialogBinding.ownerCode)
        }

        dialogBinding.invType.setOnClickListener()
        {
            showInvSheet(dialogBinding)
        }

        dialogBinding.relConfirm.confirm.setOnClickListener()
        {
            if (ifTypeIsInsertLocation)
            {
                if (
                    lenEdi(dialogBinding.locationCode)!=0
                    &&
                    lenEdi(dialogBinding.product) != 0
                    &&
                    lenEdi(dialogBinding.quantity) != 0
                    &&
                    lenEdi(dialogBinding.invType) != 0
                ) {
                    viewModel.stockTakingLocationInsert(
                        baseUrl = pref.getDomain(),
                        cookie = pref.getTokenGlcTest(),
                        locationCode = textEdi(dialogBinding.locationCode),
                        countQuantity = textEdi(dialogBinding.quantity).toInt(),
                        goodSystemCode = productCode,
                        invTypeId = invTypeId,
                        ownerCode = ownerId,
                        stockTurnId=stockTurnId,
                        progressBar = dialogBinding.progressBar,
                        onCallBack = {
                            dialog.dismiss()
                            refresh()
                        }
                    )

                }
                else toast(getString(R.string.fillAllFields),requireActivity(),)
            }else
            {
                if (
                    lenEdi(dialogBinding.product) != 0
                    &&
                    lenEdi(dialogBinding.quantity) != 0
                    &&
                    lenEdi(dialogBinding.invType) != 0
                ) {
                    viewModel.stockTakingLocationInsert(
                        baseUrl = pref.getDomain(),
                        cookie = pref.getTokenGlcTest(),
                        countQuantity = textEdi(dialogBinding.quantity).toInt(),
                        goodSystemCode = productCode,
                        invTypeId = invTypeId,
                        locationCode =model?.locationCode,
                        stockTurnId=stockTurnId,
                        ownerCode = ownerId,
                        progressBar = dialogBinding.progressBar,
                        onCallBack = {
                            dialog.dismiss()
                            refresh()
                        }
                    )

                }
                else toast(getString(R.string.fillAllFields),requireActivity(),)
            }

        }
    }

    private fun showInvSheet(dialogBinding: DialogChangeProductBinding)
    {
        var sheet: SheetInvDialog? = null
        sheet = SheetInvDialog(viewModel.
        invList(requireActivity()), object : SheetInvDialog.OnClickListener {
            override fun onCloseClick() {
                sheet?.dismiss()
            }

            override fun onInvClick(model: CatalogModel)
            {
                dialogBinding.invType.setText(model.title)
                invTypeId=model.valueField
                sheet?.dismiss()

            }

            override fun init(binding: DialogSheetInvListBinding) {

            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun showLocationSheet(tv: TextView){
        var sheet: SheetStockLocationDialog? = null
        sheet = SheetStockLocationDialog(
            object : SheetStockLocationDialog.OnClickListener
            {
                override fun onCloseClick() { sheet?.dismiss() }
                override fun init(binding: DialogSheetTaskTypeBinding) {
                    binding.relSearch.visibility = View.VISIBLE
                    binding.searchEdi.doAfterTextChanged {
                        viewModel.setLocationList(
                            baseUrl = pref.getDomain(),
                            stockTurnId = stockTurnId,
                            keyword = it?.toString()?:"",
                            cookie = pref.getTokenGlcTest()
                        )
                    }
                    clearEdi(binding.clearImg,binding.searchEdi)
                    observeLocation(sheet,tv,binding.taskRv)
                }


            }
        )
        sheet.show(this.parentFragmentManager,"")
    }

    private fun observeLocation(
        sheet: SheetStockLocationDialog?,
        tv: TextView,
        rv: RecyclerView,
    ) {
        viewModel.getLocations().observe(viewLifecycleOwner
        ) { list ->
            val adapter = StockLocationAdapter(list,
                object : StockLocationAdapter.OnCallBackListener {
                    override fun onClick(location: StockLocationRow) {
                        tv.text = location.locationCode
                        sheet?.dismiss()
                    }
                }
            )
            rv.adapter = adapter
        }
    }
    private fun showOwnerSheet(tv: TextView)
    {
        var sheet: SheetPalletDialog? = null
        sheet = SheetPalletDialog(getString(R.string.warehouse),
            object : SheetPalletDialog.OnClickListener
            {
                override fun onCloseClick() { sheet?.dismiss() }

                override fun initData(binding: DialogSheetDestinyLocationBinding)
                {
                    binding.title.text=getString(R.string.ownerCode)
                    observeOwner(sheet, tv, binding.rv,binding.serialsCount,binding)
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
        var productSheet: SheetPalletDialog? = null
        productSheet = SheetPalletDialog(
            getString(R.string.warehouse),
            object : SheetPalletDialog.OnClickListener {
                override fun onCloseClick() {
                    productSheet?.dismiss()
                }

                override fun initData(binding: DialogSheetDestinyLocationBinding) {
                    binding.title.text = title
                    clearEdi(binding.clearImg, binding.searchEdi)
                    observeProduct(
                        productSheet, tv, binding.rv, binding.serialsCount, binding.searchEdi
                    )
                }
            })
        productSheet.show(this.getParentFragmentManager(), "")
    }

    private fun observeProduct(
        sheet: SheetPalletDialog?,
        tv: TextView,
        rv: RecyclerView,
        arrCounts: TextView,
        searchEdi: EditText
    ) {
        viewModel.getProductsList()
            .observe(viewLifecycleOwner, object : Observer<List<ProductModel>> {
                override fun onChanged(list: List<ProductModel>) {
                    arrCounts.text = getBuiltString(
                        getString(R.string.tools_scannedItems), " ", list.size.toString()
                    )
                    val adapter = ProductAdapter(
                        list,
                        requireActivity(),
                        object : ProductAdapter.OnCallBackListener {
                            override fun onClick(model: ProductModel) {
                                sheet?.dismiss()
                                tv.text = model.productTitle
                                productCode=model.productCode
                                chronometer?.cancel()
                            }

                        })
                    rv.adapter = adapter

                    searchEdi.doAfterTextChanged {
                        adapter.setFilter(
                            search(
                                textEdi(searchEdi),
                                list,
                                SearchFields.ProductTitle,
                                SearchFields.ProductCode,
                                SearchFields.OwnerCode
                            )
                        )
                    }

                }
            })
    }

    private fun setStockTakeLocationData() {
        viewModel.setStockTakeLocationList(
            pref.getDomain(),
            pref.getTokenGlcTest(),
            textEdi(b.mainToolbar.searchEdi),
            stockTurnId = stockTurnId,
            stockPage,
            Utils.ROWS,
            sortType,
            receiveOrder,
            b.progressBar,
            b.swipeLayout
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
        if (view != null) {
            viewModel.getStockTakeLocationList().removeObservers(viewLifecycleOwner)
        }

    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun init() {
        setToolbarTitle(requireActivity(), getString(R.string.stockTakingLocation))

        setToolbarBackground(b.mainToolbar.rel2, requireActivity())

        stockTurnCode = arguments?.get(Utils.StockTurnCode).toString()
        stockTurnTitle = arguments?.get(Utils.StockTurnTitle).toString()
        stockTurnId = arguments?.get(Utils.StockTakingID).toString()

        b.stockTakeItem.title.text = stockTurnTitle
        b.stockTakeItem.warhouseName.text = stockTurnCode

    }


    override fun getLayout(): Int {
        return R.layout.fragment_stock_location
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}