package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.kotlin_wallet.ui.base.BaseFragment
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogPutAwayBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentDetailReceivingBinding
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.ErrorModel
import com.example.warehousemanagment.model.models.putaway.complete.CompletePutawayModel
import com.example.warehousemanagment.model.models.putaway.truck_detail.PutawayDetailRow
import com.example.warehousemanagment.ui.adapter.PutawayDetailAdapter
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.PutAwayDetailViewModel

class PutawayDetailFragment :
    BaseFragment<PutAwayDetailViewModel,FragmentDetailReceivingBinding>()
{

    lateinit var receivingId:String
    var sortType=Utils.LOCATION_CODE_SORT
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastReceivingPosition=0
    var chronometer: CountDownTimer?=null



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        receivingId=arguments?.getString(Utils.RECEIVING_ID,"").toString()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        refresh()

        b.swipeLayout.setOnRefreshListener()
        {
            refresh()
        }

        observeDetail()
        observeCount()
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)


        b.filterImg.img.setOnClickListener() { showFilterSheetDialog() }

    }
    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.relLocationCode.visibility=View.GONE
                binding.ownerCode.text=getString(R.string.createdOn)
                binding.productCode.text=getString(R.string.locationCode)

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.LOCATION_CODE_SORT->checkTick(binding.productCodeImg,binding)
                    Utils.ProductTitle->checkTick(binding.productTitleImg,binding)
                    Utils.CREATED_ON-> checkTick(binding.ownerCOdeImg, binding)

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
                if (receiveOrder!=Utils.DESC_ORDER)
                {
                    receiveOrder=Utils.DESC_ORDER
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
                if (sortType!=Utils.ProductTitle)
                {
                    sortType=Utils.ProductTitle
                    refresh()
                }

            }

            override fun onOwnerClick() {
                if ( sortType!=Utils.CREATED_ON)
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

    private fun startTimerForPutawayDetail()
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
        viewModel.clearPutawayDetail()
        receivePage = Utils.PAGE_START
        setPutDetail()
    }

    private fun observeCount()
    {
        viewModel.getPutawayCount().observe(viewLifecycleOwner,object :Observer<Int>
        {
            override fun onChanged(it: Int)
            {
                setBelowCount(requireActivity(), getString(R.string.tools_you_have),
                    it, getString(R.string.tasksToPutaway))
            }

        })
    }
    private fun observeDetail()
    {
        viewModel.getPutawayDetail().observe(viewLifecycleOwner,
            object :Observer<List<PutawayDetailRow>>
        {
            override fun onChanged(it: List<PutawayDetailRow>)
            {
                if (view != null && isAdded)
                {
                    b.swipeLayout.isRefreshing=false

                    lastReceivingPosition=it.size-1
                    showPutawayDetailList(it)

                }

            }

        })


    }

    private fun showPutawayDetailList(list:List<PutawayDetailRow>)
    {
        if(lastReceivingPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastReceivingPosition-Utils.ROWS)

        val adapter = PutawayDetailAdapter(list,requireActivity(),
            object : PutawayDetailAdapter.OnCallBackListener
            {
                override fun onClick(model:PutawayDetailRow)
                {
                    val dialogBinding = DialogPutAwayBinding.
                        inflate(LayoutInflater.from(requireActivity()), null)
                    val dialog = createAlertDialog(dialogBinding,
                        R.drawable.shape_background_rect_border_gray_solid_white, requireActivity())

                    initPutawayDialog(dialogBinding, model)

                    clearEdi(dialogBinding.layoutTopInfo.clearImg,dialogBinding.layoutTopInfo.serialEdi)

                    dialogBinding.rel4.confirm.setOnClickListener()
                    {
                        val location=textEdi(dialogBinding.layoutTopInfo.serialEdi)
                        if (location.length!=0 && location.equals(model.locationCode,ignoreCase = true))
                        {
                            completePutaway(dialogBinding.progress, model.receiptDetailID,model.itemLocationID,)
                            observeCompletePutaway(dialog)
                        }else
                            showWrongLocation(getString(R.string.putawayTitle), getString(R.string.wrongLocation))


                    }
                    dialogBinding.rel4.cansel.setOnClickListener{ dialog.dismiss() }
                    dialogBinding.closeImg.setOnClickListener { dialog.dismiss() }

                    dialog.setOnDismissListener {refresh()}
                }

                override fun reachToEnd(position: Int)
                {
                    receivePage=receivePage+1
                    setPutDetail()
                }
            })
        b.rv.adapter = adapter



        b.mainToolbar.searchEdi.doAfterTextChanged { startTimerForPutawayDetail()  }

//        b.mainToolbar.searchEdi.doAfterTextChanged()
//        {
//            search(textEdi(b.mainToolbar.searchEdi),list,
//                SearchFields.LocationCode,SearchFields.ProductTitle,
//                SearchFields.ProductCode,SearchFields.Quantity,
//                SearchFields.DockCode,)
//        }
//        search(textEdi(b.mainToolbar.searchEdi),list,
//            SearchFields.LocationCode,SearchFields.ProductTitle,
//            SearchFields.ProductCode,SearchFields.Quantity,
//            SearchFields.DockCode,)
    }

    private fun observeCompletePutaway(dialog: AlertDialog)
    {
        viewModel.getCompletePutaway().observe(viewLifecycleOwner,
            object : Observer<CompletePutawayModel> {
                override fun onChanged(it: CompletePutawayModel)
                {
                    dialog.dismiss()

                }

            })
        var done=false
        viewModel.getCompletePutawayError().observe(viewLifecycleOwner,
            object : Observer<ErrorModel>
            {
                override fun onChanged(it: ErrorModel)
                {
                    if (done==false && it.returnValue==Utils.WRONG_NUM)
                    {
                        showWrongLocation(getString(R.string.putawayTitle), getString(R.string.wrongLocation))
                        done=true
                    }
                }

            })
    }

    private fun setPutDetail()
    {
        viewModel.setPutawayDetial(
            pref.getDomain(),receivingId,
            textEdi(b.mainToolbar.searchEdi),receivePage
            ,Utils.ROWS,sortType,receiveOrder,
            pref.getTokenGlcTest(),b.progressBar,b.swipeLayout)
    }

    private fun completePutaway(progressBar: ProgressBar,
                                receiptDetailId:String,location:String)
    {
        viewModel.completePutaway(
            pref.getDomain(),location, receiptDetailId, pref.getTokenGlcTest(), progressBar)
    }

    private fun initPutawayDialog(
        dialogBinding: DialogPutAwayBinding,
        model: PutawayDetailRow)
    {
        dialogBinding.header.text=getString(R.string.locationScan)
        dialogBinding.layoutTopInfo.tv1.text = model.locationCode
        dialogBinding.layoutTopInfo.tv2.text = model.productTitle
        dialogBinding.layoutTopInfo.tv3.text = model.productCode
        dialogBinding.layoutTopInfo.tv6.text = model.quantity.toString()
    }

    private fun showWrongLocation(title:String,desc:String)
    {
        var mySheetAlertDialog: SheetAlertDialog?=null
        mySheetAlertDialog= SheetAlertDialog(title,desc
            ,object : SheetAlertDialog.OnClickListener
            {
                override fun onCanselClick() { mySheetAlertDialog?.dismiss() }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    mySheetAlertDialog?.dismiss()
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {
                    cansel.visibility=View.GONE
                }

                override fun onDismiss() {

                }

            })
        mySheetAlertDialog.show(this.getParentFragmentManager(), "")

    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
        if(view!=null){
            viewModel.getPutawayCount().removeObservers(viewLifecycleOwner)
            viewModel.getPutawayDetail().removeObservers(viewLifecycleOwner)
        }

    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.putawayItems))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())

        val receiveNumber=arguments?.getString(Utils.RECEIVE_NUMBER)
        val driveFullName=arguments?.getString(Utils.DRIVE_FULLNAME)
        val dockCode=arguments?.getString(Utils.DOCK_CODE)
        val createdOn=arguments?.getString(Utils.CREATED_ON)
        val carTypeTitle=arguments?.getString(Utils.CAR_TYPE_TITLE)
        val containerNumber=arguments?.getString(Utils.CONTAINER_NUMBER)


        b.receiveItem.recevieNumber.setText(receiveNumber)
        b.receiveItem.driverFullName.text=driveFullName
//        b.receiveItem.dockCode.text=dockCode
//        b.receiveItem.date.text=createdOn
//        b.receiveItem.type.text=carTypeTitle
        b.receiveItem.containerNumber.text=containerNumber


        val plaque1=arguments?.getString(Utils.PLAQUE_1)
        val plaque2=arguments?.getString(Utils.PLAQUE_2)
        val plaque3=arguments?.getString(Utils.PLAQUE_3)
        val plaque4=arguments?.getString(Utils.PLAQUE_4)
        b.receiveItem.plaque.setText(getBuiltString(plaque3.toString()
            ,plaque2.toString(),plaque1.toString()))
        b.receiveItem.plaqueYear.text=plaque4
    }



    override fun getLayout(): Int {
         return R.layout.fragment_detail_receiving
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}