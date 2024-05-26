package com.example.warehousemanagment.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.kotlin_wallet.ui.base.BaseFragment
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.check_truck.CheckTruckModel
import com.example.warehousemanagment.model.models.check_truck.confirm.ConfirmCheckTruckModel
import com.example.warehousemanagment.model.models.check_truck.deny.DenyCheckTruckModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.ui.adapter.CheckTruckAdapter
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetCheckTruckAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetDenyAlertDialog
import com.example.warehousemanagment.viewmodel.CheckTruckViewModel
import java.lang.StringBuilder

class CheckTruckFragment :
    BaseFragment<CheckTruckViewModel, FragmentReceivingBinding>()
{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        b.swipeLayout.setOnRefreshListener()
        {
            viewModel.setCheckTruck(pref.getDomain(),pref.getTokenGlcTest())

        }

        viewModel.setCheckTruck(pref.getDomain(),pref.getTokenGlcTest())
        observeCheckTruck()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)


    }

    private fun observeCheckTruck()
    {
        viewModel.getCheckTruckList()
            .observe(viewLifecycleOwner, object : Observer<List<CheckTruckModel>>
            {
                override fun onChanged(it: List<CheckTruckModel>)
                {
                    if (view != null && isAdded)
                    {
                        b.swipeLayout.isRefreshing=false
                        setBelowCount(requireActivity(),getString(R.string.tools_you_have),
                            it.size, getString(R.string.truckToCheck))
                        showCheckTruckList(it)
                    }
                }
            })
    }

    private fun  showCheckTruckList(list:List<CheckTruckModel>)
    {
        val adapter = CheckTruckAdapter(list,requireActivity(),
            object : CheckTruckAdapter.OnCallBackListener
            {
                override fun onDenyClick(model: CheckTruckModel, itemView: View, confirm: TextView, deny: TextView)
                {
                    changeItemViewBackground(itemView,R.color.mainYellow)
                    backToViewPostion(itemView, confirm, deny)
                    showDenySheetDialog(viewModel.getDenyReason(),getString(R.string.denyTruck), model,itemView)
                }
                override fun onConfirmClick(shippingAddressId:String,shippingNumber:String,
                                            itemView: View, confirm: TextView, deny: TextView)
                {
                    changeItemViewBackground(itemView,R.color.mainYellow)
                    backToViewPostion(itemView, confirm, deny)


                    val sb=getBuiltString(getString(R.string.areSureConfirm)
                        ,shippingNumber,getString(R.string.checkTrucks_))
                    showConfirmSheetDialog(getString(R.string.confirmTruck),sb,shippingAddressId,itemView)
                }
                override fun dragToStart(itemView: View, confirm: TextView, deny: TextView) {
                    dragViewToStart(itemView, confirm, deny)
                }

                override fun dragToEnd(itemView: View, confirm: TextView, deny: TextView) {
                    dragViewToEnd(itemView, confirm, deny)
                }
            })
        b.rv.adapter = adapter


        adapter.setFilter(search(textEdi(b.mainToolbar.searchEdi),
            list,SearchFields.ShippingNumber,
            SearchFields.CustomerFullName,SearchFields.BOLNumber
            ,SearchFields.DriverFullName,SearchFields.DockCode
            /*    ,SearchFields., car name has to be in search field =>TEMP*/
        ))

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            adapter.setFilter(search(textEdi(b.mainToolbar.searchEdi),
                list,SearchFields.ShippingNumber,
                SearchFields.CustomerFullName,SearchFields.BOLNumber
                ,SearchFields.DriverFullName,SearchFields.DockCode
                /*    ,SearchFields., car name has to be in search field =>TEMP*/
            ))

        }
//        val yourSearchEditText=b.mainToolbar.searchEdi
//        val yourList=list
//        val yourAdapter=adapter
//
//
//        yourSearchEditText.doAfterTextChanged()
//        {
//            yourAdapter.setFilter(search(
//                searchText = yourSearchEditText.text.toString(),
//                list=yourList,
//                searchField = "yourSearchField")
//            )
//        }








    }

    private fun changeItemViewBackground(itemView: View, color: Int) {
        if (color==0)
            itemView.backgroundTintList=null
        else
            itemView.backgroundTintList=ContextCompat.getColorStateList(requireActivity(),color)
    }

    private fun backToViewPostion(itemView: View, confirm: TextView, deny: TextView)
    {
        itemView.animate().translationX(0f).withEndAction()
        {
            confirm.visibility = View.INVISIBLE
            deny.visibility = View.INVISIBLE
        }
    }

    private fun dragViewToEnd(itemView: View, confirm: TextView, deny: TextView)
    {
        if (itemView.translationX != 0f) {
            itemView.animate().translationX(0f).withEndAction()
            {
                confirm.visibility = View.INVISIBLE
                deny.visibility = View.INVISIBLE
            }
        } else {
            itemView.animate().translationX(confirm.width.toFloat() * Utils.CheckTruckPERCENT)
            confirm.visibility = View.VISIBLE
            deny.visibility = View.INVISIBLE
        }
    }

    private fun dragViewToStart(itemView: View, confirm: TextView, deny: TextView) {
        if (itemView.translationX != 0f) {
            itemView.animate().translationX(0f).withEndAction()
            {
                confirm.visibility = View.INVISIBLE
                deny.visibility = View.INVISIBLE
            }
        } else {
            itemView.animate().translationX(-confirm.width.toFloat() * Utils.CheckTruckPERCENT)
            confirm.visibility = View.INVISIBLE
            deny.visibility = View.VISIBLE
        }
    }

    private fun showConfirmSheetDialog(
        title: String,
        desc: String,
        shippingAddressId: String,
        itemView: View
    )
    {
        var mySheetAlertDialog: SheetCheckTruckAlertDialog?=null
        mySheetAlertDialog= SheetCheckTruckAlertDialog(title,desc
            ,object : SheetCheckTruckAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar)
                {
                   viewModel.setConfirmTruck(pref.getDomain(),shippingAddressId,pref.getTokenGlcTest())
                   viewModel.getConfirmTruck().observe(viewLifecycleOwner,object :Observer<ConfirmCheckTruckModel>{
                       override fun onChanged(it: ConfirmCheckTruckModel)
                       {
                           /*doing something*/
                           mySheetAlertDialog?.dismiss()

                           viewModel.setCheckTruck(pref.getDomain(),pref.getTokenGlcTest())
                           observeCheckTruck()

                       }
                   })
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {
                    changeItemViewBackground(itemView,0)
                }

            })
        mySheetAlertDialog.show(this.getParentFragmentManager(), "")

    }

    private fun showDenySheetDialog(
        reasons: List<CatalogModel>, title: String,
        model: CheckTruckModel,
        itemView: View)
    {
        var valueType=1
        var mySheetAlertDialog: SheetDenyAlertDialog?=null
        mySheetAlertDialog= SheetDenyAlertDialog(reasons,title
            ,object : SheetDenyAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progressBar: ProgressBar)
                {
                    viewModel.setDenyTruck(pref.getDomain(),model.shippingAddressID,model.shippingID,
                    valueType,pref.getTokenGlcTest(),progressBar)
                    viewModel.getDenyTruck().observe(viewLifecycleOwner,object :Observer<DenyCheckTruckModel>{
                        override fun onChanged(it: DenyCheckTruckModel)
                        {
                            mySheetAlertDialog?.dismiss()


                            viewModel.setCheckTruck(pref.getDomain(),pref.getTokenGlcTest())
                            observeCheckTruck()
                        }

                    })

                }

                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun selectType(model: CatalogModel) {
                    valueType=model.valueField
                }

                override fun onDismiss() {
                    changeItemViewBackground(itemView,0)
                }


            })

        mySheetAlertDialog.show(this.getParentFragmentManager(), "")

    }

    override fun onDestroy() {
        super.onDestroy()
        if (view!=null){
            viewModel.getCheckTruckList().removeObservers(viewLifecycleOwner)
        }
    }


    override fun init()
    {
        activity?.findViewById<TextView>(R.id.title)?.setText(getString(R.string.checkTruck))

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