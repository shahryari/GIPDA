package com.example.warehousemanagment.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.warehousemanagment.R
import com.example.warehousemanagment.dagger.component.FragmentComponent
import com.example.warehousemanagment.databinding.FragmentSettingBinding
import com.example.warehousemanagment.model.classes.clearEdi
import com.example.warehousemanagment.model.classes.hideShortCut
import com.example.warehousemanagment.model.classes.hideView
import com.example.warehousemanagment.model.classes.lenEdi
import com.example.warehousemanagment.model.classes.setToolbarTitle
import com.example.warehousemanagment.model.classes.textEdi
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.BarcodeModel
import com.example.warehousemanagment.ui.base.BaseFragment
import com.example.warehousemanagment.ui.dialog.SheetBarcodeDialog
import com.example.warehousemanagment.viewmodel.SettingViewModel

class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>()
{
    var barcodeType=1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initSetting()
        b.submit.tv.setOnClickListener { saveSetting() }

    }

    private fun initSetting()
    {
        b.unValidEdi.setText(pref.getUnValidChars())
        b.baseUrlEdi.setText(pref.getDomain().replace(Utils.CONSTANT_PART_DOMAIN,""))
        b.notifEdi.setText((pref.getServicePriod()).toString())
        b.avoidDuplicateSerialsCb.isChecked = pref.getDisallowRepetitiveSerial()


        b.tinaLabelPrinterEdi.setText(pref.getTinaPaperSize())
        b.otherLabelPrinterEdi.setText(pref.getOtherPaperSize())
        b.printerNameEdi.setText(pref.getPrintName())


        initMinMaxValues(b.serialLenEdiMin,b.serialLenEdiMax)

        clearEdi(b.clearNotifImg, b.notifEdi)
        clearEdi(b.clearImgBaseUrl, b.baseUrlEdi)
        clearEdi(b.clearImgSerialLenMax, b.serialLenEdiMax)
        clearEdi(b.clearImgSerialLenMin, b.serialLenEdiMin)
        clearEdi(b.clearImgUnValid, b.unValidEdi)
        clearEdi(b.clearImgTinaLabelPrinter, b.tinaLabelPrinterEdi)
        clearEdi(b.clearImgOtherLabelPrinter, b.otherLabelPrinterEdi)
        clearEdi(b.clearImgPrinterName,b.printerNameEdi)





    }
    private fun showBarcodeDialog()
    {
        var sheet: SheetBarcodeDialog? = null
        sheet = SheetBarcodeDialog(viewModel.getBarcodeList(), object : SheetBarcodeDialog.OnClickListener {
            override fun onCloseClick() {
                sheet?.dismiss()
            }

            override fun onBarcodeClick(model: BarcodeModel) {
                sheet?.dismiss()

            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun initMinMaxValues(min:EditText,max:EditText)
    {
        if (pref.getSerialLenMax() == Utils.MINUS_ONE)
            max.setText("")
        else max.setText(pref.getSerialLenMax().toString())

        if (pref.getSerialLenMin() == Utils.MINUS_ONE)
            min.setText("")
        else min.setText(pref.getSerialLenMin().toString())


    }



    private fun saveSetting()
    {
        pref.saveDomain(textEdi(b.baseUrlEdi))



        pref.saveUnValidChars(textEdi(b.unValidEdi).trim())
        if (lenEdi(b.notifEdi)==0 || textEdi(b.notifEdi).toInt()==0)
            pref.saveServicePriod(Utils.NOTIF_DEFAULT_IME)
        else pref.saveServicePriod(textEdi(b.notifEdi).toInt())

        setMinAndMaxValues(b.serialLenEdiMin,b.serialLenEdiMax)

        pref.saveBarcode(barcodeType)


       pref.saveTinaPaperSize(textEdi(b.tinaLabelPrinterEdi))

       pref.saveOtherPaperSize(textEdi(b.otherLabelPrinterEdi) )

       pref.savePrintName(textEdi(b.printerNameEdi))

        pref.saveDisallowRepetitiveSerial(b.avoidDuplicateSerialsCb.isChecked)


    }


    private fun setMinAndMaxValues(min:EditText,max:EditText)
    {
        if (lenEdi(min) != 0 && lenEdi(max) != 0 && textEdi(min).toInt()>textEdi(max).toInt())
        {
            toast(getString(R.string.minMustLestMax),requireActivity())
        }else
        {
            if (lenEdi(max) == 0)
                pref.saveSerialLenMax(Utils.MINUS_ONE)
            else pref.saveSerialLenMax(textEdi(max).toInt())

            if (lenEdi(min) == 0)
                pref.saveSerialLenMin(Utils.MINUS_ONE)
            else pref.saveSerialLenMin(textEdi(min).toInt())

            toast(getString(R.string.successEditted),requireActivity())

        }


    }


    override fun init()
    {
        setToolbarTitle(requireActivity(),getString(R.string.setting))
        b.submit.tv.text=getString(R.string.submit)
        hideView(requireActivity(),R.id.summaryTv,View.GONE)
        barcodeType=pref.getBarcode()


    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }

    override fun onStop() {
        super.onStop()
        hideView(requireActivity(),R.id.summaryTv,View.VISIBLE)
    }
    override fun getLayout(): Int {
         return R.layout.fragment_setting
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }


}