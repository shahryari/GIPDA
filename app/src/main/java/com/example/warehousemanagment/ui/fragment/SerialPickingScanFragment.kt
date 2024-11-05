package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.FragmentSerialPickingScanBinding
import com.example.warehousemanagment.model.classes.checkEnterKey
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.picking.GetPickingSerialRow
import com.example.warehousemanagment.ui.adapter.PickingSerialAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.viewmodel.SerialPickingScanViewModel

class SerialPickingScanFragment : BaseFragment<SerialPickingScanViewModel,FragmentSerialPickingScanBinding>() {

    private lateinit var productCode: String
    private lateinit var locationCode: String
    private lateinit var shippingAddressDetailId: String
    private lateinit var shippingLocationId: String
    private lateinit var itemLocationId: String
    var chronometer: CountDownTimer? = null

    var sortType = Utils.ProductTitle
    var receivePage = Utils.PAGE_START
    var receiveOrder = Utils.ASC_ORDER
    var lastReceivingPosition = 0
    var sumQuantity = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }
        refresh()


        observeSerials()
        observeDetail()
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        clearEdi(b.clearImg,b.serialEdi)

        checkEnterKey(b.serialEdi){
            viewModel.scanPickingSerial(
                pref.getDomain(),
                locationCode,
                shippingAddressDetailId,
                shippingLocationId,
                itemLocationId,
                textEdi(b.serialEdi),
                pref.getTokenGlcTest(),
                requireContext(),
                b.progressBar,
            ){
                b.serialEdi.setText("")
                refresh()
            }
        }

        b.scanBarcode.setOnClickListener {
            viewModel.scanPickingSerial(
                pref.getDomain(),
                locationCode,
                shippingAddressDetailId,
                shippingLocationId,
                itemLocationId,
                textEdi(b.serialEdi),
                pref.getTokenGlcTest(),
                requireContext(),
                b.progressBar,
            ){
                b.serialEdi.setText("")
                refresh()
            }
        }

//        b.filterImg.img.setOnClickListener()
//        {
//            showFilterSheetDialog()
//        }
    }


    private fun refresh() {
        viewModel.dispose()
        receivePage = Utils.PAGE_START
        viewModel.clearPickingList()
        setSerials()
    }

    private fun setSerials() {
        viewModel.setSerialList(
            pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi),
            shippingAddressDetailId,
            itemLocationId,
            receivePage,
            sortType,
            receiveOrder,
            pref.getTokenGlcTest(),
            requireContext(),
            b.progressBar,
            b.swipeLayout
        )
    }

    private fun observeDetail() {
        viewModel.getDetail()
            .observe(viewLifecycleOwner){
                if (it!=null){
                    b.pickingItem.productTitle.text = it.productTitle
                    b.pickingItem.productCode.text = it.productCode
                    b.pickingItem.invTypeTitle.text = it.invTypeTitle
                    b.pickingItem.locationCode.text = it.locationCode
                    b.pickingItem.shippingArea.text = it.shippingNumber
                    b.pickingItem.quantity.text = it.quantity.toString()
                    b.pickingItem.scan.text = it.sumQuantity.toString()

                }
            }
    }

    private fun observeSerials() {
        viewModel.getSerialList().observe(this){list->
            if (view != null && isAdded) {
                b.swipeLayout.isRefreshing = false
                lastReceivingPosition = list.size - 1
                showSerialList(list)

            }
        }
    }
    private fun observeCount()
    {
//        viewModel.getSerialCount().observe(viewLifecycleOwner
//        ) { it ->
//            setBelowCount(
//                requireActivity(), getString(R.string.tools_you_have),
//                it, getString(R.string.productsToReceive)
//            )
//        }
    }

    private fun showSerialList(list: List<GetPickingSerialRow>){

        if (lastReceivingPosition - Utils.ROWS <= 0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastReceivingPosition - Utils.ROWS)

        val adapter = PickingSerialAdapter(
            list,requireContext()
        ) {
            receivePage += 1
            setSerials()
        }
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged {
            startTimerForReceiveingData()
        }


    }

    private fun startTimerForReceiveingData()
    {
        chronometer?.cancel()
        chronometer = object : CountDownTimer(Utils.DELAY_SERIAL, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                refresh()
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (view!=null){
            viewModel.getSerialList().removeObservers(this)
        }
    }


    override fun init() {
        setToolbarTitle(requireContext(),"Serial Picking Detail Scan")

        setToolbarBackground(b.mainToolbar.rel2, requireActivity())

        productCode = arguments?.getString(Utils.ProductCode)?:""
        locationCode = arguments?.getString(Utils.locationCode)?:""
        val shippingLocationCode = arguments?.getString("ShippingLocationCode")?:""
        val productTitle = arguments?.getString(Utils.ProductTitle)
        val invType = arguments?.getString(Utils.locationInventory)
        val shippingNumber = arguments?.getString(Utils.ShippingNumber)
        shippingAddressDetailId = arguments?.getString("ShippingAddressDetailID") ?:""
        shippingLocationId = arguments?.getString("ShippingLocationID") ?: ""
        itemLocationId = arguments?.getString("ItemLocationID") ?: ""
        val quantity = arguments?.getInt(Utils.Quantity)
        sumQuantity = arguments?.getInt(Utils.sumQuantity) ?: 0
        b.pickingItem.quantity.text = quantity.toString()
        b.pickingItem.scan.text = sumQuantity.toString()
        b.pickingItem.invTypeTitle.text = invType
        b.pickingItem.locationCode.text = locationCode
        b.pickingItem.shippingLocationCode.text = shippingLocationCode
        b.pickingItem.shippingArea.text =shippingNumber
        b.pickingItem.productCode.text = productCode
        b.pickingItem.productTitle.text = productTitle
    }

    override fun getLayout(): Int {
        return R.layout.fragment_serial_picking_scan
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}