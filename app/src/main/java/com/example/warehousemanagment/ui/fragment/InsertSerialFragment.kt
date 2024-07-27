package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.DialogSheetInvListBinding
import com.example.warehousemanagment.databinding.FragmentInsertSerialBinding
import com.example.warehousemanagment.databinding.PatternWarehouseBinding
import com.example.warehousemanagment.model.classes.checkEnterKey
import com.example.warehousemanagment.model.classes.checkIfIsValidChars
import com.example.warehousemanagment.model.classes.clearEdi
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
import com.example.warehousemanagment.model.models.insert_serial.InsertedSerialModel
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.insert_serial.WarehouseModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.ui.adapter.InsertedSerialAdapter
import com.example.warehousemanagment.ui.adapter.OwnerAdapter
import com.example.warehousemanagment.ui.adapter.ProductAdapter
import com.example.warehousemanagment.ui.adapter.WareHouseAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetInvDialog
import com.example.warehousemanagment.ui.dialog.SheetPalletDialog
import com.example.warehousemanagment.viewmodel.InsertSerialViewModel


class InsertSerialFragment : BaseFragment<InsertSerialViewModel, FragmentInsertSerialBinding>()
{
    private var wareHouseId:String ?=null
    private var ownerId:String?=null
    var invTypeId=Utils.MINUS_ONE
    var productId:String ?=null
    lateinit var watcher:TextWatcher

     override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initClearImgEdi()

        b.wareHouse.tv.setOnClickListener()
        {
            viewModel.setWareHouse(pref.getDomain(),pref.getTokenGlcTest())
            showWareHouseSheet(b.wareHouse.tv)
        }
        b.owner.tv.setOnClickListener()
        {
            viewModel.setOwnerList(pref.getDomain(),pref.getTokenGlcTest())
            showOwnerSheet(b.owner.tv)
        }
        setWatcherForProducts()


        checkEnterKey(b.serials.quantity,)
        {
            checkAndInsertSerial()
        }

        b.add.setOnClickListener()
        {
            checkAndInsertSerial()
        }


        b.inventoryType.tv.setOnClickListener()
        {
            showInvSheet()
        }
        b.printTv.tv.setOnClickListener()
        {
            viewModel.insertAllSerial(pref.getDomain(),b.progress,pref.getTokenGlcTest(),)
            {
                toast(getString(R.string.successfullConfirmed),requireActivity())
            }
        }


        observeSerialList()
    }

    private fun checkAndInsertSerial()
    {
        if (lenEdi(b.serials.quantity) != 0 && lenEdi(b.productTitle.quantity) != 0
            && b.owner.tv.text.length != 0 && b.wareHouse.tv.text.length != 0
        )
        {

            hideKeyboard(requireActivity())
            if (checkIfIsValidChars(
                    textEdi(b.serials.quantity),
                    pref.getUnValidChars(), pref.getSerialLenMax(),
                    pref.getSerialLenMin(), requireActivity()
                ) == true && invTypeId!= Utils.MINUS_ONE
            )
            {
                insertSerial()
            }
        } else toast(getString(R.string.fillAllFields), requireActivity())
    }

    private fun insertSerial()
    {
        viewModel.insertSerial(
            productId.toString(),
            textEdi(b.serials.quantity),
            wareHouseId.toString(),
            ownerId.toString(),
            invTypeId
        )

        clearFields()

    }

    private fun clearFields()
    {
        b.serials.quantity.setText("")
    }

    private fun showInvSheet()
    {
        var sheet: SheetInvDialog? = null
        sheet = SheetInvDialog(viewModel.invList(requireActivity()),
            object : SheetInvDialog.OnClickListener {
            override fun onCloseClick() {
                sheet?.dismiss()
            }

            override fun onInvClick(model: CatalogModel)
            {
                b.inventoryType.tv.text = model.title
                invTypeId=model.valueField
                sheet?.dismiss()

            }

            override fun init(binding: DialogSheetInvListBinding) {

            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun setWatcherForProducts()
    {
        watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                startTimerForGettingData()
                {
                    if (lenEdi(b.productTitle.quantity) != 0)
                    {
                        viewModel.setProductList(
                            pref.getDomain(),
                            textEdi(b.productTitle.quantity), pref.getTokenGlcTest()
                        )
                        getProductSheetData(
                            b.productTitle.quantity,
                            getString(R.string.productTitle)
                        )
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
        b.productTitle.quantity.addTextChangedListener(watcher)
    }

    private fun initClearImgEdi() {
        clearEdi(b.productTitle.clearImg, b.productTitle.quantity)
        clearEdi(b.serials.clearImg, b.serials.quantity)
        clearEdi(b.clearImg, b.searchEdi)
    }


    private fun observeSerialList()
    {
        viewModel.getSerialList().observe(viewLifecycleOwner, object
            : Observer<List<InsertedSerialModel>>
        {
            override fun onChanged(it: List<InsertedSerialModel>)
            {
                showSerialList(it)
                setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                    it.size, getString(R.string.serialsToInsert))

                b.serialsCount.text= getBuiltString(getString(R.string.tools_scannedItems),
                    it.size.toString())
            }

        })
    }

    private fun showSerialList(list: List<InsertedSerialModel>)
    {
        val adapter = InsertedSerialAdapter(
            list, requireActivity(),
            object : InsertedSerialAdapter.OnCallBackListener
            {
                override fun onDeleteSerial(model: InsertedSerialModel)
                {
                    deleteSerial(model)
                }
            })
        b.rv.adapter = adapter

        adapter.setFilter(search(textEdi(b.searchEdi),list,SearchFields.SerialNumber))
        b.searchEdi.doAfterTextChanged()
        {
            adapter.setFilter(search(textEdi(b.searchEdi),list,
                SearchFields.SerialNumber,SearchFields.ProductId,SearchFields.WarehouseId,
                SearchFields.OwnerInfoId))
        }
    }

    private fun deleteSerial(model: InsertedSerialModel)
    {
        val sb=StringBuilder()
        sb.append(getString(R.string.are_you_sure_for_delete))
        sb.append(model.serialNumber)
        sb.append(getString(R.string.are_you_sure_for_delete2))

        showDeleteSheetDialog(getString(R.string.serial_scan_confirm),sb.toString(),model)



    }
    private fun showDeleteSheetDialog(
        title: String,
        desc: String,
        insertedSerialModel: InsertedSerialModel
    )
    {
        var mySheetAlertDialog: SheetAlertDialog?=null
        mySheetAlertDialog= SheetAlertDialog(title,desc
            ,object : SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {
                    viewModel.removeSerial(insertedSerialModel)
                    mySheetAlertDialog?.dismiss()
                    toast(getString(R.string.successDeleted),requireActivity())
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }
                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {

                }

            })
        mySheetAlertDialog.show(this.getParentFragmentManager(), "")

    }

    private fun showWareHouseSheet(tv: TextView)
    {
        var sheet: SheetPalletDialog? = null
        sheet = SheetPalletDialog(getString(R.string.warehouse),
            object : SheetPalletDialog.OnClickListener
            {
                override fun onCloseClick() { sheet?.dismiss() }

                override fun initData(binding: DialogSheetDestinyLocationBinding)
                {
                    observeWareHouse(sheet, tv, binding.rv,binding.serialsCount)
                }

            })
        sheet.show(this.getParentFragmentManager(),"")
    }
    private fun getProductSheetData(tv: TextView, title: String)
    {
        var productSheet:SheetPalletDialog?=null
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
                                tv.removeTextChangedListener(watcher)
                                productId=model.productId
                                tv.text=model.productTitle
                                tv.addTextChangedListener(watcher)

                            }

                        })
                    rv.adapter = adapter

                }

            })
    }
    private fun observeWareHouse(
        sheet: SheetPalletDialog?,
        tv: TextView,
        rv: RecyclerView,
        arrCounts: TextView
    )
    {
        viewModel.getWareHouse().observe(viewLifecycleOwner,
            object : Observer<List<WarehouseModel>> {
                override fun onChanged(it: List<WarehouseModel>)
                {
                    arrCounts.text= getBuiltString(getString(R.string.tools_scannedItems),
                        " ",it.size.toString())
                    val adapter = WareHouseAdapter(it, requireActivity(),
                        object : WareHouseAdapter.OnCallBackListener {
                            override fun onClick(model: WarehouseModel) {
                                sheet?.dismiss()
                                wareHouseId = model.warehouseID
                                tv.text = model.warehouseName

                            }

                        })
                    rv.adapter = adapter

                }

            })
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
                override fun onChanged(list: List<OwnerModel>) {
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
            })
    }




    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        if(view!=null){

        }

    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.insertSerial))
        b.wareHouse.tv.setHint(getString(R.string.warehouse))
        b.productTitle.quantity.setHint(getString(R.string.product_title))
        b.inventoryType.tv.setHint(getString(R.string.InventoryType))
        b.owner.tv.setHint(getString(R.string.owner))
        b.serials.quantity.setHint(getString(R.string.serials))
        b.printTv.tv.text=getString(R.string.submit)

        val sb= getBuiltString(getString(R.string.tools_scannedItems)," ",getString(R.string.Zero))
        b.serialsCount.setText(sb)
    }




    override fun getLayout(): Int {
         return R.layout.fragment_insert_serial
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
