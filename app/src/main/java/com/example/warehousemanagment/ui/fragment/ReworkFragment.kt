package com.example.warehousemanagment.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.os.StrictMode
import android.print.*
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.currencykotlin.model.di.component.FragmentComponent
import com.example.kotlin_wallet.ui.base.BaseFragment
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetInvListBinding
import com.example.warehousemanagment.databinding.FragmentReworkBinding
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.rework.ReworkModel
import com.example.warehousemanagment.ui.adapter.PDFDocumentAdapter
import com.example.warehousemanagment.ui.dialog.SheetInvDialog
import com.example.warehousemanagment.viewmodel.ReworkViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.CodaBarWriter
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.*
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat
import java.util.*


class ReworkFragment
    : BaseFragment<ReworkViewModel,FragmentReworkBinding>()
{
    val MULTI_CENTI=28.333F
    var invTypeId=Utils.MINUS_ONE
    lateinit var file_name:String

    val PERMISSION_ALL = 1
    val TINA=1
    val OTHER_COMPANIES=2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        file_name=getString(R.string.rework)+getNowDate()+".pdf"


        clearEdi(b.serialClearImg,b.serialEdi)
        clearEdi(b.invTypeImg,b.invType)


        b.printTv.tv.setOnClickListener()
        {
            log("printNames==>",pref.getPrintName()+","+pref.getTinaPaperSize()+","
            +pref.getOtherPaperSize())
            if (lenEdi(b.serialEdi)!=0 && lenEdi(b.invType)!=0)
            {
                if (
                    pref.getPrintName().length!=0 &&
                    pref.getOtherPaperSize().length!=0 &&
                    pref.getTinaPaperSize().length!=0)
                {

                    viewModel.rework(
                        pref.getDomain(),textEdi(b.serialEdi),invTypeId,pref.getTokenGlcTest(),
                        printName = pref.getPrintName() ,
                        tinaPaperSize=pref.getTinaPaperSize(),
                        otherPaperSize=pref.getOtherPaperSize(),
                        b.progressBar)
                }else
                {
                    toast(getString(R.string.fillAllPrinterField),requireActivity())
                }


            }else toast(getString(R.string.fillAllFields),requireActivity())
        }

        log("barcode",pref.getBarcode().toString())

        b.invType.setOnClickListener()
        {
            showInvSheet()
        }

    }

    fun getNowDate():String{
        val simpleDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = simpleDateFormat.format(Date())
        log("-dateNow",currentDate.toString())
        return "file_"+currentDate.toString()
            .replace("/","_").replace(":","_")
            .replace(" ","_time_")

    }





    private fun showInvSheet()
    {

        var sheet: SheetInvDialog? = null
        sheet = SheetInvDialog(viewModel.invList(requireActivity()), object : SheetInvDialog.OnClickListener {
            override fun onCloseClick() {
                sheet?.dismiss()
            }

            override fun onInvClick(model: CatalogModel) {
                b.invType.setText(model.title)
                invTypeId=model.valueField
                sheet?.dismiss()
            }

            override fun init(binding: DialogSheetInvListBinding) {

            }

        })
        sheet.show(this.getParentFragmentManager(), "")
    }

    private fun startPrinting(model: ReworkModel)
    {
        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                    createPdfFile(Common.getAppPath(requireActivity()) + file_name,model)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }
            }).check()
    }


    private fun createPdfFile(path: String, model: ReworkModel)
    {
        if (File(path).exists()){
            File(path).delete()
        }
        try
        {
            val document= Document()
            PdfWriter.getInstance(document, FileOutputStream(path))
            document.open()

            if (invTypeId==TINA)
            {
                document.pageSize=RectangleReadOnly(
                    6.7F*MULTI_CENTI,
                    10.6F*MULTI_CENTI
                )
            }else if (invTypeId==OTHER_COMPANIES)
            {
                document.pageSize=RectangleReadOnly(
                    6*MULTI_CENTI,
                    3*MULTI_CENTI)
            }

//            document.pageSize=PageSize.A4//RectangleReadOnly(595F, 842F)//PageSize.A4


            val headingFontSize=20.0f

            //Custom Font
//            val fontName= BaseFont.createFont("assets/fonts/shabnam.ttf","UTF-8", BaseFont.EMBEDDED)
            val fontName=BaseFont.createFont("assets/fonts/medium.ttf",
                "UTF-8", BaseFont.EMBEDDED)

            if (invTypeId==TINA)
            {
                createTinaContent(fontName, document, headingFontSize,model.trackingNumber,"trackingnumber")
                createTinaContent(fontName, document, headingFontSize, model.serialNumber,"serialnumber")
                createTinaContent(fontName, document, headingFontSize ,model.gTINCode, "GtinCode")
                createTinaContent(fontName, document, headingFontSize, model.internalCode, "Internalcode")
            }
            else
            {
                createOtherFirmsContent(fontName, document, headingFontSize
                    ,model.trackingNumber,model.gTINCode,model.internalCode)
            }





            document.close()

            printPdf(Common.getAppPath(requireActivity())+file_name)

        }catch (e:Exception){
            toast(e.toString(),requireActivity())
            log("printError",e.toString())
        }

    }

    private fun createTinaContent(
        baseFont: BaseFont?,
        document: Document,
        headingFontSize: Float,
        myBarcode:String,
        typeTitle:String
    ) {
        val typeStyle = Font(baseFont, headingFontSize, Font.NORMAL, BaseColor.BLACK)
        addNewItem(document, typeTitle, Element.ALIGN_LEFT, typeStyle)

        var barcodeFormat:BarcodeFormat?=null
        for (i in viewModel.getBarcodeList())
        {
            if (i.barcodeNumber==pref.getBarcode())
            {
                barcodeFormat=i.barcodeValue
                addBarcodeToPdf(myBarcode, document, barcodeFormat)

                val barcodeNumberStyle = Font(baseFont, headingFontSize, Font.NORMAL, BaseColor.BLACK)
                addSubItem(document, myBarcode, Element.ALIGN_LEFT, barcodeNumberStyle)

                return
            }
        }

        //Add Serials
        val headingStyle = Font(baseFont, headingFontSize, Font.NORMAL, BaseColor.BLACK)
        addSubItem(document, myBarcode, Element.ALIGN_LEFT, headingStyle)

        addLineSeperator(document)
    }

    private fun createOtherFirmsContent(
        baseFont: BaseFont?,
        document: Document,
        headingFontSize: Float,
        myBarcode:String,
        productName:String,
        productSerial:String
    ) {
        val typeStyle = Font(baseFont, headingFontSize, Font.NORMAL, BaseColor.BLACK)

        addNewItem(document, "Tracking Code", Element.ALIGN_LEFT, typeStyle)

        var barcodeFormat:BarcodeFormat?=null
        for (i in viewModel.getBarcodeList()){
            if (i.barcodeNumber==pref.getBarcode())
            {
                barcodeFormat=i.barcodeValue
                log("barcode",barcodeFormat.toString())
                addBarcodeToPdf(myBarcode, document, barcodeFormat)

                val productNameStyle = Font(baseFont, headingFontSize, Font.NORMAL, BaseColor.BLACK)
                addNewItem(document, i.barcodeNumber.toString(), Element.ALIGN_LEFT, productNameStyle)

                val barcodeNumberStyle = Font(baseFont, headingFontSize, Font.NORMAL, BaseColor.BLACK)
                addNewItem(document, "Product Name   :"+"           "+
                        productName, Element.ALIGN_LEFT, barcodeNumberStyle)


                val productSerialStyle = Font(baseFont, headingFontSize, Font.NORMAL, BaseColor.BLACK)
                addNewItem(document, "Produce Serial   :"+"           "
                        +productSerial, Element.ALIGN_LEFT, productSerialStyle)

                return
            }
        }

        //Add Serials
        val headingStyle = Font(baseFont, headingFontSize, Font.NORMAL, BaseColor.BLACK)
        addSubItem(document, myBarcode, Element.ALIGN_LEFT, headingStyle)

        addLineSeperator(document)
    }

    private fun addBarcodeToPdf(myBarcode: String, document: Document,barcodeFormat: BarcodeFormat)
    {
        try {
            val SIZE=80
            val number = myBarcode
            val writer = CodaBarWriter()
            val bitMatrix: BitMatrix = writer.encode(number, barcodeFormat, SIZE, SIZE)
            val bitmap = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888)
            for (x in 0..SIZE-1) {
                for (y in 0..SIZE-1) {
                    bitmap.setPixel(
                        x,
                        y,
                        if (bitMatrix.get(
                                x,
                                y
                            )
                        ) resources.getColor(R.color.black) else resources.getColor(R.color.white)
                    )
                }
            }
            val image: Image = Image.getInstance(bitmapToByteArray(bitmap))
            document.add(image)

        } catch (e: WriterException) {
            toast(e.toString(),requireActivity())
            e.printStackTrace()
        }
    }

    // Convert a Bitmap to a byte array
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun printPdf(path: String)
    {
        write(path)
//        if (pref.getPreviewStatus()==true)
//        {
//            preview()
//        }

    }

    private fun printDirectly() {
        val printManager = requireActivity().getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = "Document"
        printManager.print(jobName, object : PrintDocumentAdapter() {
            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes?,
                cancellationSignal: android.os.CancellationSignal?,
                callback: LayoutResultCallback?,
                extras: Bundle?
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback?.onLayoutCancelled()
                    return
                }

                val info = PrintDocumentInfo.Builder(jobName)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build()
                callback?.onLayoutFinished(info, true)
            }

            override fun onWrite(
                pages: Array<out PageRange>?,
                destination: ParcelFileDescriptor?,
                cancellationSignal: android.os.CancellationSignal?,
                callback: WriteResultCallback?
            ) {
                val fileDescriptor =
                    FileInputStream(Common.getAppPath(requireActivity()) + file_name).fd
                val inputStream = FileInputStream(fileDescriptor)
                val outputStream = FileOutputStream(destination?.fileDescriptor)

                val buf = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buf).also { bytesRead = it } > 0) {
                    outputStream.write(buf, 0, bytesRead)
                }

                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                inputStream.close()
                outputStream.close()
            }


        }, null)
    }

    private fun preview() {
        val printManager = requireActivity().getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            val printAdapter = PDFDocumentAdapter(
                requireActivity(),
                Common.getAppPath(requireActivity()) + file_name
            )
            printManager.print("Document", printAdapter, PrintAttributes.Builder().build())
        } catch
            (e: Exception) {
            log("printError2", e.toString())
        }
    }

    fun write( path: String?) {
        path?.let {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            Base64.decode("Base64 image content goes here", Base64.DEFAULT).printByDeviceIp(path)
            toast("printed",requireActivity())
        }
    }

    fun ByteArray.printByDeviceIp(address: String)
    {
        try {
            val socket = Socket(address, Utils.PRINTER_DEFAULT_PORT)
            val output = DataOutputStream(socket.getOutputStream())
            val buffer = ByteArray(Utils.PRINTER_BUFFER_SIZE)
            val inputStream = ByteArrayInputStream(this)

            output.writeBytes(Utils.UEL)
            output.writeBytes(Utils.PRINT_META_JOB_START)
            output.writeBytes(Utils.PRINT_META_JOB_NAME)
            output.writeBytes(Utils.PRINT_META_JOB_PAPER_TYPE)
            output.writeBytes(Utils.PRINT_META_JOB_COPIES)
            output.writeBytes(Utils.PRINT_META_JOB_LANGUAGE)

            while (inputStream.read(buffer) != -1)
                output.write(buffer)

            output.writeBytes(Utils.ESC_SEQ)
            output.writeBytes(Utils.UEL)

            output.flush()

            inputStream.close()
            output.close()
            socket.close()
        } catch (e: Exception) {
            when (e) {
                is SocketException -> Log.e(this.javaClass.name, "Network failure: ${e.message}")
                is SocketTimeoutException -> Log.e(this.javaClass.name, "Timeout: ${e.message}")
                is IOException -> Log.e(this.javaClass.name, "Buffer failure: ${e.message}")
                else -> Log.e(this.javaClass.name, "General failure: ${e.message}")
            }
        }
    }

    private fun addLineSeperator(document: Document)
    {
        val lineseperator= LineSeparator()
        lineseperator.lineColor= BaseColor(0,0,0,64)
        addLineSpace(document)
        document.add(Chunk(lineseperator))
        addLineSpace(document)

    }

    private fun addLineSpace(document: Document) {
        document.add(Paragraph(""))
    }


    @Throws(DocumentException::class)
    private fun addNewItem(document: Document, text: String, align: Int, style: Font)
    {
        val chunk=Chunk(text,style)
        val p=Paragraph(chunk)
        p.alignment=align
        document.add(p)

    }
    @Throws(DocumentException::class)
    private fun addSubItem(document: Document, text: String, align: Int, style: Font)
    {
        val chunk=Chunk(text,style)
        document.add(chunk)

    }


    override fun onDestroy() {
        super.onDestroy()
        hideView(requireActivity(),R.id.summaryTv,View.VISIBLE)


    }

    override fun init()
    {
        hideView(requireActivity(),R.id.summaryTv,View.GONE)
        setToolbarTitle(requireActivity(),getString(R.string.rework))
        b.printTv.tv.text=getString(R.string.submit)
    }

    override fun onResume() {
        super.onResume()
        hideShortCut(requireActivity())
    }



    override fun getLayout(): Int {
         return R.layout.fragment_rework
    }

    override fun setupComponent(component: FragmentComponent) {
        component.inject(this)
    }




}
