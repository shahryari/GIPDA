package com.example.warehousemanagment.ui.activity

import android.Manifest
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.Common
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.toast
import com.example.warehousemanagment.model.constants.Utils
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

class TestActivity : AppCompatActivity()
{
    val file_name="test_pdf.pdf"
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val printBtn=findViewById<Button>(R.id.leftDock)

        Dexter.withActivity(this).
            withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object :PermissionListener
            {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    printBtn.setOnClickListener {
                        createPdfFile(Common.getAppPath(this@TestActivity)+file_name)
                    }
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

    private fun createPdfFile(path: String)
    {
        if (File(path).exists()){
            File(path).delete()
        }
        try
        {
            val document= Document()
            PdfWriter.getInstance(document,FileOutputStream(path))
            document.open()

            document.pageSize=PageSize.A4
            document.addCreationDate()
            document.addAuthor("Majid2851")
            document.addCreator("Majid Bagheri")

            val colorAccent=BaseColor(0,153,204,255)
            val headingFontSize=20.0f
            val valueFontSize=26.0f

            //Custom Font
            val fontName=BaseFont.createFont("assets/fonts/medium.ttf","UTF-8",BaseFont.EMBEDDED)

            //Add Title
            val titleStyle=Font(fontName,36.0f,Font.NORMAL,BaseColor.BLACK)
            addNewItem(document,"Order Detail",Element.ALIGN_CENTER,titleStyle)

            //Add Heading
            val headingStyle=Font(fontName,headingFontSize,Font.NORMAL,BaseColor.BLACK)
            addNewItem(document,"Order No:",Element.ALIGN_CENTER,headingStyle)



            addLineSeperator(document)

            document.close()

            printPdf(Common.getAppPath(this)+file_name)

        }catch (e:Exception){
            log("printError",e.toString())
        }

    }

    private fun printPdf(path: String)
    {
        write("",path)
//        val printManager=getSystemService(Context.PRINT_SERVICE,) as PrintManager
//        try {
//            val printAdapter=PDFDocumentAdapter(this, Common.getAppPath(this)+file_name)
//            printManager.print("Document", printAdapter,PrintAttributes.Builder().build())
//        }catch
    //        (e:Exception){
//            log("printError2",e.toString())
//        }
    }
    fun write(content: String, address: String?) {
        address?.let {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            Base64.decode("Base64 image content goes here", Base64.DEFAULT).printByDeviceIp(address)
            toast("printed",this)
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
        val lineseperator=LineSeparator()
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


}