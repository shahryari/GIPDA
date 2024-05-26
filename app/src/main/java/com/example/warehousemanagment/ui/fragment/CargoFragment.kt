package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.kotlin_wallet.ui.base.BaseFragment
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentReceivingBinding
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.cargo_folder.cargo.CargoRow
import com.example.warehousemanagment.ui.adapter.CargoAdapter
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.CargoViewModel
import com.squareup.picasso.Picasso

class CargoFragment : BaseFragment<CargoViewModel,FragmentReceivingBinding>()
{

    var sortType=Utils.NOT_ASSIGN
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastReceivingPosition=0
    var chronometer: CountDownTimer?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        b.swipeLayout.setOnRefreshListener()
        {
            refreshReceive()
        }

        refreshReceive()

        observeReceiveList()
        observeReceiveCount()

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
                binding.rel5.visibility=View.VISIBLE
                binding.locationCode.text=getString(R.string.notAssign)
                binding.ownerCode.text=getString(R.string.dockAssignTime)
                binding.productCode.text=getString(R.string.shippingNumber2)
                binding.productTitle.text=getString(R.string.driverFullName)
                binding.title5.text=getString(R.string.done)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.NOT_ASSIGN->checkTick(binding.locationCodeImg,binding)
                    Utils.ShippingNumber->checkTick(binding.productCodeImg,binding)
                    Utils.DriverFullName->checkTick(binding.productTitleImg,binding)
                    Utils.DockAssignTime-> checkTick(binding.ownerCOdeImg, binding)
                    Utils.Done-> checkTick(binding.img5, binding)


                }
            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(receiveOrder==Utils.ASC_ORDER)
                {
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
                    refreshReceive()
                }

            }

            override fun onDescClick() {
                if (receiveOrder != Utils.DESC_ORDER) {
                    receiveOrder = Utils.DESC_ORDER
                    refreshReceive()
                }
            }

            override fun onLocationCodeClick() {
                if (sortType != Utils.NOT_ASSIGN) {
                    sortType = Utils.NOT_ASSIGN
                    refreshReceive()
                }
            }

            override fun onProductCodeClick()
            {
                if (sortType!=Utils.ShippingNumber)
                {
                    sortType=Utils.ShippingNumber
                    refreshReceive()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!=Utils.DriverFullName)
                {
                    sortType=Utils.DriverFullName
                    refreshReceive()
                }

            }

            override fun onOwnerClick()
            {
                if (sortType!=Utils.DockAssignTime)
                {
                    sortType=Utils.DockAssignTime
                    refreshReceive()
                }

            }

            override fun onRel5Click() {
                if (sortType != Utils.Done) {
                    sortType = Utils.Done
                    refreshReceive()
                }
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }

    private fun refreshReceive()
    {
        receivePage = Utils.PAGE_START
        viewModel.clearReceiveList()
        setReceiveData()
    }

    private fun observeReceiveCount()
    {
        viewModel.getCargoCount().observe(viewLifecycleOwner,object :Observer<Int>
        {
            override fun onChanged(it: Int)
            {
                setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.tools_you_have_3_trcuk_to_receive))
            }

        })

    }

    private fun observeReceiveList()
    {
        viewModel.getCargoList()
            .observe(viewLifecycleOwner, object : Observer<List<CargoRow>>
            {
                override fun onChanged(it: List<CargoRow>)
                {
                    if (view!=null && isAdded)
                    {
                        b.swipeLayout.isRefreshing=false
                        lastReceivingPosition=it.size-1
                        showCargoList(it)
                    }
                }
            })
    }
    private fun showCargoList(list:List<CargoRow>)
    {
        if(lastReceivingPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else{
            val  multi=(lastReceivingPosition-Utils.ROWS)/10+1
            b.rv.scrollToPosition(Utils.ROWS * multi -1)
        }

       val  adapter = CargoAdapter(list, requireActivity(), object : CargoAdapter.OnCallBackListener
       {
           override fun onClick(model: CargoRow, position: Int, progressBar: ProgressBar)
           {
               val bundle = Bundle()
               bundle.putString(Utils.ShippingAddressId, model.shippingAddressID)
               bundle.putString(Utils.ShippingNumber, model.shippingNumber)
               bundle.putString(Utils.CUSTOMER_FULL_NAME, model.customerFullName)
               bundle.putString(Utils.DriverFullName, model.driverFullName)
               bundle.putString(Utils.CarType, model.carTypeTitle)
               bundle.putString(Utils.Date, model.createdOnString)
               bundle.putString(Utils.PLAQUE_1, model.plaqueNumberFirst)
               bundle.putString(Utils.PLAQUE_2, model.plaqueNumberSecond)
               bundle.putString(Utils.PLAQUE_3, model.plaqueNumberThird)
               bundle.putString(Utils.PLAQUE_4, model.plaqueNumberFourth)

               pref.saveAdapterPosition(position)

               navController?.navigate(R.id.action_cargoFragment_to_cargoDetailFragment, bundle)



            }

           override fun reachToEnd(position: Int)
           {
               receivePage=receivePage+1
               setReceiveData()
           }

           override fun setImage(img: String, imageView: ImageView)
           {
               Picasso.get().load(pref.getDomain()
                   .replace("/mobile/v1/","")+img)
                   .into(imageView)
           }
       })
        b.rv.adapter = adapter
         


        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData { refreshReceive()}
        }


    }



    private fun setReceiveData()
    {
        viewModel.setCargoList(
            pref.getDomain(),
            pref.getTokenGlcTest(), textEdi(b.mainToolbar.searchEdi),
            receivePage, Utils.ROWS, sortType, receiveOrder,b.progressBar,
            b.swipeLayout
        )
    }


    override fun onResume() {
        super.onResume()
        initShortCut(context=requireActivity(), title =getString(R.string.myCargo), onClick = {
            navController?.navigate(R.id.action_cargoFragment_to_myCargoFragment)
        });
    }

    override fun onDestroy()
    {
        super.onDestroy()
        hideShortCut(requireActivity())

    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.cargo))

        setToolbarBackground(b.mainToolbar.rel2,requireActivity())



    }





    override fun getLayout(): Int {
         return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
