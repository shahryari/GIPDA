package com.example.warehousemanagment.ui.fragment

import PickingDetailRow
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetBottomBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentDetailPickingBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.ui.adapter.PickingDetailAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetConfirmDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.PickingDetailListViewModel


class PickingDetailFragment() :
    BaseFragment<PickingDetailListViewModel, FragmentDetailPickingBinding>() {
    lateinit var pickingId: String
    var chronometer: CountDownTimer? = null

    var sortType = Utils.ProductTitle
    var receivePage = Utils.PAGE_START
    var receiveOrder = Utils.ASC_ORDER
    var lastReceivingPosition = 0

    lateinit var sumQuantity:String
    lateinit var pickingLocationCode:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickingId = arguments?.
            getString(Utils.PICKING_ID, "").toString()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPickingDetail()
        b.swipeLayout.setOnRefreshListener()
        {
            refreshReceiveDetail()
        }
        obserReceiveDetailData()
        observePickingCount()

        clearEdi(b.mainToolbar.clearImg, b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener()
        {
            showFilterSheetDialog()
        }


    }

    private fun obserReceiveDetailData() {
        viewModel.getPickingList()
            .observe(requireActivity(), object : Observer<List<PickingDetailRow>> {
                override fun onChanged(list: List<PickingDetailRow>) {
                    if (view != null && isAdded) {
                        b.swipeLayout.isRefreshing = false
                        lastReceivingPosition = list.size - 1
                        setDetailAdapter(list)

                    }
                }
            })
    }

    private fun startTimerForReceiveingData()
    {
        chronometer?.cancel()
        chronometer = object : CountDownTimer(Utils.DELAY_SERIAL, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                refreshReceiveDetail()
            }
        }.start()
    }

    private fun refreshReceiveDetail() {
        disposeRequest()
        receivePage = Utils.PAGE_START
        viewModel.clearPickingList()
        setPickingDetail()
    }

    private fun setPickingDetail()
    {
        viewModel.setPickingDetailList(
            locationCode = pickingLocationCode,
            pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi),
            receivePage,
            Utils.ROWS,
            sortType,
            receiveOrder,
            pref.getTokenGlcTest(),
            b.progressBar
            ,b.swipeLayout
        )
    }

    private fun observePickingCount()
    {
        viewModel.getPickCount().observe(viewLifecycleOwner, object : Observer<Int> {
            override fun onChanged(it: Int) {
                setBelowCount(
                    requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.productsToReceive)
                )

            }
        })
    }

    private fun showFilterSheetDialog() {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener {
            override fun initView(binding: DialogSheetSortFilterBinding) {
                binding.relProdcutCode.visibility = View.GONE
                binding.ownerCode.text = getString(R.string.createdOn)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding) {
                when (sortType) {
                    Utils.LOCATION_CODE_SORT -> checkTick(binding.locationCodeImg, binding)
                    Utils.ProductTitle -> checkTick(binding.productTitleImg, binding)
                    Utils.CREATED_ON -> checkTick(binding.ownerCOdeImg, binding)

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
                    refreshReceiveDetail()
                }
            }

            override fun onDescClick() {
                if (receiveOrder != Utils.DESC_ORDER) {
                    receiveOrder = Utils.DESC_ORDER
                    refreshReceiveDetail()
                }

            }

            override fun onLocationCodeClick() {
                if (sortType != Utils.LOCATION_CODE_SORT) {
                    sortType = Utils.LOCATION_CODE_SORT
                    refreshReceiveDetail()
                }
            }

            override fun onProductCodeClick() {


            }

            override fun onProductTitleClick() {
                if (sortType != Utils.ProductTitle) {
                    sortType = Utils.ProductTitle
                    refreshReceiveDetail()
                }

            }

            override fun onOwnerClick() {
                if (sortType != Utils.CREATED_ON) {
                    sortType = Utils.CREATED_ON
                    refreshReceiveDetail()
                }

            }

            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }




    private fun setDetailAdapter(serialList: List<PickingDetailRow>)
    {
        if (lastReceivingPosition - Utils.ROWS <= 0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastReceivingPosition - Utils.ROWS)

        val adapter = PickingDetailAdapter(serialList, requireActivity(),
            object : PickingDetailAdapter.OnCallBackListener {
                override fun onItemClick(model: PickingDetailRow) {
                    showConfirmSheet(model)
                }


                override fun reachToEnd(position: Int)
                {
                    receivePage = receivePage + 1
                    setPickingDetail()
                }
            })
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForReceiveingData()
        }

    }

    private fun completePicking(
        model: PickingDetailRow,
        progressBar: ProgressBar,
        mySheetAlertDialog: SheetConfirmDialog?,
    ) {
        viewModel.completePicking(
            baseUrl = pref.getDomain(),
            productCode = model.productCode,
            locations = model.locations,
            sumQuantity=model.sumQuantity,
            gtinCode = model.gTINCode,
            cookie = pref.getTokenGlcTest(),
            progress = progressBar,
            callBack={
                mySheetAlertDialog?.dismiss()
                refreshReceiveDetail()
            }
        )
    }



    private fun disposeRequest() {
        viewModel.dispose()
    }



    private fun showConfirmSheet(model: PickingDetailRow)
    {
        var mySheetAlertDialog: SheetConfirmDialog? = null
        mySheetAlertDialog = SheetConfirmDialog(
            title = getString(R.string.pickingFinish),
            msg = getString(R.string.confirm_picking_with_producTtitle)
                +model.productTitle + getString(R.string.and_productcode)+model.productCode,
            object : SheetConfirmDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, expectedCount: String) {
                    completePicking(
                        model=model,
                        progressBar=progress,
                        mySheetAlertDialog
                    )
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {

                }

                override fun init(binding: DialogSheetBottomBinding) {

                }

            })
        mySheetAlertDialog.show(getParentFragmentManager(), "")
    }



    override fun onDestroy() {
        super.onDestroy()
        if (view != null) {
            viewModel.getPickingList().removeObservers(viewLifecycleOwner)
            disposeRequest()
        }

    }

    override fun init() {
        setToolbarTitle(requireActivity(), getString(R.string.pickingDetail))

        setToolbarBackground(b.mainToolbar.rel2, requireActivity())

        sumQuantity=arguments?.getString(Utils.sumQuantity).toString()
        pickingLocationCode=arguments?.getString(Utils.locationCode).toString()

        b.pickingItem.locationCode.text=pickingLocationCode
        b.pickingItem.quantity.text=sumQuantity


    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }


    override fun getLayout(): Int {
        return R.layout.fragment_detail_picking
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}

