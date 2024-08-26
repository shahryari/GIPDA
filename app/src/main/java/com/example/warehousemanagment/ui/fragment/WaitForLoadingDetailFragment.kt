package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentDetailReceivingBinding
import com.example.warehousemanagment.databinding.PatternShippingDetailBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.chronometer
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.shipping.customer.CustomerInShipping
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailRow
import com.example.warehousemanagment.ui.adapter.ShipingDetailAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.WaitForLoadingDetailViewModel

class WaitForLoadingDetailFragment : BaseFragment<WaitForLoadingDetailViewModel, FragmentDetailReceivingBinding>(){
    lateinit var shippingId:String

    var sortType=Utils.ProductTitle
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastPosition=0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shippingId= arguments?.getString(Utils.ShippingId).toString()

        setShippingDetail()

        b.swipeLayout.setOnRefreshListener {
            refresh()
        }

        observeShippingDetail()
        observeShippingCount()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)
        var customerColorList : List<CustomerInShipping> = emptyList()


        b.filterImg.img.setOnClickListener {
            showFilterSheetDialog()
        }
    }

    private fun observeShippingCount()
    {
        viewModel.getShippingCount().observe(viewLifecycleOwner
        ) { it ->
            setBelowCount(
                requireActivity(), getString(R.string.tools_you_have),
                it.total, " " + getString(R.string.productsAnd) + " " +
                        it.remain + " " + getString(R.string.Items)
            )
        }

    }
    private fun startTimerForReceiveingData()
    {
        chronometer?.cancel()
        chronometer = object : CountDownTimer(Utils.DELAY_SERIAL, 100)
        {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                refresh()
            }
        }.start()
    }

    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.locationCode.text=getString(R.string.customerFullName)
                binding.relOwnerCode.visibility=View.GONE
                binding.productCode.text=getString(R.string.product_code)
                binding.productTitle.text=getString(R.string.productTitle)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.CUSTOMER_FULL_NAME -> checkTick(binding.locationCodeImg,binding)
                    Utils.ProductTitle -> checkTick(binding.productTitleImg,binding)
                    Utils.ProductCode -> checkTick(binding.productCodeImg,binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(receiveOrder==Utils.ASC_ORDER){
                    asc.backgroundTintList= ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
                    desc.backgroundTintList=null
                }else
                {
                    desc.backgroundTintList= ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
                    asc.backgroundTintList=null
                }
            }


            override fun onCloseClick() { sheet?.dismiss() }

            override fun onAscClick()
            {
                if (receiveOrder!=Utils.ASC_ORDER)
                {
                    receiveOrder=Utils.ASC_ORDER
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
                if (sortType!=Utils.CUSTOMER_FULL_NAME) {
                    sortType=Utils.CUSTOMER_FULL_NAME
                    refresh()
                }
            }

            override fun onProductCodeClick() {
                if (sortType!=Utils.ProductCode) {
                    sortType=Utils.ProductCode
                    refresh()
                }

            }

            override fun onProductTitleClick() {
                if (sortType!=Utils.ProductTitle) {
                    sortType=Utils.ProductTitle
                    refresh()
                }

            }

            override fun onOwnerClick() {


            }

            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }
    private fun refresh() {
        receivePage = Utils.PAGE_START
        viewModel.clearList()
        setShippingDetail()
    }

    private fun observeShippingDetail()
    {
        viewModel.getShippingDetail().observe(viewLifecycleOwner
        ) { it ->
            if (isAdded && view != null) {
                b.swipeLayout.isRefreshing = false

                lastPosition = it.size - 1
                showShippingDetailList(it)

            }
        }
    }
    private fun showShippingDetailList(list: List<ShippingDetailRow>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition-Utils.ROWS)

        val adapter = ShipingDetailAdapter(list, requireActivity(),
            object : ShipingDetailAdapter.OnCallBackListener
            {
                override fun onClick(model: ShippingDetailRow)
                {

                }

                override fun reachToEnd(position: Int)
                {
                    receivePage += 1
                    setShippingDetail()
                }

                override fun onCloseClick(model: ShippingDetailRow) {
                }

                override fun init(binding: PatternShippingDetailBinding) {
                    binding.relClose.visibility = View.GONE

                }
            })
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged {
            startTimerForReceiveingData()
        }

    }
    private fun setShippingDetail() {
        viewModel.setShippingList(
            pref.getDomain(),shippingId,
            textEdi(b.mainToolbar.searchEdi),
            customers = "",
            receivePage,Utils.ROWS,sortType,receiveOrder,
            pref.getTokenGlcTest(),b.progressBar,b.swipeLayout)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (view!=null){
            viewModel.getShippingDetail().removeObservers(viewLifecycleOwner)
        }
    }

    override fun init() {
        b.receiveItem.btitle2?.text=getString(R.string.bolNumber)
        b.receiveItem.bolLay?.visibility = View.VISIBLE
        b.receiveItem.bolLay2?.visibility = View.GONE
        b.receiveItem.driverLay?.visibility = View.VISIBLE
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
        activity?.findViewById<TextView>(R.id.title)?.text = getString(R.string.waite_for_loading)

        b.mainToolbar2.rel2.visibility = View.GONE

        b.receiveItem.driverFullName.text=arguments?.getString(Utils.DRIVE_FULLNAME)
        b.receiveItem.recevieNumber.text=arguments?.getString(Utils.ShippingNumber)
        b.receiveItem.containerNumber.text=arguments?.getString(Utils.BOLNumber)

        val plaque1=arguments?.getString(Utils.PLAQUE_1)
        val plaque2=arguments?.getString(Utils.PLAQUE_2)
        val plaque3=arguments?.getString(Utils.PLAQUE_3)
        val plaque4=arguments?.getString(Utils.PLAQUE_4)
//        val total = arguments?.getInt(Utils.total) ?: 0
//        val doneCount = arguments?.getInt(Utils.Done) ?: 0
//        val sumQuantity = arguments?.getInt(Utils.doneQuantity) ?: 0
//        val sumDoneQuantity = arguments?.getInt(Utils.sumDoneQuantity) ?: 0
        val customerCount = arguments?.getInt(Utils.customerCount) ?: 0

        b.receiveItem.plaque.text = getBuiltString(plaque3.toString(),plaque2.toString(),plaque1.toString())
        b.receiveItem.plaqueYear.text=plaque4
//        b.receiveItem.qtyLay?.visibility = View.VISIBLE
//        b.receiveItem.qtyLay?.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.yellowGray))
//        b.receiveItem.qtyTv?.text = sumQuantity.toString()
//        b.receiveItem.totalTv?.text = total.toString()
//        b.receiveItem.doneQtyTv?.text = sumDoneQuantity.toString()
//        b.receiveItem.doneTv?.text = doneCount.toString()
        b.receiveItem.lineCustomerCount?.visibility = View.GONE
        b.receiveItem.customerCount?.text = customerCount.toString()
        b.receiveItem.relAssignFinish.visibility = View.GONE

        b.receiveItem.title1?.text=getString(R.string.shippingNumber2)
        b.searchEdi.hint = getString(R.string.customers)

    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }


    override fun getLayout(): Int {
        return R.layout.fragment_detail_receiving
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}