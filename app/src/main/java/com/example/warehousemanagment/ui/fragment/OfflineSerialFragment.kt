package com.example.warehousemanagment.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.databinding.FragmentOfflineSerialBinding
import com.example.warehousemanagment.model.classes.Common
import com.example.warehousemanagment.model.classes.checkEnterKey
import com.example.warehousemanagment.model.classes.checkIfIsValidChars
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.hideView
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.search
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.SearchFields
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.receive.receiving_detail_serials.ReceivingDetailSerialModel
import com.example.warehousemanagment.ui.adapter.SerialAdapter
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.example.warehousemanagment.ui.dialog.SheetOfflineSubmitDialog
import com.example.warehousemanagment.ui.dialog.SheetSortFilterDialog
import com.example.warehousemanagment.viewmodel.OfflineSerialViewModel
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date


class OfflineSerialFragment : BaseFragment<OfflineSerialViewModel, FragmentOfflineSerialBinding>()
{
    var chronometer:CountDownTimer ?=null
    val PERMISSION_ALL = 1
    val PERMISSIONS = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var serialOrder=Utils.ASC_ORDER

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        clearEdi(b.relSerials.clearImg,b.relSerials.quantity)
        clearEdi(b.clearImg,b.searchEdi)
        initRequestPermission(requireActivity())
        b.relSerials.quantity.requestFocus()

        checkEnterKey(b.relSerials.quantity)
        {
            addSerialByBoth()

        }
        b.add.setOnClickListener()
        {
            addSerialByBoth()
        }


        observeOfflineSerials(b.serialsCount)


        b.printTv.tv.setOnClickListener()
        {
            showStoreDataConfirm(getString(R.string.storeData), getString(R.string.areYouStore))
        }

        b.sortImg.setOnClickListener {
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
                binding.relProdcutCode.visibility=View.INVISIBLE
                binding.relProductTitle.visibility=View.INVISIBLE

            }

            override fun initTickedSort(binding: DialogSheetSortFilterBinding)
            {

            }

            override fun initAscDesc(asc: TextView, desc: TextView)
            {
                if(serialOrder== Utils.ASC_ORDER){
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
                serialOrder= Utils.ASC_ORDER
                viewModel.sortAsc()

            }

            override fun onDescClick() {
                serialOrder = Utils.DESC_ORDER
                viewModel.sortDesc()
            }

            override fun onLocationCodeClick() {}

            override fun onProductCodeClick() {

            }

            override fun onProductTitleClick() { }

            override fun onOwnerClick() {}
            override fun onRel5Click() {
                TODO("Not yet implemented")
            }

        })
        sheet.show(this.getParentFragmentManager(), "")

    }


    private fun addSerialByBoth()
    {
        val serial = ReceivingDetailSerialModel(
            false, "", "",
            textEdi(b.relSerials.quantity)
        )
        if (lenEdi(b.relSerials.quantity) != 0 )
        {

                if (checkIfIsValidChars(
                        textEdi(b.relSerials.quantity),
                        pref.getUnValidChars(),pref.getSerialLenMax(),
                        pref.getSerialLenMin(),requireActivity()) == true
                ){
                    addSerial(serial)
                }



        }
    }

    private fun addSerial(serial: ReceivingDetailSerialModel) {
        viewModel.addSerial(serial){
            toast(getString(R.string.serialExists),requireActivity())
        }
        b.relSerials.quantity.setText("")
    }

    private fun showStoreDataConfirm(title:String,desc:String)
    {
        var mySheetAlertDialog: SheetOfflineSubmitDialog? = null
        mySheetAlertDialog =
            SheetOfflineSubmitDialog(title, desc, object : SheetOfflineSubmitDialog.OnClickListener {
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String, fileName: String)
                {
                    if (fileName.length!=0)
                    {
                        if (hasPermissions(requireActivity(), *PERMISSIONS)==true)
                        {
                            createXLSReport(viewModel.getSerials().value,fileName)
                            mySheetAlertDialog?.dismiss()
                        }else {
                            log("permissionError","there is an error in logic")
                            initRequestPermission(requireActivity())
                        }
                    }else toast(getString(R.string.fileNameCantBeEmpty),requireActivity())

                }

                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun hideCansel(cansel: TextView) {

                }

                override fun onDismiss() {

                }

            })
        mySheetAlertDialog.show(this.getParentFragmentManager(), "")
    }

    fun getNowDate():String{
        val simpleDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = simpleDateFormat.format(Date())
        log("-dateNow",currentDate.toString())
        return "file_"+currentDate.toString()
            .replace("/","_").replace(":","_")
            .replace(" ","_time_")

    }
    private fun createXLSReport(list: List<ReceivingDetailSerialModel>?, fileName: String)
    {
        try
        {
            val path = File(Common.getAppPath(requireActivity()) +fileName+getNowDate()+".xls")

            val ssf = HSSFWorkbook()
            val ssfSheet = ssf.createSheet()

            val ssfRow = ssfSheet.createRow(0)
            val ssfCell = ssfRow.createCell(0)

            ssfCell.setCellValue(getString(R.string.serialNumber))


            for (i in 0..(list?.size?.minus(1)!!)){
                val ssfRow = ssfSheet.createRow(i+1)
                val ssfCell = ssfRow.createCell(0)

                ssfCell.setCellValue(list?.get(i)?.serialNumber)
            }


            //Environment.getExternalStoragePublicDirectory()

            if (!path.exists()) {
                path.createNewFile()
            }
            val output = FileOutputStream(path)
            ssf.write(output)
            if (output != null) {
                output.flush()
                output.close()
                viewModel.clearSerials()
            }
        } catch (e: Exception) {
            log("excelError", e.toString())
        }
    }

    fun initRequestPermission(activity: Activity) {
        if (!hasPermissions(activity, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL)
        }
    }


    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission!!)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }



    private fun observeOfflineSerials(countTv:TextView)
    {
        viewModel.getSerials().observe(viewLifecycleOwner,object :Observer<List<ReceivingDetailSerialModel>>
        {
            override fun onChanged(list: List<ReceivingDetailSerialModel>)
            {
                showLocationList(list)
                countTv.setText(getBuiltString(getString(R.string.serialsItems),list.size.toString()))

            }

        })



    }


    private fun showLocationList(list:List<ReceivingDetailSerialModel>)
    {
       val adapter=SerialAdapter(list,requireActivity(),object :SerialAdapter.OnCallBackListener{
            override fun onDelete(model: ReceivingDetailSerialModel)
            {

                val sb= getBuiltString(getString(R.string.are_you_sure_for_delete),
                    model.serialNumber,getString(R.string.are_you_sure_for_delete2))
                showDeleteSheetDialog(getString(R.string.serial_scan_confirm),sb,model)
            }

            override fun imgVisible(img: ImageView) {
                img.visibility=View.INVISIBLE
            }

        })
        b.rv.adapter=adapter

        b.searchEdi.doAfterTextChanged {
            adapter.setFilter(search(textEdi(b.searchEdi),list,SearchFields.SerialNumber))
        }



    }
    private fun showDeleteSheetDialog(
        title: String,
        desc: String,
        serialModel:ReceivingDetailSerialModel)
    {
        var mySheetAlertDialog:SheetAlertDialog ?=null
        mySheetAlertDialog= SheetAlertDialog(title,desc
            ,object :SheetAlertDialog.OnClickListener{
                override fun onCanselClick() {
                    mySheetAlertDialog?.dismiss()
                }

                override fun onOkClick(progress: ProgressBar, toInt: String)
                {
                    viewModel.removeSerial(serialModel)
                    mySheetAlertDialog?.dismiss()
                    toast(getString(R.string.successDeleted),requireActivity())
                }


                override fun onCloseClick() {
                    mySheetAlertDialog?.dismiss()
                }
                override fun hideCansel(cansel: TextView) {

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
        hideView(requireActivity(),R.id.summaryTv,View.VISIBLE)

    }

    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.offlineSerials))
        b.relSerials.quantity.setHint(getString(R.string.serial))
        hideView(requireActivity(),R.id.summaryTv,View.GONE)
        b.printTv.tv.text=getString(R.string.submit)

    }




    override fun getLayout(): Int {
         return R.layout.fragment_offline_serial
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
