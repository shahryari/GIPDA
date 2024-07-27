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
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.receive.receiving.RowReceivingModel
import com.example.warehousemanagment.ui.adapter.ReceiveAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.ReceivingViewModel

class ReceivingFragment : BaseFragment<ReceivingViewModel, FragmentReceivingBinding>()
{

    var sortType=Utils.DockAssignTime
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
                binding.relLocationCode.visibility=View.INVISIBLE
                binding.ownerCode.text=getString(R.string.dockAssignTime)
                binding.productCode.text=getString(R.string.recevieNumber)
                binding.productTitle.text=getString(R.string.containerNumber)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.RECEIVE_NUMBER->checkTick(binding.productCodeImg,binding)
                    Utils.CONTAINER_NUMBER->checkTick(binding.productTitleImg,binding)
                    Utils.DockAssignTime-> checkTick(binding.ownerCOdeImg, binding)

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

            override fun onLocationCodeClick() {}

            override fun onProductCodeClick()
            {
                if (sortType!=Utils.RECEIVE_NUMBER)
                {
                    sortType=Utils.RECEIVE_NUMBER
                    refreshReceive()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!=Utils.CONTAINER_NUMBER)
                {
                    sortType=Utils.CONTAINER_NUMBER
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
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }

    private fun refreshReceive()
    {
        viewModel.dispose()
        receivePage = Utils.PAGE_START
        viewModel.clearReceiveList()
        setReceiveData()
    }

    private fun observeReceiveCount()
    {
        viewModel.getReceiveCount().observe(viewLifecycleOwner,object :Observer<Int>
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
        viewModel.getReceivingList()
            .observe(viewLifecycleOwner, object : Observer<List<RowReceivingModel>>
            {
                override fun onChanged(it: List<RowReceivingModel>)
                {
                    if (view!=null && isAdded)
                    {
                        b.swipeLayout.isRefreshing=false
                        lastReceivingPosition=it.size-1
                        showReceiveList(it)
                    }
                }
            })
    }
    private fun showReceiveList(list:List<RowReceivingModel>)
    {
        if(lastReceivingPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastReceivingPosition-Utils.ROWS)

       val  adapter = ReceiveAdapter(list, requireActivity(), object : ReceiveAdapter.OnCallBackListener
       {
                override fun onClick(model: RowReceivingModel, position: Int)
                {
                    val bundle = Bundle()
                    bundle.putString(Utils.RECEIVING_ID,model.receivingID)
                    bundle.putString(Utils.RECEIVE_NUMBER, model.receivingNumber)
                    bundle.putString(Utils.DRIVE_FULLNAME, model.driverFullName)
                    bundle.putString(Utils.DOCK_CODE, model.dockCode)
                    bundle.putString(Utils.CREATED_ON,model.createdOnString)
                    bundle.putString(Utils.CAR_TYPE_TITLE,model?.carTypeTitle?.toString())
                    bundle.putString(Utils.CONTAINER_NUMBER,model.containerNumber)

                    bundle.putString(Utils.PLAQUE_1,model.plaqueNumberFirst)
                    bundle.putString(Utils.PLAQUE_2,model.plaqueNumberSecond)
                    bundle.putString(Utils.PLAQUE_3,model.plaqueNumberThird)
                    bundle.putString(Utils.PLAQUE_4,model.plaqueNumberFourth)

                    pref.saveAdapterPosition(position)

                    navController?.navigate(R.id.action_receivingFragment_to_receivingDetailFragment, bundle)
                }


               override fun reachToEnd(position: Int)
               {
                   receivePage=receivePage+1
                   setReceiveData()
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
        viewModel.setReceivingList(
            pref.getDomain(),
            pref.getTokenGlcTest(), textEdi(b.mainToolbar.searchEdi),
            receivePage, Utils.ROWS, sortType, receiveOrder,b.progressBar,
            b.swipeLayout
        )
    }



    override fun onDestroy()
    {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){
            viewModel.getReceiveCount().removeObservers(viewLifecycleOwner)
            viewModel.getReceivingList().removeObservers(viewLifecycleOwner)
        }

    }
    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.receiving))

        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
    }




    override fun getLayout(): Int {
         return R.layout.fragment_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
