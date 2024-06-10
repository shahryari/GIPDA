package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetDestinyLocationBinding
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.DialogUnitOfMeasureBinding
import com.example.warehousemanagment.databinding.FragmentProductWithoutMasterBinding
import com.example.warehousemanagment.model.classes.checkTick
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.createAlertDialog
import com.example.warehousemanagment.model.classes.hideKeyboard
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.search
import com.example.warehousemanagment.model.classes.setToolbarBackground
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.transfer_task.PalletModel
import com.example.warehousemanagment.model.models.without_master.ProductWithoutMasterModel
import com.example.warehousemanagment.model.models.without_master.UnitOfMeasureSubmitModel
import com.example.warehousemanagment.ui.adapter.PalletAdapter
import com.example.warehousemanagment.ui.adapter.ProductWithoutMasterAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetPalletDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.ProductWithoutMasterViewModel


class ProductWithoutMasterFragment :
    BaseFragment<ProductWithoutMasterViewModel,
            FragmentProductWithoutMasterBinding>()
{
    var sortType:String= Utils.CREATED_ON
    var orderType:String= Utils.ASC_ORDER
    var page= Utils.PAGE_START
    var lastPosition=0
    var palletId:String=""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        clearEdi(b.mainToolbar.clearImg,b.mainToolbar.searchEdi)

        getFilteredData()

        observeProductWithoutMaster(b.swipeLayout)
        b.swipeLayout.setOnRefreshListener()
        {
            getFilteredData()

        }

        b.filterImg.setOnClickListener()
        {
            showFilterSheetDialog()

        }


    }

    private fun showFilterSheetDialog()
    {
        var sheet: SheetSortFilterDialog? = null
        sheet = SheetSortFilterDialog(object : SheetSortFilterDialog.OnClickListener
        {
            override fun initView(binding: DialogSheetSortFilterBinding) {
                binding.rel5.visibility=View.VISIBLE
            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {
                when(sortType)
                {
                    Utils.LOCATION_CODE_SORT->checkTick(binding.locationCodeImg,binding)

                    Utils.PRODUCT_CODE_SORT->checkTick(binding.productCodeImg,binding)

                    Utils.PRODUCT_TITLE_SORT->checkTick(binding.productTitleImg,binding)

                    Utils.OWNER_SORT ->checkTick(binding.ownerCOdeImg,binding)

                    Utils.CREATED_ON -> checkTick(binding.img5, binding)
                }
            }


            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                 if(orderType==Utils.ASC_ORDER){
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
                orderType = Utils.ASC_ORDER
                getFilteredData()
            }

            override fun onDescClick() {
                orderType = Utils.DESC_ORDER
                getFilteredData()
            }

            override fun onLocationCodeClick() {
                sortType = Utils.LOCATION_CODE_SORT
                getFilteredData()
            }

            override fun onProductCodeClick() {
                sortType = Utils.PRODUCT_CODE_SORT
                getFilteredData()
            }

            override fun onProductTitleClick() {
                sortType = Utils.PRODUCT_TITLE_SORT
                getFilteredData()
            }

            override fun onOwnerClick() {
                sortType = Utils.OWNER_SORT
                getFilteredData()
            }

            override fun onRel5Click() {
                sortType=Utils.CREATED_ON
                getFilteredData()
            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun getFilteredData()
    {
        viewModel.clearProductWithoutMaster()
        page = Utils.PAGE_START
        hideKeyboard(requireActivity())
        viewModel.setProductWithoutMaster(
            pref.getDomain(),
            textEdi(b.mainToolbar.searchEdi), page, Utils.ROWS, sortType,
            orderType, pref.getTokenGlcTest(), b.progressBar,b.swipeLayout
        )

    }


    private fun observeProductWithoutMaster(swipeLayout: SwipeRefreshLayout)
    {
        viewModel.getProductWithoutMaster().observe(viewLifecycleOwner,
            object :Observer<List<ProductWithoutMasterModel>>
            {
                override fun onChanged(it: List<ProductWithoutMasterModel>)
                {
                    swipeLayout.isRefreshing=false
                    lastPosition=it.size-1
                    showProductMasterList(it)

                }
            })


    }


    private fun showProductMasterList(list: List<ProductWithoutMasterModel>)
    {
        if(lastPosition-Utils.ROWS<=0)
            b.rv.scrollToPosition(0)
        else b.rv.scrollToPosition(lastPosition-Utils.ROWS)
        val adapter = ProductWithoutMasterAdapter(list, requireActivity(),
            object : ProductWithoutMasterAdapter.OnCallBackListener
            {
                override fun onClick(model: ProductWithoutMasterModel)
                {
                    showUnitOfMeasureDialog(model.productID,model.productTitle)
                }

                override fun reachToEnd(position: Int)
                {
                    page=page+1
                    viewModel.setProductWithoutMaster(
                        pref.getDomain(),
                        textEdi(b.mainToolbar.searchEdi), page, Utils.ROWS, sortType,
                        orderType, pref.getTokenGlcTest(), b.progressBar, b.swipeLayout
                    )

                }
            })
        b.rv.adapter = adapter

        b.mainToolbar.searchEdi.doAfterTextChanged()
        {
            startTimerForGettingData {
               getFilteredData()
            }
        }



    }

    private fun showUnitOfMeasureDialog(productId: String, productTitle: String)
    {

        val dialogBinding = DialogUnitOfMeasureBinding
            .inflate(LayoutInflater.from(requireActivity()), null)
        val dialog = createAlertDialog(
            dialogBinding,
            R.drawable.shape_background_rect_border_gray_solid_white, requireActivity()
        )
        dialogBinding.tv1.setText(productTitle)
        dialogBinding.palletTv.setOnClickListener()
        {
            showPalletSheetDialog(dialogBinding)
        }
        dialogBinding.closeImg.setOnClickListener{dialog.dismiss()}
        dialogBinding.rel4.cansel.setOnClickListener { dialog.dismiss() }
        dialogBinding.rel4.ok.setOnClickListener()
        {
            if (lenEdi(dialogBinding.widthEdi)!=0 && lenEdi(dialogBinding.heightEdi)!=0
                && lenEdi(dialogBinding.lenghtEdi)!=0 && lenEdi(dialogBinding.palletLayer)!=0
                && lenEdi(dialogBinding.palletLayer)!=0 && lenEdi(dialogBinding.quantityPerLayer)!=0)
            {
                viewModel.setUnitOfMeasure(
                    pref.getDomain(),goodId = productId,
                    textEdi(dialogBinding.widthEdi).toInt(), textEdi(dialogBinding.heightEdi).toInt(),
                    textEdi(dialogBinding.lenghtEdi).toInt(),palletId,
                    textEdi(dialogBinding.palletLayer).toInt(),
                    textEdi(dialogBinding.quantityPerLayer).toInt(),pref.getTokenGlcTest(),dialogBinding.progress
                )

                observeUnitOfMeasure(dialogBinding.widthEdi,
                    dialogBinding.heightEdi,dialogBinding.lenghtEdi,dialogBinding.palletLayer,
                    dialogBinding.quantityPerLayer, dialog)
            }else toast(getString(R.string.fillAllFields),requireActivity())


        }

    }

    private fun observeUnitOfMeasure(widthEdi:EditText,
        heightEdi:EditText,lengthEdi:EditText,palletLayer:EditText,quantityPerLayer:EditText,dialog:AlertDialog)
    {
        widthEdi.setText("")
        heightEdi.setText("")
        lengthEdi.setText("")
        palletLayer.setText("")
        quantityPerLayer.setText("")

        var done=false
        viewModel.getUnitOfMeasure().observe(viewLifecycleOwner, object :
            Observer<UnitOfMeasureSubmitModel>
        {
            override fun onChanged(it: UnitOfMeasureSubmitModel)
            {
                dialog.dismiss()
                if (done==false)
                {
                    done=true
                    toast(it.messages.get(0),requireActivity())
                }

            }

        })
    }

    private fun showPalletSheetDialog(dialogBinding: DialogUnitOfMeasureBinding, )
    {

        var sheet: SheetPalletDialog? = null
        sheet = SheetPalletDialog(getString(R.string.unitMeasure),
            object : SheetPalletDialog.OnClickListener {
                override fun onCloseClick() {
                    sheet?.dismiss()
                }

                override fun initData(binding: DialogSheetDestinyLocationBinding)
                {
                    binding.title.text=getString(R.string.pa)
                    viewModel.setPalletList(
                        pref.getDomain(),pref.getTokenGlcTest())
                    observePalletRequest(sheet, dialogBinding, binding.rv,binding.searchEdi,binding.clearImg)
                }

            })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun observePalletRequest(
        sheet: SheetPalletDialog?,
        dialogBinding: DialogUnitOfMeasureBinding,
        rv: RecyclerView,
        searchEdi: EditText,
        clearImg: ImageView
    )
    {
        viewModel.getPallet().observe(viewLifecycleOwner, object : Observer<List<PalletModel>>
            {
                override fun onChanged(list: List<PalletModel>)
                {
                    val adapter = PalletAdapter(list, requireActivity(),
                        object : PalletAdapter.OnCallBackListener
                        {
                            override fun onClick(model: PalletModel)
                            {
                                sheet?.dismiss()
                                dialogBinding.palletTv.text = model.palletName
                                palletId = model.palletID
                            }

                        })
                    rv.adapter = adapter

                    searchEdi.doAfterTextChanged {
                        adapter.setFilter(search(textEdi(searchEdi),list,Utils.PALLET_CODE,Utils.PALLET_NAME))
                    }
                    clearEdi(clearImg,searchEdi)

                }
            })



    }


    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onDestroy()
    {
        super.onDestroy()
        if(view!=null){

        }
    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.productWithouMaster))
        setToolbarBackground(b.mainToolbar.rel2,requireActivity())
        b.mainToolbar.searchEdi.setHint(getString(R.string.searchKeyWord))
    }




    override fun getLayout(): Int {
         return R.layout.fragment_product_without_master
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}
