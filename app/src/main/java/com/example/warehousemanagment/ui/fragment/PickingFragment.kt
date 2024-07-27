package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.picking.picking.PickingRow
import com.example.warehousemanagment.ui.adapter.PickingListAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.PickingListViewModel

class PickingFragment( )
    : BaseFragment<PickingListViewModel, FragmentReceivingBinding>()
{

    var sortType= Utils.TaskTime
    var receivePage= Utils.PAGE_START
    var receiveOrder= Utils.ASC_ORDER
    var lastReceivingPosition=0
    var chronometer: CountDownTimer?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }
        refresh()


        observePicking()
        observeCount()
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)


        b.filterImg.img.setOnClickListener()
        {
            showFilterSheetDialog()
        }

    }

    private fun observeCount()
    {
        viewModel.getPickCount().observe(viewLifecycleOwner
        ) { it ->
            setBelowCount(
                requireActivity(), getString(R.string.tools_you_have),
                it, getString(R.string.truckToPick)
            )
        }

    }

    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.relLocationCode.visibility=View.INVISIBLE
                binding.ownerCode.text=getString(R.string.taskTime)
                binding.productCode.text=getString(R.string.locationCode)
                binding.productTitle.text=getString(R.string.productTitle)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.LOCATION_CODE_SORT->checkTick(binding.productCodeImg,binding)
                    Utils.PRODUCT_TITLE_SORT->checkTick(binding.productTitleImg,binding)
                    Utils.TaskTime -> checkTick(binding.ownerCOdeImg, binding)

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

            override fun onDescClick()
            {
                if (receiveOrder != Utils.DESC_ORDER)
                {
                    receiveOrder = Utils.DESC_ORDER
                    refresh()
                }
            }

            override fun onLocationCodeClick() {}

            override fun onProductCodeClick()
            {
                if (sortType!=Utils.LOCATION_CODE_SORT)
                {
                    sortType=Utils.LOCATION_CODE_SORT
                    refresh()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!=Utils.PRODUCT_TITLE_SORT)
                {
                    sortType=Utils.PRODUCT_TITLE_SORT
                    refresh()
                }

            }

            override fun onOwnerClick()
            {
                if (sortType!=Utils.CREATED_ON)
                {
                    sortType=Utils.CREATED_ON
                    refresh()
                }

            }

            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }
    private fun startTimerForPicking()
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

    private fun refresh()
    {
        viewModel.dispose()
        viewModel.clearPickingList()
        receivePage = Utils.PAGE_START
        setPickingList()
    }

    private fun observePicking()
    {
        viewModel.getPickingList().observe(viewLifecycleOwner,
            object : Observer<List<PickingRow>>
            {
                override fun onChanged(it: List<PickingRow>)
                {
                    if (view!=null && isAdded)
                    {
                        b.swipeLayout.isRefreshing=false

                        lastReceivingPosition=it.size-1
                        showPickingList(it)
                    }
                }
            })
    }

    private fun showPickingList(list: List<PickingRow>)
    {
        if(lastReceivingPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastReceivingPosition-Utils.ROWS)


        val adapter = PickingListAdapter(list, requireActivity(),
            object : PickingListAdapter.OnCallBackListener
            {
                override fun onClick(model: PickingRow)
                {
                    val bundle=Bundle()
                    bundle.putString(Utils.locationCode,model.locationCode)
                    bundle.putString(Utils.sumQuantity,model.sumQuantity.toString())

                    navController?.navigate(R.id.action_pickingFragment_to_pickingDetailFragment, bundle)



                }

                override fun reachToEnd(position: Int)
                {
                    receivePage=receivePage+1
                    setPickingList()
                }
            })
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForPicking()
        }



    }



    private fun setPickingList()
    {
        viewModel.setPickingList(
            pref.getDomain(),textEdi(b.mainToolbar.searchEdi),
            receivePage,Utils.ROWS,sortType,
            receiveOrder,pref.getTokenGlcTest(),b.progressBar
        ,b.swipeLayout)
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){
            viewModel.getPickCount().removeObservers(viewLifecycleOwner)
            viewModel.getPickingList().removeObservers(viewLifecycleOwner)
        }
    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.picking))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }


    override fun getLayout(): Int {
         return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}