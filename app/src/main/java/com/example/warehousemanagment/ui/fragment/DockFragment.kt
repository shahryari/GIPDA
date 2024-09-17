package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentDocksBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.dock.DockRow
import com.example.warehousemanagment.ui.adapter.DockAdapter
import com.example.warehousemanagment.ui.adapter.DockGroupAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.DockViewModel

class DockFragment : BaseFragment<DockViewModel,FragmentDocksBinding>() {
    var sortType= Utils.DockCode
    var receivePage= Utils.PAGE_START
    var receiveOrder= Utils.ASC_ORDER
    var lastReceivingPosition=0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDockList()
        b.swipeLayout.setOnRefreshListener {
            refresh()
        }
        refresh()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)
        observeDockCount()
        observeUseDock()

        b.filterImg.img.setOnClickListener {
            showFilterDialog()
        }
    }


    private fun showFilterDialog(){
        var sheet : SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(onClickListener = object : SheetSortFilterDialog.OnClickListener {
            override fun initView(binding: DialogSheetSortFilterBinding) {
//                binding.relProdcutCode.visibility = View.GONE
//                binding.relProductTitle.visibility = View.GONE
//                binding.relOwnerCode.visibility = View.GONE
                binding.relLocationCode.visibility = View.GONE
                binding.rel5.visibility = View.GONE
                binding.rel6.visibility = View.GONE

                binding.productCode.text = getString(R.string.docCode)
                binding.productTitle.text = getString(R.string.createdOn)
                binding.ownerCode.text = getString(R.string.use)
            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding) {
                when(sortType) {
                    Utils.DockCode -> checkTick(binding.productCodeImg,binding)
                    Utils.CreatedOn -> checkTick(binding.productTitleImg,binding)
                    Utils.UseDock -> checkTick(binding.ownerCOdeImg,binding)
                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView) {
                if(receiveOrder==Utils.ASC_ORDER){
                    asc.backgroundTintList= ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
                    desc.backgroundTintList=null
                }else
                {
                    desc.backgroundTintList= ContextCompat.getColorStateList(requireActivity(), R.color.mainYellow)
                    asc.backgroundTintList=null
                }
            }

            override fun onCloseClick() {
                sheet?.dismiss()
            }

            override fun onAscClick() {
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
            }

            override fun onProductCodeClick() {
                if (sortType!=Utils.DockCode)
                {
                    sortType=Utils.DockCode
                    refresh()
                }
            }

            override fun onProductTitleClick() {
                if (sortType!=Utils.CreatedOn)
                {
                    sortType=Utils.CreatedOn
                    refresh()
                }
            }

            override fun onOwnerClick() {
                if (sortType!=Utils.UseDock)
                {
                    sortType=Utils.UseDock
                    refresh()
                }
            }

            override fun onRel5Click() {
            }

        })
        sheet.show(parentFragmentManager,"")
    }
    private fun observeDockCount(){
        viewModel.getDockCount().observe(viewLifecycleOwner){
            setBelowCount(requireActivity(),getString(R.string.tools_you_have),it,getString(R.string.docks))
        }
    }

    private fun observeUseDock() {
        viewModel.getUseDock().observe(viewLifecycleOwner){
            if (it.isSucceed){
                refresh()
            }
        }
    }

    private fun observeDockList(){
        viewModel.getDockList().observe(viewLifecycleOwner){
            if (view != null && isAdded) {
                b.swipeLayout.isRefreshing = false
                lastReceivingPosition = it.size - 1
                showDockList(it)
            }
        }
    }

    private fun showDockList(list: List<DockRow>){
        val groupDock = list.groupBy { it.warehouseCode }
        if(lastReceivingPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else{
            val  multi=(lastReceivingPosition-Utils.ROWS)/10+1
            b.rv.scrollToPosition(Utils.ROWS * multi -1)
        }
        val adapter = DockGroupAdapter(requireActivity(),groupDock, onCallBackListener = object : DockAdapter.OnCallBackListener {
            override fun reachToEnd(position: Int) {
                receivePage += 1
                setDockList()
            }

            override fun onUseDock(dockId: String,useDock: Int) {
                setUseDock(dockId,useDock)
            }

            override fun enableRefresh(boolean: Boolean) {
                b.swipeLayout.isEnabled = boolean
            }

        })
        b.rv.addOnScrollListener(
            object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy!=0){
                        b.swipeLayout.isEnabled = false
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        b.swipeLayout.isEnabled = true
                    }
                }
            }
        )
        b.rv.adapter = adapter
    }

    private fun refresh() {
        viewModel.clearList()
        receivePage = Utils.PAGE_START
        setDockList()
    }

    private fun setDockList() {
        viewModel.setDockList(
            requireActivity(),
            pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi),
            receivePage,
            1000,
            sortType,
            receiveOrder,
            pref.getTokenGlcTest(),
            b.progressBar,
            b.swipeLayout
        )
    }

    private fun setUseDock(dockId: String,useDock: Int) {
        viewModel.setUseDock(
            requireActivity(),
            pref.getDomain(),
            dockId,
            useDock,
            pref.getTokenGlcTest()
        )
    }

    override fun init() {
        setToolbarTitle(requireActivity(),getString(R.string.dock))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }

    override fun getLayout(): Int {
        return R.layout.fragment_docks
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}