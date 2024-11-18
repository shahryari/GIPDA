package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.FragmentDockAssignBinding
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.models.dock_assign.ShippingListOnDockRow
import com.example.warehousemanagment.ui.adapter.DockAssignAdapter
import com.example.warehousemanagment.ui.adapter.DockListAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetDockDialog
import com.example.warehousemanagment.viewmodel.DockAssignViewModel

class DockAssignFragment : BaseFragment<DockAssignViewModel,FragmentDockAssignBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh()
        observeCount()
        observeShippingList()

    }


    fun showDockSheet(model: ShippingListOnDockRow) {
        var dialog: SheetDockDialog? = null
        dialog = SheetDockDialog(requireContext(),object :SheetDockDialog.OnClickListener{
            override fun onCloseClick() {
                dialog?.dismiss()
            }

            override fun init(rv: RecyclerView, progressBar: ProgressBar, searchEdi: EditText) {
                setDockList(model,searchEdi,progressBar)
                viewModel.getDockList()
                    .observe(viewLifecycleOwner){ list ->
                        val adapter = DockListAdapter(
                            list,requireContext()
                        ) {
                            viewModel.assignDock(
                                pref.getDomain(),
                                it.dockID,
                                model.shippingAddressID,
                                pref.getTokenGlcTest(),
                                requireContext(),
                                onSuccess = {
                                    dialog?.dismiss()
                                    refresh()
                                }
                            )
                        }
                        rv.adapter = adapter

                    }
                searchEdi.doAfterTextChanged {
                    startTimerForGettingData {
                        setDockList(model,searchEdi,progressBar)
                    }
                }
            }

        })

        dialog.show(this.parentFragmentManager,"")
    }

    fun setDockList(model: ShippingListOnDockRow,searchEditText: EditText,progressBar: ProgressBar) {
        viewModel.clearDockList()
        viewModel.setDockList(
            pref.getDomain(), textEdi(searchEditText),model.warehouseID,pref.getTokenGlcTest(),requireContext(),progressBar
        )
    }

    fun setShippingList() {
        viewModel.setShipping(
            pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi),
            pref.getTokenGlcTest(),
            requireContext(),
            b.progressBar
        )
    }

    fun refresh() {
        viewModel.clearList()
        setShippingList()
    }

    fun observeCount() {
        viewModel.getShippingCount()
            .observe(viewLifecycleOwner){
                setBelowCount(
                    requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.dock_assign)
                )
            }
    }
    fun observeShippingList() {
        viewModel.getShippingList()
            .observe(viewLifecycleOwner){
                showShippingList(it)
            }
    }

    private fun showShippingList(list: List<ShippingListOnDockRow>) {
        val adapter = DockAssignAdapter(
            list,
            requireActivity(),
            onReachEnd = {},
            onClick = {
                showDockSheet(it)
            }
        )
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData { refresh() }
        }
    }
    override fun init() {

        setToolbarTitle(requireContext(),getString(R.string.dock_assign))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }

    override fun getLayout(): Int {
        return R.layout.fragment_dock_assign
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}