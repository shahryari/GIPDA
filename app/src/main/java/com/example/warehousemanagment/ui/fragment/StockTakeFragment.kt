package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.ui.adapter.StockTakingAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.StockTakeViewModel
import com.test.StockTrackRow

class StockTakeFragment : BaseFragment<StockTakeViewModel, FragmentReceivingBinding>()
{
    var sortType= Utils.CREATED_ON
    var stockPage= Utils.PAGE_START
    var receiveOrder= Utils.ASC_ORDER
    var lastPosition=0
    var chronometer: CountDownTimer?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }

        refresh()

        observeStockList()
        observeStockCount()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener()
        {
            showFilterSheetDialog()
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
                binding.relOwnerCode.visibility=View.INVISIBLE
                binding.relProductTitle.visibility=View.INVISIBLE
                binding.productCode.text=getString(R.string.createdOn)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.CREATED_ON-> checkTick(binding.productCodeImg,binding)

                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(receiveOrder== Utils.ASC_ORDER)
                {
                    asc.backgroundTintList= ContextCompat
                        .getColorStateList(requireActivity(), R.color.mainYellow)
                    desc.backgroundTintList=null
                }else
                {
                    desc.backgroundTintList= ContextCompat.
                    getColorStateList(requireActivity(), R.color.mainYellow)
                    asc.backgroundTintList=null
                }
            }


            override fun onCloseClick() { sheet?.dismiss() }

            override fun onAscClick()
            {
                if (receiveOrder!= Utils.ASC_ORDER)
                {
                    receiveOrder= Utils.ASC_ORDER
                    refresh()
                }

            }

            override fun onDescClick() {
                if (receiveOrder != Utils.DESC_ORDER) {
                    receiveOrder = Utils.DESC_ORDER
                    refresh()
                }
            }

            override fun onLocationCodeClick() {}

            override fun onProductCodeClick()
            {
                if (sortType!= Utils.CREATED_ON)
                {
                    sortType= Utils.CREATED_ON
                    refresh()
                }

            }

            override fun onProductTitleClick()
            {


            }

            override fun onOwnerClick()
            {


            }

            override fun onRel5Click() {
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }

    private fun refresh()
    {
        viewModel.dispose()
        stockPage = Utils.PAGE_START
        viewModel.clearStockList()
        setStockTakeData()
    }

    private fun observeStockCount()
    {
        viewModel.getStockCount().observe(viewLifecycleOwner,object : Observer<Int>
        {
            override fun onChanged(it: Int)
            {

                setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.stockToTake))
            }

        })

    }

    private fun observeStockList()
    {
        viewModel.getStockTakeList()
            .observe(viewLifecycleOwner, object : Observer<List<StockTrackRow>>
            {
                override fun onChanged(it: List<StockTrackRow>)
                {
                    if (view!=null && isAdded)
                    {
                        b.swipeLayout.isRefreshing=false
                        lastPosition=it.size-1
                        showStockTakeList(it)
                    }
                }
            })
    }
    private fun showStockTakeList(list:List<StockTrackRow>)
    {
        if(lastPosition- Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition- Utils.ROWS)

        val  adapter = StockTakingAdapter(list, requireActivity(),
            object : StockTakingAdapter.OnCallBackListener
        {
            override fun onClick(model: StockTrackRow)
            {
               val bundle=Bundle()

                bundle.putString(Utils.StockTakingID,model.stockTakingID)
                bundle.putString(Utils.StockTurnCode,model.warehouseCode)
                bundle.putString(Utils.StockTurnTitle,model.title)



                navController?.navigate(R.id.
                    action_stockTakeFragment_to_stockTakeLocationFragment, bundle)
            }


            override fun reachToEnd(position: Int)
            {
                stockPage=stockPage+1
                setStockTakeData()
            }
        })
        b.rv.adapter = adapter



        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData { refresh()}
        }


    }

    private fun setStockTakeData()
    {
        viewModel.setStockTakeList(
            pref.getDomain(),
            pref.getTokenGlcTest(),
            textEdi(b.mainToolbar.searchEdi),
            stockPage,
            Utils.ROWS,
            sortType,
            receiveOrder,
            b.progressBar,
            b.swipeLayout
        )
    }



    override fun onDestroy()
    {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){
            viewModel.getStockTakeList().removeObservers(viewLifecycleOwner)
        }

    }
    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.stockTake))

        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }




    override fun getLayout(): Int {
        return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}