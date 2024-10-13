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

    private lateinit var itemLocationId: String
    var chronometer: CountDownTimer? = null

    var sortType = Utils.ProductTitle
    var receivePage = Utils.PAGE_START
    var receiveOrder = Utils.ASC_ORDER
    var lastReceivingPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }
        refresh()


        observeSerials()
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        checkEnterKey(b.serialEdi){
            viewModel.scanPickingSerial(
                pref.getDomain(),
                itemLocationId,
                textEdi(b.serialEdi),
                pref.getTokenGlcTest(),
                requireContext(),
                b.progressBar,
            ){
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

        itemLocationId = arguments?.getString("ItemLocationID") ?:""
        val locationCode = arguments?.getString(Utils.locationCode)
        val productTitle = arguments?.getString(Utils.ProductTitle)
        val invType = arguments?.getString(Utils.locationInventory)
        val owner = arguments?.getString(Utils.OwnerCode)
        val quantity = arguments?.getInt(Utils.Quantity)
        b.pickingItem.rel4.visibility = View.GONE
        b.pickingItem.quantity.text = quantity.toString()
        b.pickingItem.driverFullName.text = owner
        b.pickingItem.invTypeTitle.text = invType
        b.pickingItem.productCode.text = locationCode
        b.pickingItem.productCode.text = productTitle
    }

    override fun getLayout(): Int {
        return R.layout.fragment_serial_picking_scan
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}