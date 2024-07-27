package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetBottomBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.DialogSheetTaskTypeBinding
import com.example.warehousemanagment.databinding.FragmentDetailCargoBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideKeyboard
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.initShortCut
import com.example.warehousemanagment.model.classes.setBelowCount
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.LocationModel
import com.example.warehousemanagment.model.models.cargo_folder.cargo_detail.CargoDetailRow
import com.example.warehousemanagment.ui.adapter.CargoDetailAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetChooseLocationDialog
import com.example.warehousemanagment.ui.dialog.SheetConfirmDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.CargoDetailViewModel
import kotlin.properties.Delegates


class CargoDetailFragment :
    BaseFragment<CargoDetailViewModel, FragmentDetailCargoBinding>()
{

    var sortType=Utils.PRODUCT_TITLE_SORT
    var receivePage=Utils.PAGE_START
    var receiveOrder=Utils.ASC_ORDER
    var lastReceivingPosition=0
    var chronometer: CountDownTimer?=null
    lateinit var shippingAddressId:String
    lateinit var shippingNumber:String
    lateinit var bolNumber: String
    lateinit var customerFullName:String
    lateinit var driverFullName:String
    var isDone by Delegates.notNull<Boolean>()
    private var locations: List<LocationModel> = emptyList()
    private var selectedLocation: List<String> = emptyList()





    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        b.swipeLayout.setOnRefreshListener()
        {
            refreshCargoDetail()
        }

        refreshCargoDetail()

        observeReceiveList()
        observeReceiveCount()

        observeLocations()
        setLocations()

        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        b.filterImg.img.setOnClickListener()
        {
            showFilterSheetDialog()
        }


        b.receiveItem.assign.setOnClickListener()
        {
            hideKeyboard(requireActivity())
            showSubmitAssignSheet()
        }

        b.searchEdi.setOnClickListener {
            showLocationSheetDialog()
        }


        b.clearImg.setOnClickListener {
            selectedLocation = emptyList()
            b.searchEdi.setText("")
            refreshCargoDetail()
        }
//        onChangeCustomerName()

    }

//    private fun onChangeCustomerName()
//    {
//        b.searchEdi.doAfterTextChanged()
//        {
//            startTimerForGettingData()
//            {
//                refreshCargoDetail()
//            }
//        }
//    }

    private fun showLocationSheetDialog() {
        var sheet: SheetChooseLocationDialog? = null
        sheet = SheetChooseLocationDialog(
            locations.map { it.locationCode },selectedLocation,object : SheetChooseLocationDialog.OnClickListener{
                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun onItemClick(locations: List<String>) {
                    b.searchEdi.setText(locations.joinToString(","))
                    selectedLocation = locations
                    refreshCargoDetail()
                    sheet?.dismiss()
                }

                override fun init(binding: DialogSheetTaskTypeBinding) {

                }

            }
        )

        sheet.show(parentFragmentManager,"dialog")
    }
    private fun observeLocations() {
        viewModel.getLocations()
            .observe(viewLifecycleOwner){
                locations = it
            }
    }

    private fun setLocations() {
        viewModel.setLocations(
            pref.getDomain(),
            shippingAddressId,
            pref.getTokenGlcTest()
        )
    }

    private fun showSubmitAssignSheet()
    {
        var mySheetAlertDialog: SheetConfirmDialog? = null
        mySheetAlertDialog = SheetConfirmDialog(getString(R.string.driverTaskSubmit),
            getString(R.string.DoyouWantDriverTask),
            object : SheetConfirmDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String) {
                    submtiTransferTask(shippingAddressId, progress, mySheetAlertDialog)
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {

                }

                override fun init(binding: DialogSheetBottomBinding) {

                }

            })
        mySheetAlertDialog.show(getParentFragmentManager(), "")
    }




    private fun submtiTransferTask(
        shippingAddressId:String,
        progressBar: ProgressBar,
        mySheetAlertDialog: SheetConfirmDialog?
    ) {
        viewModel.driverTaskSubmit(
            url = pref.getDomain(),
            shippingAddressId = shippingAddressId,
            progressBar = progressBar,
            cookie = pref.getTokenGlcTest(),
            onErrorCallback = {mySheetAlertDialog?.dismiss()}
        ) {
            mySheetAlertDialog?.dismiss()
            navController?.popBackStack()
//            refreshCargoDetail()
        }
    }



    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding)
            {
                binding.productTitle.text=getString(R.string.productTitle)
                binding.ownerCode.text=getString(R.string.customerFullName)
                binding.locationCode.text=getString(R.string.locationCode)
                binding.productCode.text=getString(R.string.shippingNumber2)
                binding.rel5.visibility=View.VISIBLE
                binding.title5.text=getString(R.string.hasPriority)
                binding.rel6.visibility =if (!isDone) View.VISIBLE else View.GONE
                binding.title6.text = getString(R.string.done)


            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.LOCATION_CODE_SORT->checkTick(binding.locationCodeImg,binding)
                    Utils.ShippingNumber->checkTick(binding.productCodeImg,binding)
                    Utils.CUSTOMER_FULL_NAME->checkTick(binding.ownerCOdeImg,binding)
                    Utils.PRODUCT_TITLE_SORT->checkTick(binding.productTitleImg,binding)
                    Utils.HAS_PRIORITY -> checkTick(binding.img5,binding)
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
                    refreshCargoDetail()
                }

            }

            override fun onDescClick() {
                if (receiveOrder != Utils.DESC_ORDER) {
                    receiveOrder = Utils.DESC_ORDER
                    refreshCargoDetail()
                }
            }

            override fun onLocationCodeClick() {
                if (sortType!=Utils.LOCATION_CODE_SORT)
                {
                    sortType=Utils.LOCATION_CODE_SORT
                    refreshCargoDetail()
                }

            }

            override fun onProductCodeClick()
            {
                if (sortType!=Utils.ShippingNumber)
                {
                    sortType=Utils.ShippingNumber
                    refreshCargoDetail()
                }

            }

            override fun onProductTitleClick()
            {
                if (sortType!=Utils.PRODUCT_TITLE_SORT)
                {
                    sortType=Utils.PRODUCT_TITLE_SORT
                    refreshCargoDetail()
                }

            }

            override fun onOwnerClick()
            {
                if (sortType!=Utils.CUSTOMER_FULL_NAME)
                {
                    sortType=Utils.CUSTOMER_FULL_NAME
                    refreshCargoDetail()
                }

            }

            override fun onRel5Click() {
                if(sortType!=Utils.HAS_PRIORITY){
                    sortType=Utils.HAS_PRIORITY
                    refreshCargoDetail()
                }
            }

            override fun onRel6Click() {
                if (sortType!=Utils.Done) {
                    sortType=Utils.Done
                    refreshCargoDetail()
                }
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }

    private fun refreshCargoDetail()
    {
        receivePage = Utils.PAGE_START
        viewModel.clearReceiveList()
        setReceiveData()
    }

    private fun observeReceiveCount()
    {
        viewModel.getCargoCount().observe(viewLifecycleOwner
        ) { it ->
            setBelowCount(
                requireActivity(),
                getString(R.string.tools_you_have),
                it.first,
                getString(R.string.product)+" "+getString(R.string.and),
                it.second,
                getString(R.string.items)
            )
        }

    }

    private fun observeReceiveList()
    {
        viewModel.getCargoDetailList()
            .observe(viewLifecycleOwner
            ) { it ->
                if (view != null && isAdded) {
                    b.swipeLayout.isRefreshing = false
                    lastReceivingPosition = it.size - 1
                    showCargoDetailList(it)
                }
            }
    }
    private fun showCargoDetailList(list:List<CargoDetailRow>)
    {
        if(lastReceivingPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastReceivingPosition-Utils.ROWS)

       val  adapter = CargoDetailAdapter(list, requireActivity(), object : CargoDetailAdapter.OnCallBackListener
       {
           override fun onClick(model: CargoDetailRow, position: Int) {

            }

           override fun reachToEnd(position: Int)
           {
               receivePage += 1
               setReceiveData()
           }
        })
        b.rv.adapter = adapter
         


        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData { refreshCargoDetail()}
        }


    }


    private fun setReceiveData()
    {
        viewModel.setCargoDetailList(
            pref.getDomain(),
            pref.getTokenGlcTest(),
            textEdi(b.mainToolbar.searchEdi),
            receivePage, Utils.ROWS, sortType, receiveOrder,
            shippingAddressId = shippingAddressId,b.progressBar,
            b.swipeLayout,
//            customerName = textEdi(b.searchEdi),
            selectedLocation.joinToString(",")
        )
    }


    override fun onResume() {
        super.onResume()
        initShortCut(context=requireActivity(), title =getString(R.string.myCargo),
            onClick = {
                navController?.navigate(R.id.action_cargoDetailFragment_to_myCargoFragment)
            });
    }

    override fun onDestroy()
    {
        super.onDestroy()
        hideShortCut(requireActivity())
    }

    override fun init()
    {

        b.rel2.visibility =View.VISIBLE

        setToolbarTitle(requireActivity(),getString(R.string.cargoDetail))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
        b.receiveItem.title1?.text=getString(R.string.shippingNumber2)


        shippingAddressId=arguments?.getString(Utils.ShippingAddressId,"").toString()
        shippingNumber=arguments?.getString(Utils.ShippingNumber,"").toString()
        bolNumber = arguments?.getString(Utils.BOLNumber,"") ?: ""
        customerFullName=arguments?.getString(Utils.CUSTOMER_FULL_NAME,"").toString()
        driverFullName=arguments?.getString(Utils.DriverFullName,"").toString()
        isDone = arguments?.getBoolean(Utils.IsDone) ?: false

        val total = arguments?.getInt(Utils.total) ?: 0
        val doneCount = arguments?.getInt(Utils.Done) ?: 0
        val sumQuantity = arguments?.getInt(Utils.doneQuantity) ?: 0
        val sumDoneQuantity = arguments?.getInt(Utils.sumDoneQuantity) ?: 0
        val plaque1=arguments?.getString(Utils.PLAQUE_1)
        val plaque2=arguments?.getString(Utils.PLAQUE_2)
        val plaque3=arguments?.getString(Utils.PLAQUE_3)
        val plaque4=arguments?.getString(Utils.PLAQUE_4)
        val carType:String=arguments?.getString(Utils.CarType,"").toString()
        val date:String=arguments?.getString(Utils.Date,"").toString()
        val showAssignToMe = arguments?.getBoolean(Utils.showAssignToMe) ?: false

        b.receiveItem.recevieNumber.text =shippingNumber
        b.receiveItem.containerNumber.text =bolNumber
        b.receiveItem.driverFullName.text =driverFullName



        b.receiveItem.plaque.text = getBuiltString(plaque3.toString(),plaque2.toString(),plaque1.toString())
        b.receiveItem.plaqueYear.text =plaque4



        b.receiveItem.date?.text = date
        b.receiveItem.cartType?.text = carType


        b.receiveItem.relAssignFinish.visibility=View.VISIBLE
        b.receiveItem.finish.visibility=View.INVISIBLE

        b.receiveItem.qtyLay?.visibility = View.VISIBLE
        b.receiveItem.qtyTv?.text = sumQuantity.toString()
        b.receiveItem.totalTv?.text = total.toString()
        b.receiveItem.doneQtyTv?.text = sumDoneQuantity.toString()
        b.receiveItem.doneTv?.text = doneCount.toString()

        b.receiveItem.assign.visibility = if (showAssignToMe) View.VISIBLE else View.GONE



    }




    override fun getLayout(): Int {
         return R.layout.fragment_detail_cargo
    }

    override fun setupComponent(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }




}
