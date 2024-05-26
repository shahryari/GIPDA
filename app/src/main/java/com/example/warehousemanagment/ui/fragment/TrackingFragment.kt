package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.kotlin_wallet.ui.base.BaseFragment
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.FragmentTrackingBinding
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.tracking.GetSerialInfoModel
import com.example.warehousemanagment.ui.adapter.ProductAdapter
import com.example.warehousemanagment.ui.adapter.TrackingSerialAdapter
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetPalletDialog
import com.example.warehousemanagment.viewmodel.TrackingViewModel


class TrackingFragment : BaseFragment<TrackingViewModel,FragmentTrackingBinding>()
{

    private var productCode:String=""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        clearEdi(b.relProductTitle.clearImg,b.relProductTitle.quantity)
        clearEdi(b.relSerials.clearImg,b.relSerials.quantity)
        clearEdi(b.clearSearchImg,b.searchEdi)


        observeSerialList()


        b.relProductTitle.quantity.doAfterTextChanged ()
        {
            productCode=""
            checkProductsList()
        }

        b.add.setOnClickListener()
        {
            addTrackingSerial()

        }

        b.relSerials.quantity.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addTrackingSerial()
                return@OnEditorActionListener true
            }
            false
        })



        b.printTv.tv.setOnClickListener()
        {
            hideKeyboard(requireActivity())
            viewModel.labelling(
                pref.getDomain(),b.progress,pref.getTokenGlcTest())
            {
                toast(getString(R.string.successfullConfirmed),requireActivity())
            }
        }


        b.relSerials.quantity.requestFocus()
    }

    private fun addTrackingSerial() {
        hideKeyboard(requireActivity())
        if (lenEdi(b.relProductTitle.quantity) != 0 && lenEdi(b.relSerials.quantity) != 0) {
            viewModel.setSerialInfo(
                pref.getDomain(),
                productCode,
                textEdi(b.relSerials.quantity),
                pref.getTokenGlcTest(),
                b.progress
            )
        } else toast(getString(R.string.fillAllFields), requireActivity())
    }

    private fun observeSerialList()
    {
        viewModel.getSerialsList()
            .observe(viewLifecycleOwner, object : Observer<List<GetSerialInfoModel>> {
                override fun onChanged(it: List<GetSerialInfoModel>) {
                    showSerials(it)

                    b.serialsCount.setText(
                        getBuiltString(
                            getString(R.string.serialsItems),
                            it.size.toString()
                        )
                    )
                }

            })
    }

    private fun checkProductsList()
    {
        startTimerForGettingData()
        {
            if (lenEdi(b.relProductTitle.quantity) != 0) {
                viewModel.setTrackingProducts(
                    pref.getDomain(),
                    textEdi(b.relProductTitle.quantity), pref.getTokenGlcTest()
                )
                getProductSheetData(b.relProductTitle.quantity, getString(R.string.productTitle))
            }
        }
    }

    private fun showSerials(list:List<GetSerialInfoModel>)
    {
        val adapter=TrackingSerialAdapter(list,requireActivity()
            ,object :TrackingSerialAdapter.OnCallBackListener
            {
            override fun onDelete(model: GetSerialInfoModel)
            {

                ShowDeleteDialog(model)


            }

            override fun imgVisible(img: ImageView) {
                img.visibility=View.INVISIBLE
            }

        })
        b.rv.adapter=adapter

        b.searchEdi.doAfterTextChanged {
            adapter.setFilter(search(textEdi(b.searchEdi),
                list,SearchFields.SerialNumber, ))
        }

    }

    private fun ShowDeleteDialog(model: GetSerialInfoModel) {
        val sb = StringBuilder()
        sb.append(getString(R.string.are_you_sure_for_delete))
        sb.append(model.serialNumber)
        sb.append(getString(R.string.are_you_sure_for_delete2))

        var mySheetAlertDialog: SheetAlertDialog? = null
        mySheetAlertDialog = SheetAlertDialog(getString(R.string.removeSerial),
            sb.toString(),
            object : SheetAlertDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    viewModel.removeSerial(model)
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {

                }

            })
        mySheetAlertDialog.show(getParentFragmentManager(), "")
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
                    observeProduct(productSheet, tv, binding.rv,binding.serialsCount,binding.searchEdi,binding.clearImg)
                }
            })
        productSheet.show(this.getParentFragmentManager(),"")
    }

    private fun observeProduct(
        sheet: SheetPalletDialog?,
        tv: TextView,
        rv: RecyclerView,
        arrCounts: TextView,
        searchEdi: EditText,
        clearImg: ImageView
    )
    {
        clearEdi(clearImg,searchEdi)
        viewModel.getProductList().observe(viewLifecycleOwner,
            object : Observer<List<ProductModel>>
            {
                override fun onChanged(list: List<ProductModel>)
                {
                    arrCounts.text= getBuiltString(getString(R.string.tools_scannedItems)," ",list.size.toString())
                    val adapter = ProductAdapter(list, requireActivity(),
                        object : ProductAdapter.OnCallBackListener
                        {
                            override fun onClick(model: ProductModel)
                            {
                                sheet?.dismiss()
                                tv.text=model.productTitle
                                productCode=model.productCode
                                chronometer?.cancel()//instead of using textWatcher
                            }

                        })
                    rv.adapter = adapter

                    searchEdi.doAfterTextChanged {
                        adapter.setFilter(search(textEdi(searchEdi),
                            list,SearchFields.ProductTitle,
                            SearchFields.ProductCode,
                            SearchFields.OwnerCode))
                    }




                }
            })


    }

    override fun onDestroy() {
        super.onDestroy()
        hideView(requireActivity(),R.id.summaryTv,View.VISIBLE)
    }


    override fun onResume() {
        super.onResume()

        hideShortCut(requireActivity())

        hideView(requireActivity(),R.id.summaryTv,View.GONE)
    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.trackingSerial))
        b.relProductTitle.quantity.setHint(getString(R.string.productTitle))
        b.relSerials.quantity.setHint(getString(R.string.serial))
        b.printTv.tv.setText(getString(R.string.submit))
    }




    override fun getLayout(): Int {
         return R.layout.fragment_tracking
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }






}
