package com.example.warehousemanagment.ui.adapter

import android.content.Context
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.printservice.PrintDocument
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.logErr
import java.io.*

class PDFDocumentAdapter(context: Context,path: String) : PrintDocumentAdapter()
{
    internal var context:Context?=null
    internal var path=""
    init {
        this.context=context
        this.path=path
    }

    override fun onLayout(
        p0: PrintAttributes?,
        p1: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        layoutResultCallback: LayoutResultCallback?,
        p4: Bundle?
    ) {
        if (cancellationSignal!!.isCanceled){
            layoutResultCallback!!.onLayoutCancelled()
        }else{
            val builder=PrintDocumentInfo.Builder("file name")
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build()
            layoutResultCallback!!.onLayoutFinished(builder.build(),p1!=p0)
        }
    }

    override fun onWrite(
        pageRange: Array<out PageRange>?,
        parcelFileDescriptor: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        writeResultCallback: WriteResultCallback?
    ) {
        var input:InputStream ?=null
        var output:OutputStream ?=null
        try {
            val file=File(path)
            input=FileInputStream(file)
            output=FileOutputStream(parcelFileDescriptor!!.fileDescriptor)

            if (!cancellationSignal!!.isCanceled){
                input.copyTo(output)
                writeResultCallback!!.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            }else{
                writeResultCallback!!.onWriteCancelled()
            }
        }catch (e:Exception){
            writeResultCallback!!.onWriteFailed(e.message)
            log("printAdatper",e.toString())
        }finally {
            try {
                input!!.close()
                output!!.close()
            }catch (e:java.lang.Exception){
                log("printAdatperFinally",e.toString())
            }


        }

    }


}